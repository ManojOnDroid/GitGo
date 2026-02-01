package com.example.gitgo.modules.repoIssuesScreen.screen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.CallMerge
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.gitgo.R
import com.example.gitgo.components.network.models.GitHubRepoIssuesModel
import com.example.gitgo.modules.repoIssuesScreen.models.IssueFilterItem
import com.example.gitgo.modules.repoIssuesScreen.utils.IssueUtils
import com.example.gitgo.modules.repoIssuesScreen.viewmodel.RepoIssuesViewModel
import com.example.gitgo.ui.theme.GitGoTheme
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun RepoIssuesScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: RepoIssuesViewModel = hiltViewModel()
) {
    val issues = viewModel.issues.collectAsLazyPagingItems()
    val selectedFilter by viewModel.currentFilter.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(issues.loadState) {
        val errorState = issues.loadState.refresh as? LoadState.Error
            ?: issues.loadState.append as? LoadState.Error
        errorState?.let {
            Toast.makeText(context, it.error.localizedMessage, Toast.LENGTH_SHORT).show()
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
        Column(modifier = Modifier.fillMaxSize()) {

            IssuesFilterHeader(
                selectedFilter = selectedFilter,
                onFilterSelected = { viewModel.updateFilter(it) }
            )

            when (val refreshState = issues.loadState.refresh) {
                is LoadState.Loading -> LoadingView()
                is LoadState.Error -> ErrorView(refreshState.error) { issues.retry() }
                is LoadState.NotLoading -> {
                    if (issues.itemCount == 0) {
                        EmptyView()
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(issues.itemCount) { index ->
                                issues[index]?.let { issue ->
                                    IssueCard(issue)
                                }
                            }

                            when (val appendState = issues.loadState.append) {
                                is LoadState.Loading -> item { LoadingMoreView() }
                                is LoadState.Error -> item { ErrorMoreView { issues.retry() } }
                                else -> Unit
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun IssuesFilterHeader(
    selectedFilter: String,
    onFilterSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    val filterOptions = listOf(
        IssueFilterItem(
            "all",
            R.string.filter_all,
            Icons.Default.List,
            GitGoTheme.colors.textSecondary
        ),
        IssueFilterItem("open", R.string.filter_open, Icons.Default.BugReport, GitGoTheme.colors.success),
        IssueFilterItem("closed", R.string.filter_closed, Icons.Default.CheckCircle, GitGoTheme.colors.textTertiary)
    )
    val currentOption = filterOptions.find { it.value == selectedFilter } ?: filterOptions.first()

    Surface(
        color = GitGoTheme.colors.surface,
        shadowElevation = 4.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.issues_title),
                style = GitGoTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = GitGoTheme.colors.textColor
            )

            Box {
                Surface(
                    onClick = { expanded = true },
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, GitGoTheme.colors.outline),
                    color = GitGoTheme.colors.surface
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(currentOption.icon, null, tint = currentOption.color, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = stringResource(currentOption.labelRes),
                            style = GitGoTheme.typography.bodyMedium,
                            color = GitGoTheme.colors.textColor
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(Icons.Default.ExpandMore, null, tint = GitGoTheme.colors.textSecondary, modifier = Modifier.size(16.dp))
                    }
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.background(GitGoTheme.colors.surface)
                ) {
                    filterOptions.forEach { option ->
                        DropdownMenuItem(
                            text = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(option.icon, null, tint = option.color, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(stringResource(option.labelRes), color = GitGoTheme.colors.textColor)
                                }
                            },
                            onClick = {
                                onFilterSelected(option.value)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun IssueCard(issue: GitHubRepoIssuesModel.GitHubRepoIssuesModelItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = GitGoTheme.colors.card),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StateChip(state = issue.state ?: "open", isPr = issue.pullRequest != null)
                Text(
                    text = "#${issue.number}",
                    style = GitGoTheme.typography.bodySmall,
                    color = GitGoTheme.colors.textSecondary
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = issue.title ?: "No Title",
                style = GitGoTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = GitGoTheme.colors.textColor,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            if (!issue.labels.isNullOrEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(issue.labels.filterNotNull()) { label ->
                        LabelChip(label)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = GitGoTheme.colors.outline.copy(alpha = 0.3f))
            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(shape = CircleShape, modifier = Modifier.size(24.dp)) {
                    GlideImage(model = issue.user?.avatarUrl, contentDescription = null)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = issue.user?.login ?: "Unknown",
                    style = GitGoTheme.typography.bodySmall,
                    color = GitGoTheme.colors.textColor
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(Icons.Default.Comment, null, tint = GitGoTheme.colors.textSecondary, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${issue.comments ?: 0}",
                    style = GitGoTheme.typography.bodySmall,
                    color = GitGoTheme.colors.textSecondary
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = IssueUtils.formatDate(issue.createdAt),
                    style = GitGoTheme.typography.bodySmall,
                    color = GitGoTheme.colors.textSecondary
                )
            }
        }
    }
}

@Composable
private fun StateChip(state: String, isPr: Boolean) {
    val isOpen = state == "open"
    val color = if (isOpen) GitGoTheme.colors.success else GitGoTheme.colors.primary
    val icon = if (isPr) Icons.Default.CallMerge else if (isOpen) Icons.Default.BugReport else Icons.Default.CheckCircle

    Surface(
        color = color.copy(alpha = 0.1f),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, color.copy(alpha = 0.2f))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, null, tint = color, modifier = Modifier.size(12.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = state.replaceFirstChar { it.uppercase() },
                style = GitGoTheme.typography.labelSmall,
                color = color,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun LabelChip(label: GitHubRepoIssuesModel.GitHubRepoIssuesModelItem.Label) {
    val color = IssueUtils.parseColor(label.color)
    Surface(
        color = color.copy(alpha = 0.15f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = label.name ?: "",
            style = GitGoTheme.typography.labelSmall,
            color = GitGoTheme.colors.textColor,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
        )
    }
}


@Composable
private fun LoadingView() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = GitGoTheme.colors.primary)
    }
}

@Composable
private fun EmptyView() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(Icons.Default.Inbox, null, tint = GitGoTheme.colors.textSecondary, modifier = Modifier.size(64.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text(stringResource(R.string.issue_empty), style = GitGoTheme.typography.titleMedium, color = GitGoTheme.colors.textColor)
    }
}

@Composable
private fun ErrorView(error: Throwable, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Error: ${error.localizedMessage}", color = GitGoTheme.colors.error)
        Button(onClick = onRetry) { Text(stringResource(R.string.issue_retry)) }
    }
}

@Composable
private fun LoadingMoreView() {
    Box(modifier = Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(modifier = Modifier.size(24.dp), strokeWidth = 2.dp, color = GitGoTheme.colors.primary)
    }
}

@Composable
private fun ErrorMoreView(onRetry: () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
        TextButton(onClick = onRetry) { Text("Error loading more. Retry?") }
    }
}