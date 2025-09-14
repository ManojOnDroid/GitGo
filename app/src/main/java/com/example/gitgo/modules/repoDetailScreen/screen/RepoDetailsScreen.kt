package com.example.gitgo.modules.repoDetailScreen.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.example.gitgo.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RepoDetailsScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: RepoDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.repoDetails.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        GitGoTheme.colors.background,
                        GitGoTheme.colors.surfaceVariant
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
                        color = GitGoTheme.colors.loaderColor,
                        strokeWidth = 4.dp
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Loading repository details...",
                        color = GitGoTheme.colors.textSecondary,
                        style = MaterialTheme.typography.bodyLarge,
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
                        color = GitGoTheme.colors.errorBackground,
                        modifier = Modifier.size(80.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Error,
                            contentDescription = "Error",
                            tint = GitGoTheme.colors.error,
                            modifier = Modifier
                                .size(40.dp)
                                .padding(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Error loading repository",
                        color = GitGoTheme.colors.textColor,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = data.error,
                        color = GitGoTheme.colors.textSecondary,
                        style = MaterialTheme.typography.bodyMedium,
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
                        .padding(12.dp)
                ) {
                    // Header Card with Owner Info and Repository Name
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = GitGoTheme.colors.card
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            GitGoTheme.colors.outline
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
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
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = details.fullName ?: "${viewModel.owner}/${viewModel.repo}",
                                        color = GitGoTheme.colors.textSecondary,
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                                if (details.private == true) {
                                    Surface(
                                        color = GitGoTheme.colors.warningBackground,
                                        shape = RoundedCornerShape(12.dp),
                                        border = androidx.compose.foundation.BorderStroke(
                                            1.dp,
                                            GitGoTheme.colors.warning.copy(alpha = 0.3f)
                                        )
                                    ) {
                                        Text(
                                            text = "Private",
                                            color = GitGoTheme.colors.warning,
                                            style = MaterialTheme.typography.labelMedium,
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
                                        style = MaterialTheme.typography.bodyLarge,
                                        lineHeight = 22.sp,
                                        modifier = Modifier.padding(16.dp)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Statistics Row with enhanced design
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
                            modifier = Modifier.weight(1f).clickable{
                                navController.navigate("repoIssues/${viewModel.owner}/${viewModel.repo}")
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Repository Information Card
                    InfoCard(
                        title = "Repository Information",
                        icon = Icons.Default.Info
                    ) {
                        InfoRow(
                            icon = Icons.Default.Code,
                            label = "Default Branch",
                            value = details.defaultBranch ?: "main"
                        )

                        InfoRow(
                            icon = Icons.Default.Storage,
                            label = "Repository Size",
                            value = formatSize(details.size ?: 0)
                        )

                        InfoRow(
                            icon = Icons.Default.Visibility,
                            label = "Visibility",
                            value = details.visibility?.replaceFirstChar { it.uppercaseChar() }
                                ?: if (details.private == true) "Private" else "Public"
                        )

                        details.languagesUrl?.let { language ->
                            InfoRow(
                                icon = Icons.Default.Code,
                                label = "Language",
                                value = language.toString()
                            )
                        }

                        if (!details.homepage.isNullOrBlank()) {
                            InfoRow(
                                icon = Icons.Default.Link,
                                label = "Homepage",
                                value = details.homepage,
                                isUrl = true
                            )
                        }

                        details.license?.name?.let { licenseName ->
                            InfoRow(
                                icon = Icons.Default.Description,
                                label = "License",
                                value = licenseName
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Repository Features Card
                    InfoCard(
                        title = "Repository Features",
                        icon = Icons.Default.Settings
                    ) {
                        val features = listOf(
                            "Issues" to (details.hasIssues == true),
                            "Wiki" to (details.hasWiki == true),
                            "Pages" to (details.hasPages == true),
                            "Projects" to (details.hasProjects == true),
                            "Downloads" to (details.hasDownloads == true),
                            "Discussions" to (details.hasDiscussions == true),
                            "Template Repository" to (details.isTemplate == true)
                        )

                        features.chunked(2).forEach { row ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                row.forEach { (feature, isEnabled) ->
                                    FeatureChip(
                                        feature = feature,
                                        isEnabled = isEnabled,
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                                if (row.size == 1) {
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Timestamps Card
                    InfoCard(
                        title = "Timeline",
                        icon = Icons.Default.Schedule
                    ) {
                        details.createdAt?.let { createdAt ->
                            InfoRow(
                                icon = Icons.Default.Add,
                                label = "Created",
                                value = formatDate(createdAt)
                            )
                        }

                        details.updatedAt?.let { updatedAt ->
                            InfoRow(
                                icon = Icons.Default.Update,
                                label = "Updated",
                                value = formatDate(updatedAt)
                            )
                        }

                        details.pushedAt?.let { pushedAt ->
                            InfoRow(
                                icon = Icons.Default.CloudUpload,
                                label = "Last Push",
                                value = formatDate(pushedAt)
                            )
                        }
                    }

                    // Topics if available
                    if (!details.topics.isNullOrEmpty()) {
                        Spacer(modifier = Modifier.height(20.dp))
                        InfoCard(
                            title = "Topics",
                            icon = Icons.Default.Tag
                        ) {
                            @OptIn(ExperimentalLayoutApi::class)
                            FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                details.topics.filterNotNull().forEach { topic ->
                                    Surface(
                                        color = GitGoTheme.colors.chipBackground,
                                        shape = RoundedCornerShape(20.dp),
                                        border = androidx.compose.foundation.BorderStroke(
                                            1.dp,
                                            GitGoTheme.colors.primary.copy(alpha = 0.2f)
                                        )
                                    ) {
                                        Text(
                                            text = topic,
                                            color = GitGoTheme.colors.primary,
                                            style = MaterialTheme.typography.bodyMedium,
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
        colors = CardDefaults.cardColors(
            containerColor = GitGoTheme.colors.card
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            GitGoTheme.colors.outline
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Surface(
                    shape = CircleShape,
                    color = GitGoTheme.colors.primaryBackground,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = GitGoTheme.colors.primary,
                        modifier = Modifier
                            .size(20.dp)
                            .padding(8.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = title,
                    color = GitGoTheme.colors.textColor,
                    style = MaterialTheme.typography.titleLarge,
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
    color: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = GitGoTheme.colors.card
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            color.copy(alpha = 0.2f)
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
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
                    modifier = Modifier
                        .size(24.dp)
                        .padding(8.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = formatCount(count),
                color = GitGoTheme.colors.textColor,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = label,
                color = GitGoTheme.colors.textSecondary,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun InfoRow(
    icon: ImageVector,
    label: String,
    value: String,
    isUrl: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
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
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.width(120.dp)
        )
        Text(
            text = value,
            color = if (isUrl) GitGoTheme.colors.primary else GitGoTheme.colors.textColor,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (isUrl) FontWeight.Medium else FontWeight.Normal,
            modifier = Modifier.weight(1f),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

@Composable
private fun FeatureChip(
    feature: String,
    isEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        color = if (isEnabled) {
            GitGoTheme.colors.successBackground
        } else {
            GitGoTheme.colors.surfaceVariant
        },
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (isEnabled) {
                GitGoTheme.colors.success.copy(alpha = 0.3f)
            } else {
                GitGoTheme.colors.outlineVariant
            }
        ),
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
                tint = if (isEnabled) {
                    GitGoTheme.colors.success
                } else {
                    GitGoTheme.colors.textTertiary
                },
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = feature,
                color = if (isEnabled) {
                    GitGoTheme.colors.success
                } else {
                    GitGoTheme.colors.textTertiary
                },
                style = MaterialTheme.typography.bodySmall,
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

private fun formatDate(dateString: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        date?.let { outputFormat.format(it) } ?: dateString
    } catch (e: Exception) {
        dateString
    }
}