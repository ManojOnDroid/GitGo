package com.example.gitgo.modules.repoDetailScreen.screen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material.icons.filled.Update
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.gitgo.modules.repoDetailScreen.states.RepoDetailsResponseState
import com.example.gitgo.modules.repoDetailScreen.viewmodel.RepoDetailsViewModel
import com.example.gitgo.ui.theme.GitGoTheme
import java.text.SimpleDateFormat
import java.util.Locale

private object DateFormatter {
    private val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    private val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())

    fun format(dateString: String): String {
        return try {
            val date = inputFormat.parse(dateString)
            date?.let { outputFormat.format(it) } ?: dateString
        } catch (e: Exception) {
            dateString
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RepoDetailsScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: RepoDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.repoDetails.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(state) {
        if (state is RepoDetailsResponseState.Error) {
            val errorMessage = (state as RepoDetailsResponseState.Error).error
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        GitGoTheme.colors.gradientStart,
                        GitGoTheme.colors.gradientEnd,
                        GitGoTheme.colors.background
                    )
                )
            )
    ) {
        when (val data = state) {
            is RepoDetailsResponseState.Loading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(50.dp),
                        color = GitGoTheme.colors.primary,
                        strokeWidth = 4.dp
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Loading repository details...",
                        color = GitGoTheme.colors.textSecondary,
                        style = GitGoTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            is RepoDetailsResponseState.Error -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Surface(
                        shape = CircleShape,
                        color = GitGoTheme.colors.error.copy(alpha = 0.1f),
                        modifier = Modifier.size(80.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Error,
                            contentDescription = "Error",
                            tint = GitGoTheme.colors.error,
                            modifier = Modifier.padding(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Error loading repository",
                        color = GitGoTheme.colors.textColor,
                        style = GitGoTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = data.error,
                        color = GitGoTheme.colors.textSecondary,
                        style = GitGoTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        lineHeight = 20.sp
                    )
                }
            }

            is RepoDetailsResponseState.Success -> {
                val details = data.details

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = GitGoTheme.colors.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Surface(
                                    shape = CircleShape,
                                    color = GitGoTheme.colors.outline,
                                    modifier = Modifier.size(68.dp)
                                ) {
                                    GlideImage(
                                        model = details.owner?.avatarUrl,
                                        contentDescription = "Owner Avatar",
                                        modifier = Modifier
                                            .size(64.dp)
                                            .padding(2.dp)
                                            .clip(CircleShape)
                                    )
                                }
                                Spacer(modifier = Modifier.width(16.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = details.owner?.login ?: "Unknown Owner",
                                        color = GitGoTheme.colors.textColor,
                                        style = GitGoTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = details.fullName ?: "${viewModel.owner}/${viewModel.repo}",
                                        color = GitGoTheme.colors.textSecondary,
                                        style = GitGoTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                                if (details.private == true) {
                                    Surface(
                                        color = GitGoTheme.colors.warning.copy(alpha = 0.1f),
                                        shape = RoundedCornerShape(12.dp),
                                        border = BorderStroke(1.dp, GitGoTheme.colors.warning.copy(alpha = 0.3f))
                                    ) {
                                        Text(
                                            text = "Private",
                                            color = GitGoTheme.colors.warning,
                                            style = GitGoTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                        )
                                    }
                                }
                            }

                            if (!details.description.isNullOrBlank()) {
                                Spacer(modifier = Modifier.height(16.dp))
                                Surface(
                                    color = GitGoTheme.colors.surfaceVariant,
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Text(
                                        text = details.description,
                                        color = GitGoTheme.colors.textColor,
                                        style = GitGoTheme.typography.bodyLarge,
                                        lineHeight = 22.sp,
                                        modifier = Modifier.padding(16.dp)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        StatCard(
                            icon = Icons.Default.Star,
                            count = details.stargazersCount ?: 0,
                            label = "Stars",
                            color = GitGoTheme.colors.warning,
                            modifier = Modifier.weight(1f)
                        )
                        StatCard(
                            icon = Icons.Default.AccountTree,
                            count = details.forksCount ?: 0,
                            label = "Forks",
                            color = GitGoTheme.colors.info,
                            modifier = Modifier.weight(1f)
                        )
                        StatCard(
                            icon = Icons.Default.BugReport,
                            count = details.openIssuesCount ?: 0,
                            label = "Issues",
                            color = if ((details.openIssuesCount ?: 0) > 0) GitGoTheme.colors.error else GitGoTheme.colors.success,
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    navController.navigate("repoIssues/${viewModel.owner}/${viewModel.repo}")
                                }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    InfoCard(title = "Information", icon = Icons.Default.Info) {
                        InfoRow(Icons.Default.Code, "Default Branch", details.defaultBranch ?: "main")
                        InfoRow(Icons.Default.Storage, "Size", formatSize(details.size ?: 0))
                        InfoRow(
                            Icons.Default.Visibility,
                            "Visibility",
                            details.visibility?.replaceFirstChar { it.uppercaseChar() } ?: if (details.private == true) "Private" else "Public"
                        )
                        details.languagesUrl?.let { InfoRow(Icons.Default.Code, "Language", it.toString()) }
                        if (!details.homepage.isNullOrBlank()) {
                            InfoRow(Icons.Default.Link, "Homepage", details.homepage, isUrl = true)
                        }
                        details.license?.name?.let { InfoRow(Icons.Default.Description, "License", it) }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    InfoCard(title = "Features", icon = Icons.Default.Settings) {
                        val features = listOf(
                            "Issues" to (details.hasIssues == true),
                            "Wiki" to (details.hasWiki == true),
                            "Pages" to (details.hasPages == true),
                            "Projects" to (details.hasProjects == true),
                            "Downloads" to (details.hasDownloads == true),
                            "Discussions" to (details.hasDiscussions == true),
                            "Templates" to (details.isTemplate == true)
                        )

                        features.chunked(2).forEach { row ->
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                row.forEach { (feature, isEnabled) ->
                                    FeatureChip(feature, isEnabled, Modifier.weight(1f))
                                }
                                if (row.size == 1) Spacer(modifier = Modifier.weight(1f))
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    InfoCard(title = "Timeline", icon = Icons.Default.Schedule) {
                        details.createdAt?.let { InfoRow(Icons.Default.Add, "Created", DateFormatter.format(it)) }
                        details.updatedAt?.let { InfoRow(Icons.Default.Update, "Updated", DateFormatter.format(it)) }
                        details.pushedAt?.let { InfoRow(Icons.Default.CloudUpload, "Last Push", DateFormatter.format(it)) }
                    }

                    if (!details.topics.isNullOrEmpty()) {
                        Spacer(modifier = Modifier.height(20.dp))
                        InfoCard(title = "Topics", icon = Icons.Default.Tag) {
                            @OptIn(ExperimentalLayoutApi::class)
                            FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                details.topics.filterNotNull().forEach { topic ->
                                    Surface(
                                        color = GitGoTheme.colors.primary.copy(alpha = 0.1f),
                                        shape = RoundedCornerShape(20.dp),
                                        border = BorderStroke(1.dp, GitGoTheme.colors.primary.copy(alpha = 0.2f))
                                    ) {
                                        Text(
                                            text = topic,
                                            color = GitGoTheme.colors.primary,
                                            style = GitGoTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.Medium,
                                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}


@Composable
private fun InfoCard(
    title: String,
    icon: ImageVector,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = GitGoTheme.colors.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = CircleShape,
                    color = GitGoTheme.colors.primary.copy(alpha = 0.1f),
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = GitGoTheme.colors.primary,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = title,
                    color = GitGoTheme.colors.textColor,
                    style = GitGoTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            content()
        }
    }
}

@Composable
private fun StatCard(
    icon: ImageVector,
    count: Int,
    label: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = GitGoTheme.colors.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, color.copy(alpha = 0.15f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                shape = CircleShape,
                color = color.copy(alpha = 0.1f),
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = color,
                    modifier = Modifier.padding(8.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = formatCount(count),
                color = GitGoTheme.colors.textColor,
                style = GitGoTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = label,
                color = GitGoTheme.colors.textSecondary,
                style = GitGoTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun InfoRow(icon: ImageVector, label: String, value: String, isUrl: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = GitGoTheme.colors.iconSecondary,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = label,
            color = GitGoTheme.colors.textSecondary,
            style = GitGoTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.width(110.dp)
        )
        Text(
            text = value,
            color = if (isUrl) GitGoTheme.colors.primary else GitGoTheme.colors.textColor,
            style = GitGoTheme.typography.bodyMedium,
            fontWeight = if (isUrl) FontWeight.Medium else FontWeight.Normal,
            modifier = Modifier.weight(1f),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

@Composable
private fun FeatureChip(feature: String, isEnabled: Boolean, modifier: Modifier = Modifier) {
    Surface(
        color = if (isEnabled) GitGoTheme.colors.success.copy(alpha = 0.1f) else GitGoTheme.colors.surfaceVariant,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, if (isEnabled) GitGoTheme.colors.success.copy(alpha = 0.2f) else GitGoTheme.colors.outline.copy(alpha = 0.5f)),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = if (isEnabled) Icons.Default.CheckCircle else Icons.Default.Cancel,
                contentDescription = null,
                tint = if (isEnabled) GitGoTheme.colors.success else GitGoTheme.colors.textTertiary,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = feature,
                color = if (isEnabled) GitGoTheme.colors.success else GitGoTheme.colors.textTertiary,
                style = GitGoTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

private fun formatCount(count: Int): String {
    return when {
        count >= 1_000_000 -> "${(count / 1_000_000.0).format(1)}M"
        count >= 1_000 -> "${(count / 1_000.0).format(1)}K"
        else -> count.toString()
    }
}

private fun formatSize(sizeKB: Int): String {
    return when {
        sizeKB >= 1_048_576 -> "${(sizeKB / 1_048_576.0).format(1)} GB"
        sizeKB >= 1_024 -> "${(sizeKB / 1_024.0).format(1)} MB"
        else -> "$sizeKB KB"
    }
}

private fun Double.format(digits: Int) = "%.${digits}f".format(this).trimEnd('0').trimEnd('.')