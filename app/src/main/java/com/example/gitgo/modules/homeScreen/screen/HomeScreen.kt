package com.example.gitgo.modules.homeScreen.screen

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CallSplit
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalDensity
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
import com.example.gitgo.components.constants.Destination
import com.example.gitgo.modules.homeScreen.components.CollapsingHomeHeader
import com.example.gitgo.modules.homeScreen.components.FeaturesSection
import com.example.gitgo.modules.homeScreen.components.HomeBackgroundDecor
import com.example.gitgo.modules.homeScreen.components.QuickActionsSection
import com.example.gitgo.modules.homeScreen.components.SearchButton
import com.example.gitgo.modules.homeScreen.components.TrendingHeader
import com.example.gitgo.modules.homeScreen.components.WelcomeCard
import com.example.gitgo.modules.homeScreen.states.TrendingReposState
import com.example.gitgo.modules.homeScreen.viewmodel.HomeScreenViewModel
import com.example.gitgo.ui.theme.GitGoTheme
import com.example.gitgo.ui.theme.errorBackground
import com.example.gitgo.ui.theme.primaryBackground


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val trendingState by viewModel.state.collectAsState()
    val scrollState = rememberLazyListState()
    val collapseRange = 200.dp
    val collapseRangePx = with(LocalDensity.current) { collapseRange.toPx() }
    val collapseFraction by remember {
        derivedStateOf {
            val firstItemIndex = scrollState.firstVisibleItemIndex
            val firstItemOffset = scrollState.firstVisibleItemScrollOffset.toFloat()

            if (firstItemIndex > 0) {
                1f
            } else {
                (firstItemOffset / collapseRangePx).coerceIn(0f, 1f)
            }
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
                    ),
                    startY = 0f,
                    endY = 1200f
                )
            )
    ) {
        HomeBackgroundDecor()

        LazyColumn(
            state = scrollState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = 220.dp, bottom = 24.dp, start = 24.dp, end = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                WelcomeCard()
            }

            item {
                SearchButton { navController.navigate(Destination.REPO_SEARCH_SCREEN) }
            }

            item {
                FeaturesSection()
            }
            item {
                QuickActionsSection()
            }

            item {
                TrendingHeader()
            }

            item {
                Column {
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
                                                        val owner =
                                                            it.owner?.login ?: return@clickable
                                                        val name = it.name ?: return@clickable
                                                        navController.navigate("repoDetails/$owner/$name")
                                                    },
                                                shape = RoundedCornerShape(16.dp),
                                                elevation = CardDefaults.cardElevation(
                                                    defaultElevation = 6.dp
                                                ),
                                                colors = CardDefaults.cardColors(
                                                    containerColor = GitGoTheme.colors.surface.copy(
                                                        alpha = 0.95f
                                                    )
                                                ),
                                                border = BorderStroke(
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
                                                        text = it.description?.take(80)
                                                            ?: "No description available",
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
                                                                    modifier = Modifier.padding(
                                                                        horizontal = 8.dp,
                                                                        vertical = 2.dp
                                                                    )
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
                                                                    text = formatCount(
                                                                        it.stargazersCount ?: 0
                                                                    ),
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
                                                                    text = formatCount(
                                                                        it.forksCount ?: 0
                                                                    ),
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp + (186.dp * (1 - collapseFraction)))
                .background(
                    GitGoTheme.colors.surface.copy(alpha = if (collapseFraction > 0.8f) 0.95f else 0f)
                )
                .align(Alignment.TopCenter)
        ) {
            CollapsingHomeHeader(collapseFraction = collapseFraction)
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






