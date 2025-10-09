package com.example.gitgo.modules.profileScreen.screen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CardMembership
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.SdCard
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.gitgo.components.constants.Destination
import com.example.gitgo.modules.profileScreen.states.UserDetailsResponseState
import com.example.gitgo.modules.profileScreen.viewmodel.ProfileScreenViewModel
import com.example.gitgo.ui.theme.GitGoTheme
import com.example.gitgo.ui.theme.errorBackground
import com.example.gitgo.ui.theme.infoBackground
import com.example.gitgo.ui.theme.primaryBackground
import com.example.gitgo.ui.theme.successBackground
import com.example.gitgo.ui.theme.warningBackground
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: ProfileScreenViewModel = hiltViewModel()
) {
    val userDetailsState = viewModel.userDetails.collectAsStateWithLifecycle().value

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        GitGoTheme.colors.gradient1.copy(alpha = 0.3f),
                        GitGoTheme.colors.background,
                        GitGoTheme.colors.surfaceVariant
                    )
                )
            )
    ) {
        when (val state = userDetailsState) {
            is UserDetailsResponseState.Loading -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Surface(
                        shape = CircleShape,
                        color = GitGoTheme.colors.primaryBackground,
                        modifier = Modifier.size(80.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            CircularProgressIndicator(
                                color = GitGoTheme.colors.loaderColor,
                                strokeWidth = 4.dp,
                                modifier = Modifier.size(40.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Loading profile...",
                        color = GitGoTheme.colors.textSecondary,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            is UserDetailsResponseState.Error -> {
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
                        text = "Failed to load profile",
                        color = GitGoTheme.colors.textColor,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = state.message,
                        color = GitGoTheme.colors.textSecondary,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }

            is UserDetailsResponseState.Success -> {
                val user = state.userDetails

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Header with Avatar and Basic Info
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = GitGoTheme.colors.card
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        Brush.verticalGradient(
                                            colors = listOf(
                                                GitGoTheme.colors.primary.copy(alpha = 0.1f),
                                                GitGoTheme.colors.card
                                            )
                                        )
                                    )
                            ) {
                                Column(
                                    modifier = Modifier.padding(24.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    // Avatar
                                    Surface(
                                        modifier = Modifier.size(80.dp),
                                        shape = CircleShape,
                                        color = GitGoTheme.colors.outline,
                                        shadowElevation = 8.dp
                                    ) {
                                        GlideImage(
                                            model = user.avatarUrl,
                                            contentDescription = "Profile Picture",
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .clip(CircleShape)
                                                .border(4.dp, GitGoTheme.colors.surface, CircleShape)
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(16.dp))

                                    // Name
                                    Text(
                                        text = user.name ?: "Unknown User",
                                        style = MaterialTheme.typography.headlineMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = GitGoTheme.colors.textColor,
                                        textAlign = TextAlign.Center
                                    )

                                    Spacer(modifier = Modifier.height(4.dp))

                                    // Username
                                    Surface(
                                        color = GitGoTheme.colors.primaryBackground,
                                        shape = RoundedCornerShape(20.dp)
                                    ) {
                                        Text(
                                            text = "@${user.login ?: "unknown"}",
                                            style = MaterialTheme.typography.bodyLarge,
                                            fontWeight = FontWeight.Medium,
                                            color = GitGoTheme.colors.primary,
                                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                                        )
                                    }

                                    // Bio
                                    if (!user.bio.isNullOrBlank()) {
                                        Spacer(modifier = Modifier.height(16.dp))
                                        Text(
                                            text = user.bio,
                                            style = MaterialTheme.typography.bodyLarge,
                                            color = GitGoTheme.colors.textSecondary,
                                            textAlign = TextAlign.Center,
                                            lineHeight = 24.sp
                                        )
                                    }

                                    // Stats Row
                                    Spacer(modifier = Modifier.height(20.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceEvenly
                                    ) {
                                        StatItem(
                                            count = user.publicRepos ?: 0,
                                            label = "Repositories"
                                        )
                                        StatItem(
                                            count = user.followers ?: 0,
                                            label = "Followers"
                                        )
                                        StatItem(
                                            count = user.following ?: 0,
                                            label = "Following"
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // User Information
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = GitGoTheme.colors.card
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Column(modifier = Modifier.padding(20.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Surface(
                                        shape = CircleShape,
                                        color = GitGoTheme.colors.infoBackground,
                                        modifier = Modifier.size(36.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Info,
                                            contentDescription = null,
                                            tint = GitGoTheme.colors.info,
                                            modifier = Modifier
                                                .size(20.dp)
                                                .padding(8.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(
                                        text = "Information",
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = GitGoTheme.colors.textColor
                                    )
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                if (!user.company.isNullOrBlank()) {
                                    InfoRow(
                                        icon = Icons.Default.Business,
                                        label = "Company",
                                        value = user.company
                                    )
                                }

                                if (!user.location.isNullOrBlank()) {
                                    InfoRow(
                                        icon = Icons.Default.LocationOn,
                                        label = "Location",
                                        value = user.location
                                    )
                                }

                                if (!user.email.isNullOrBlank()) {
                                    InfoRow(
                                        icon = Icons.Default.Email,
                                        label = "Email",
                                        value = user.email
                                    )
                                }

                                if (!user.blog.isNullOrBlank()) {
                                    InfoRow(
                                        icon = Icons.Default.Link,
                                        label = "Website",
                                        value = user.blog,
                                        isLink = true
                                    )
                                }

                                if (!user.twitterUsername.isNullOrBlank()) {
                                    InfoRow(
                                        icon = Icons.Default.Tag,
                                        label = "Twitter",
                                        value = "@${user.twitterUsername}"
                                    )
                                }

                                user.createdAt?.let { date ->
                                    InfoRow(
                                        icon = Icons.Default.CalendarMonth,
                                        label = "Joined",
                                        value = formatDate(date)
                                    )
                                }

                                InfoRow(
                                    icon = Icons.Default.AccountCircle,
                                    label = "Account Type",
                                    value = user.type ?: "User"
                                )
                            }
                        }
                    }

                    // Repository Stats
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = GitGoTheme.colors.card
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Column(modifier = Modifier.padding(20.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Surface(
                                        shape = CircleShape,
                                        color = GitGoTheme.colors.successBackground,
                                        modifier = Modifier.size(36.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Folder,
                                            contentDescription = null,
                                            tint = GitGoTheme.colors.success,
                                            modifier = Modifier
                                                .size(20.dp)
                                                .padding(8.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(
                                        text = "Repository Stats",
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = GitGoTheme.colors.textColor
                                    )
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    RepoStatCard(
                                        count = user.publicRepos ?: 0,
                                        label = "Public Repos",
                                        icon = Icons.Default.Public,
                                        color = GitGoTheme.colors.success,
                                        modifier = Modifier.weight(1f)
                                    )
                                    RepoStatCard(
                                        count = user.ownedPrivateRepos ?: 0,
                                        label = "Private Repos",
                                        icon = Icons.Default.Lock,
                                        color = GitGoTheme.colors.warning,
                                        modifier = Modifier.weight(1f)
                                    )
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    RepoStatCard(
                                        count = user.publicGists ?: 0,
                                        label = "Public Gists",
                                        icon = Icons.Default.Code,
                                        color = GitGoTheme.colors.info,
                                        modifier = Modifier.weight(1f)
                                    )
                                    RepoStatCard(
                                        count = user.privateGists ?: 0,
                                        label = "Private Gists",
                                        icon = Icons.Default.VisibilityOff,
                                        color = GitGoTheme.colors.textTertiary,
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }
                        }
                    }

                    // Additional Info
                    if (user.plan != null) {
                        item {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = GitGoTheme.colors.card
                                ),
                                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                            ) {
                                Column(modifier = Modifier.padding(20.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Surface(
                                            shape = CircleShape,
                                            color = GitGoTheme.colors.warningBackground,
                                            modifier = Modifier.size(36.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Star,
                                                contentDescription = null,
                                                tint = GitGoTheme.colors.warning,
                                                modifier = Modifier
                                                    .size(20.dp)
                                                    .padding(8.dp)
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Text(
                                            text = "Plan Information",
                                            style = MaterialTheme.typography.titleLarge,
                                            fontWeight = FontWeight.Bold,
                                            color = GitGoTheme.colors.textColor
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(16.dp))

                                    InfoRow(
                                        icon = Icons.Default.CardMembership,
                                        label = "Plan",
                                        value = user.plan.name ?: "Free"
                                    )

                                    InfoRow(
                                        icon = Icons.Default.Storage,
                                        label = "Space",
                                        value = formatBytes(user.plan.space ?: 0)
                                    )

                                    user.diskUsage?.let { usage ->
                                        InfoRow(
                                            icon = Icons.Default.SdCard,
                                            label = "Disk Usage",
                                            value = formatBytes(usage)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    item { Spacer(modifier = Modifier.height(8.dp)) }
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = GitGoTheme.colors.card
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        Brush.verticalGradient(
                                            colors = listOf(
                                                GitGoTheme.colors.primary.copy(alpha = 0.1f),
                                                GitGoTheme.colors.card
                                            )
                                        )
                                    ).clickable {
                                        val context = navController.context
                                        val prefs = context.getSharedPreferences(
                                            "auth_prefs",
                                            Context.MODE_PRIVATE
                                        ).edit()
                                        prefs.remove("access_token")
                                        prefs.apply()

                                        navController.navigate(Destination.LOGIN_SCREEN) {
                                            popUpTo(navController.graph.startDestinationId) {
                                                inclusive = true
                                            }
                                        }
                                    }
                            ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(20.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Logout,
                                            contentDescription = "Logout",
                                            tint = GitGoTheme.colors.error,
                                            modifier = Modifier.size(24.dp)
                                        )
                                        Spacer(modifier = Modifier.width(16.dp))
                                        Text(
                                            text = "Logout",
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = GitGoTheme.colors.error
                                        )
                                    }
                                }
                            }
                        }
                    }
            }

            null -> Unit
        }
    }
}

@Composable
private fun StatItem(
    count: Int,
    label: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = formatCount(count),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = GitGoTheme.colors.textColor
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = GitGoTheme.colors.textSecondary
        )
    }
}

@Composable
private fun InfoRow(
    icon: ImageVector,
    label: String,
    value: String,
    isLink: Boolean = false
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
            style = MaterialTheme.typography.bodyMedium,
            color = GitGoTheme.colors.textSecondary,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.width(100.dp)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = if (isLink) GitGoTheme.colors.primary else GitGoTheme.colors.textColor,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun RepoStatCard(
    count: Int,
    label: String,
    icon: ImageVector,
    color: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = color.copy(alpha = 0.1f),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            color.copy(alpha = 0.3f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = color,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = formatCount(count),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = GitGoTheme.colors.textColor
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = GitGoTheme.colors.textSecondary,
                textAlign = TextAlign.Center,
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

private fun formatBytes(bytes: Int): String {
    return when {
        bytes >= 1_073_741_824 -> "${(bytes / 1_073_741_824.0).format(1)} GB"
        bytes >= 1_048_576 -> "${(bytes / 1_048_576.0).format(1)} MB"
        bytes >= 1024 -> "${(bytes / 1024.0).format(1)} KB"
        else -> "$bytes bytes"
    }
}

private fun formatDate(dateString: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        date?.let { outputFormat.format(it) } ?: dateString
    } catch (e: Exception) {
        dateString
    }
}

private fun Double.format(digits: Int) = "%.${digits}f".format(this).trimEnd('0').trimEnd('.')
