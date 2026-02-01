package com.example.gitgo.modules.repoSearchScreen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CallSplit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.gitgo.components.network.models.GitHubSearchRepoModel
import com.example.gitgo.ui.theme.GitGoTheme

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RepoCardComposable(repo: GitHubSearchRepoModel.Item, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate("repoDetails/${repo.owner?.login}/${repo.name}")
            },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp), // Subtle elevation
        colors = CardDefaults.cardColors(
            containerColor = GitGoTheme.colors.surface
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header: Avatar + Name
            Row(verticalAlignment = Alignment.CenterVertically) {
                GlideImage(
                    model = repo.owner?.avatarUrl,
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = repo.owner?.login ?: "Unknown",
                        style = GitGoTheme.typography.bodySmall,
                        color = GitGoTheme.colors.textSecondary
                    )
                    Text(
                        text = repo.name ?: "Unknown Repo",
                        style = GitGoTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = GitGoTheme.colors.textColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Description
            if (!repo.description.isNullOrBlank()) {
                Text(
                    text = repo.description,
                    style = GitGoTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = GitGoTheme.colors.textColor,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Stats Footer
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Language Badge
                if (!repo.language.isNullOrEmpty()) {
                    Surface(
                        color = GitGoTheme.colors.primary.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = repo.language,
                            style = GitGoTheme.typography.labelSmall,
                            color = GitGoTheme.colors.primary,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                // Stars
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        tint = GitGoTheme.colors.warning,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = formatCount(repo.stargazersCount ?: 0),
                        style = GitGoTheme.typography.labelSmall,
                        color = GitGoTheme.colors.textSecondary
                    )
                }

                // Forks
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.CallSplit,
                        contentDescription = null,
                        tint = GitGoTheme.colors.textSecondary,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = formatCount(repo.forksCount ?: 0),
                        style = GitGoTheme.typography.labelSmall,
                        color = GitGoTheme.colors.textSecondary
                    )
                }
            }
        }
    }
}

// Simple helper to make numbers look nice (1200 -> 1.2k)
private fun formatCount(count: Int): String {
    return when {
        count >= 1_000_000 -> "%.1fM".format(count / 1_000_000.0)
        count >= 1_000 -> "%.1fk".format(count / 1_000.0)
        else -> count.toString()
    }
}