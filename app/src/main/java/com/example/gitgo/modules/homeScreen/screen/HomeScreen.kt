package com.example.gitgo.modules.homeScreen.screen

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.CallSplit
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.WavingHand
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.gitgo.modules.homeScreen.states.TrendingReposState
import com.example.gitgo.modules.homeScreen.viewmodel.HomeScreenViewModel
import com.example.gitgo.ui.theme.GitGoTheme
import com.example.gitgo.ui.theme.errorBackground
import com.example.gitgo.ui.theme.onPrimary
import com.example.gitgo.ui.theme.primaryBackground


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val trendingState by viewModel.repos.collectAsState()
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        GitGoTheme.colors.gradient1,
                        GitGoTheme.colors.gradient2,
                        GitGoTheme.colors.background.copy(alpha = 0.9f),
                        GitGoTheme.colors.surfaceVariant
                    ),
                    startY = 0f,
                    endY = 1500f
                )
            )
    ) {
        // Background decorations
        Box(modifier = Modifier.fillMaxSize()) {
            Surface(
                modifier = Modifier
                    .size(150.dp)
                    .offset(x = 100.dp, y = (-50).dp)
                    .align(Alignment.TopEnd),
                shape = CircleShape,
                color = GitGoTheme.colors.primary.copy(alpha = 0.1f)
            ) {}

            Surface(
                modifier = Modifier
                    .size(120.dp)
                    .offset(x = (-30).dp, y = 50.dp)
                    .align(Alignment.BottomStart),
                shape = CircleShape,
                color = GitGoTheme.colors.secondary.copy(alpha = 0.08f)
            ) {}

            Surface(
                modifier = Modifier
                    .size(80.dp)
                    .offset(x = 40.dp)
                    .align(Alignment.CenterEnd),
                shape = CircleShape,
                color = GitGoTheme.colors.info.copy(alpha = 0.06f)
            ) {}
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item { Spacer(modifier = Modifier.height(36.dp)) }

            // App Header
            item {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Surface(
                        modifier = Modifier.size(100.dp),
                        shape = RoundedCornerShape(24.dp),
                        color = GitGoTheme.colors.surface,
                        shadowElevation = 12.dp
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.linearGradient(
                                        colors = listOf(
                                            GitGoTheme.colors.primary,
                                            GitGoTheme.colors.secondary
                                        )
                                    )
                                )
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccountTree,
                                contentDescription = "GitGo Logo",
                                tint = GitGoTheme.colors.onPrimary,
                                modifier = Modifier.size(50.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "GitGo",
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = GitGoTheme.colors.surface,
                        letterSpacing = 1.sp
                    )

                    Text(
                        text = "Explore GitHub Repositories",
                        style = MaterialTheme.typography.bodyLarge,
                        color = GitGoTheme.colors.surface.copy(alpha = 0.8f),
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Welcome Section
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = GitGoTheme.colors.surface.copy(alpha = 0.9f)
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.WavingHand,
                            contentDescription = "Welcome",
                            tint = GitGoTheme.colors.primary,
                            modifier = Modifier.size(32.dp)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Welcome to GitGo!",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = GitGoTheme.colors.textColor,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Discover and explore GitHub repositories with ease. Search for projects, view repository details, track issues, and stay connected with the developer community.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = GitGoTheme.colors.textSecondary,
                            textAlign = TextAlign.Center,
                            lineHeight = 24.sp
                        )
                    }
                }
            }

            // Main Action Button
            item {
                Button(
                    onClick = { navController.navigate("repoSearch") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GitGoTheme.colors.primary
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 8.dp,
                        pressedElevation = 4.dp
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Start Exploring Repositories",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }
            }

            // Feature Cards
            item {
                Column {
                    Text(
                        text = "What You Can Do",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = GitGoTheme.colors.surface,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Feature items
                    val features = listOf(
                        Triple(Icons.Default.Search, "Search Repositories", "Find GitHub repositories by name, description, or programming language") to GitGoTheme.colors.info,
                        Triple(Icons.Default.Visibility, "Repository Details", "View comprehensive information about repositories including stats and features") to GitGoTheme.colors.success,
                        Triple(Icons.Default.BugReport, "Track Issues", "Browse and filter repository issues to stay updated with project status") to GitGoTheme.colors.warning
                    )

                    features.forEach { (feature, color) ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = GitGoTheme.colors.surface.copy(alpha = 0.9f)
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(20.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    modifier = Modifier.size(50.dp),
                                    shape = CircleShape,
                                    color = color.copy(alpha = 0.1f)
                                ) {
                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = Modifier.fillMaxSize()
                                    ) {
                                        Icon(
                                            imageVector = feature.first,
                                            contentDescription = feature.second,
                                            tint = color,
                                            modifier = Modifier.size(24.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.width(16.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = feature.second,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = GitGoTheme.colors.textColor
                                    )

                                    Spacer(modifier = Modifier.height(4.dp))

                                    Text(
                                        text = feature.third,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = GitGoTheme.colors.textSecondary,
                                        lineHeight = 20.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Quick Actions
            item {
                Column {
                    Text(
                        text = "Quick Actions",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = GitGoTheme.colors.surface.copy(alpha = 0.9f),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        listOf(
                            Triple(Icons.Default.TrendingUp, "Trending") { },
                            Triple(Icons.Default.Star, "Popular") { },
                            Triple(Icons.Default.History, "Recent") {}
                        ).forEach { (icon, label, onClick) ->
                            Card(
                                onClick = onClick,
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = GitGoTheme.colors.surface.copy(alpha = 0.8f)
                                ),
                                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = label,
                                        tint = GitGoTheme.colors.primary,
                                        modifier = Modifier.size(24.dp)
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Text(
                                        text = label,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Medium,
                                        color = GitGoTheme.colors.textColor,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Trending Repositories Section
            item {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Trending Repositories",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = GitGoTheme.colors.surface
                        )

                        Icon(
                            imageVector = Icons.Default.TrendingUp,
                            contentDescription = "Trending",
                            tint = GitGoTheme.colors.surface.copy(alpha = 0.8f),
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    when (val state = trendingState) {
                        is TrendingReposState.Loading -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    CircularProgressIndicator(
                                        color = GitGoTheme.colors.primary,
                                        modifier = Modifier.size(40.dp)
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Text(
                                        text = "Loading trending repositories...",
                                        color = GitGoTheme.colors.surface.copy(alpha = 0.8f),
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }

                        is TrendingReposState.Error -> {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = GitGoTheme.colors.errorBackground
                                )
                            ) {
                                Column(
                                    modifier = Modifier.padding(20.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Error,
                                        contentDescription = "Error",
                                        tint = GitGoTheme.colors.error,
                                        modifier = Modifier.size(32.dp)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "Failed to load trending repositories",
                                        color = GitGoTheme.colors.error,
                                        style = MaterialTheme.typography.bodyMedium,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }

                        is TrendingReposState.Success -> {
                            val lazyPagingItems = state.items.collectAsLazyPagingItems()

                            if (lazyPagingItems.itemCount > 0) {
                                LazyVerticalGrid(
                                    columns = GridCells.Fixed(2),
                                    modifier = Modifier.height(600.dp),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    verticalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    items(minOf(lazyPagingItems.itemCount, 6)) { index ->
                                        val repo = lazyPagingItems[index]
                                        repo?.let {
                                            Card(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(180.dp)
                                                    .clickable {
                                                        val owner = it.owner?.login ?: return@clickable
                                                        val name = it.name ?: return@clickable
                                                        navController.navigate("repoDetails/$owner/$name")
                                                    },
                                                shape = RoundedCornerShape(16.dp),
                                                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                                                colors = CardDefaults.cardColors(
                                                    containerColor = GitGoTheme.colors.surface.copy(alpha = 0.95f)
                                                ),
                                                border = androidx.compose.foundation.BorderStroke(
                                                    1.dp,
                                                    GitGoTheme.colors.outline.copy(alpha = 0.3f)
                                                )
                                            ) {
                                                Column(
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                        .padding(16.dp)
                                                ) {
                                                    // Owner avatar and name
                                                    Row(
                                                        verticalAlignment = Alignment.CenterVertically,
                                                        modifier = Modifier.fillMaxWidth()
                                                    ) {
                                                        Surface(
                                                            shape = CircleShape,
                                                            color = GitGoTheme.colors.outline,
                                                            modifier = Modifier.size(32.dp)
                                                        ) {
                                                            GlideImage(
                                                                model = it.owner?.avatarUrl,
                                                                contentDescription = it.owner?.login,
                                                                modifier = Modifier
                                                                    .fillMaxSize()
                                                                    .clip(CircleShape)
                                                            )
                                                        }
                                                        Spacer(modifier = Modifier.width(8.dp))
                                                        Text(
                                                            text = it.owner?.login ?: "Unknown",
                                                            style = MaterialTheme.typography.bodySmall,
                                                            color = GitGoTheme.colors.textSecondary,
                                                            fontWeight = FontWeight.Medium,
                                                            maxLines = 1,
                                                            overflow = TextOverflow.Ellipsis,
                                                            modifier = Modifier.weight(1f)
                                                        )
                                                    }

                                                    Spacer(modifier = Modifier.height(8.dp))

                                                    // Repository name
                                                    Text(
                                                        text = it.name ?: "Unknown Repo",
                                                        style = MaterialTheme.typography.titleSmall,
                                                        fontWeight = FontWeight.Bold,
                                                        color = GitGoTheme.colors.textColor,
                                                        maxLines = 1,
                                                        overflow = TextOverflow.Ellipsis
                                                    )

                                                    Spacer(modifier = Modifier.height(6.dp))

                                                    // Description
                                                    Text(
                                                        text = it.description?.take(80) ?: "No description available",
                                                        style = MaterialTheme.typography.bodySmall,
                                                        color = GitGoTheme.colors.textSecondary,
                                                        maxLines = 3,
                                                        overflow = TextOverflow.Ellipsis,
                                                        lineHeight = 16.sp,
                                                        modifier = Modifier.weight(1f)
                                                    )

                                                    Spacer(modifier = Modifier.height(8.dp))

                                                    // Language and stats
                                                    Column {
                                                        if (it.language != null) {
                                                            Surface(
                                                                color = GitGoTheme.colors.primaryBackground,
                                                                shape = RoundedCornerShape(8.dp)
                                                            ) {
                                                                Text(
                                                                    text = it.language,
                                                                    style = MaterialTheme.typography.labelSmall,
                                                                    color = GitGoTheme.colors.primary,
                                                                    fontWeight = FontWeight.Medium,
                                                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                                                )
                                                            }
                                                            Spacer(modifier = Modifier.height(6.dp))
                                                        }

                                                        Row(
                                                            modifier = Modifier.fillMaxWidth(),
                                                            horizontalArrangement = Arrangement.SpaceBetween
                                                        ) {
                                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                                Icon(
                                                                    Icons.Default.Star,
                                                                    contentDescription = "Stars",
                                                                    tint = GitGoTheme.colors.warning,
                                                                    modifier = Modifier.size(12.dp)
                                                                )
                                                                Spacer(modifier = Modifier.width(4.dp))
                                                                Text(
                                                                    text = formatCount(it.stargazersCount ?: 0),
                                                                    style = MaterialTheme.typography.labelSmall,
                                                                    color = GitGoTheme.colors.textSecondary
                                                                )
                                                            }

                                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                                Icon(
                                                                    Icons.Default.CallSplit,
                                                                    contentDescription = "Forks",
                                                                    tint = GitGoTheme.colors.info,
                                                                    modifier = Modifier.size(12.dp)
                                                                )
                                                                Spacer(modifier = Modifier.width(4.dp))
                                                                Text(
                                                                    text = formatCount(it.forksCount ?: 0),
                                                                    style = MaterialTheme.typography.labelSmall,
                                                                    color = GitGoTheme.colors.textSecondary
                                                                )
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(20.dp)) }
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

private fun Double.format(digits: Int) = "%.${digits}f".format(this).trimEnd('0').trimEnd('.')






