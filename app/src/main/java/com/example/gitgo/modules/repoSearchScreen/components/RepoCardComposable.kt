package com.example.gitgo.modules.repoSearchScreen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ForkRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
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
            .padding(8.dp)
            .clickable{
                navController.navigate("repoDetails/${repo.owner?.login}/${repo.name}")
            },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(
            containerColor = GitGoTheme.colors.card
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                GlideImage(
                    model = repo.owner?.avatarUrl,
                    contentDescription = "Owner Avatar",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(end = 8.dp)
                )

                Column {
                    Text(
                        text = repo.fullName ?: repo.name ?: "Unknown Repo",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = GitGoTheme.colors.textColor
                    )
                    Text(
                        text = repo.owner?.login ?: "Unknown Owner",
                        style = MaterialTheme.typography.bodySmall,
                        color = GitGoTheme.colors.textColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            repo.description?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    color = GitGoTheme.colors.textColor
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(Icons.Filled.Star, contentDescription = "Stars")
                Text(text = "${repo.stargazersCount ?: 0}",color = GitGoTheme.colors.textColor
                )

                Icon(Icons.Filled.ForkRight, contentDescription = "Forks")
                Text(text = "${repo.forksCount ?: 0}",color = GitGoTheme.colors.textColor
                )

                repo.license?.name?.let { license ->
                    Icon(Icons.Filled.Info, contentDescription = "License")
                    Text(text = license,color = GitGoTheme.colors.textColor)
                }
            }


            Spacer(modifier = Modifier.height(8.dp))

            repo.updatedAt?.let {
                Text(
                    text = "Last updated: $it",
                    style = MaterialTheme.typography.bodySmall,
                    color = GitGoTheme.colors.textColor
                )
            }
        }
    }
}
