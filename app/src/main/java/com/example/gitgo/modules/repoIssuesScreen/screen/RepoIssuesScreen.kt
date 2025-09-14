package com.example.gitgo.modules.repoIssuesScreen.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.CallMerge
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MergeType
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.gitgo.components.network.models.GitHubRepoIssuesModel
import com.example.gitgo.modules.repoIssuesScreen.viewmodel.RepoIssuesViewModel
import com.example.gitgo.ui.theme.GitGoTheme
import com.example.gitgo.ui.theme.errorBackground
import com.example.gitgo.ui.theme.infoBackground
import com.example.gitgo.ui.theme.primaryBackground
import com.example.gitgo.ui.theme.successBackground
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RepoIssuesScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: RepoIssuesViewModel = hiltViewModel()
) {
    val issues = viewModel.issues.collectAsLazyPagingItems()
    var selectedFilter by remember { mutableStateOf("all") }
    var showFilterDropdown by remember { mutableStateOf(false) }

    val filterOptions = listOf(
        IssueFilter(
            value = "all",
            label = "All Issues",
            icon = Icons.Default.List,
            color = GitGoTheme.colors.textSecondary
        ),
        IssueFilter(
            value = "open",
            label = "Open",
            icon = Icons.Default.BugReport,
            color = GitGoTheme.colors.success
        ),
        IssueFilter(
            value = "closed",
            label = "Closed",
            icon = Icons.Default.CheckCircle,
            color = GitGoTheme.colors.textTertiary
        )
    )

    // Update issues when filter changes
    LaunchedEffect(selectedFilter) {
        viewModel.fetchIssuesWithState(viewModel.owner, viewModel.repo, selectedFilter)
    }

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
        Column(modifier = Modifier.fillMaxSize()) {
            // Filter Header
            FilterHeader(
                selectedFilter = selectedFilter,
                filterOptions = filterOptions,
                showDropdown = showFilterDropdown,
                onFilterClick = { showFilterDropdown = !showFilterDropdown },
                onFilterSelected = { filter ->
                    selectedFilter = filter
                    showFilterDropdown = false
                },
                onDismissDropdown = { showFilterDropdown = false }
            )

            // Issues Content
            when (val refreshState = issues.loadState.refresh) {
                is LoadState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        LoadingState()
                    }
                }

                is LoadState.Error -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        ErrorState(
                            error = refreshState.error,
                            onRetry = { issues.retry() }
                        )
                    }
                }

                is LoadState.NotLoading -> {
                    if (issues.itemCount == 0) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            EmptyState(selectedFilter = selectedFilter)
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(issues.itemCount) { index ->
                                val issue = issues[index]
                                if (issue != null) {
                                    IssueCard(
                                        issue = issue,
                                        onIssueClick = { /* Handle issue click */ }
                                    )
                                }
                            }

                            // Loading more indicator
                            when (val appendState = issues.loadState.append) {
                                is LoadState.Loading -> {
                                    item {
                                        LoadingMoreIndicator()
                                    }
                                }

                                is LoadState.Error -> {
                                    item {
                                        LoadMoreError(
                                            error = appendState.error,
                                            onRetry = { issues.retry() }
                                        )
                                    }
                                }

                                is LoadState.NotLoading -> Unit
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FilterHeader(
    selectedFilter: String,
    filterOptions: List<IssueFilter>,
    showDropdown: Boolean,
    onFilterClick: () -> Unit,
    onFilterSelected: (String) -> Unit,
    onDismissDropdown: () -> Unit
) {
    Surface(
        color = GitGoTheme.colors.surface,
        shadowElevation = if (GitGoTheme.colors.isLight) 2.dp else 0.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Repository Issues",
                    color = GitGoTheme.colors.textColor,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                val currentFilter = filterOptions.find { it.value == selectedFilter }

                Surface(
                    onClick = onFilterClick,
                    color = GitGoTheme.colors.filterBackground,
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        GitGoTheme.colors.outline
                    ),
                ) {
                    Row {
                        Row(
                            modifier = Modifier.padding(8.dp).widthIn(min = 120.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            currentFilter?.let { filter ->
                                Icon(
                                    imageVector = filter.icon,
                                    contentDescription = null,
                                    tint = filter.color,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = filter.label,
                                    color = GitGoTheme.colors.textColor,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                imageVector = if (showDropdown) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                contentDescription = "Toggle dropdown",
                                tint = GitGoTheme.colors.iconSecondary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        DropdownMenu(
                            expanded = showDropdown,
                            onDismissRequest = onDismissDropdown,
                            modifier = Modifier.background(GitGoTheme.colors.dropdownBackground).clip(RoundedCornerShape(12.dp)),
                        ) {
                            filterOptions.forEach { filter ->
                                DropdownMenuItem(
                                    text = {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                imageVector = filter.icon,
                                                contentDescription = null,
                                                tint = filter.color,
                                                modifier = Modifier.size(18.dp)
                                            )
                                            Spacer(modifier = Modifier.width(12.dp))
                                            Text(
                                                text = filter.label,
                                                color = GitGoTheme.colors.textColor,
                                                style = MaterialTheme.typography.bodyMedium,
                                                fontWeight = if (selectedFilter == filter.value) FontWeight.Bold else FontWeight.Normal
                                            )
                                        }
                                    },
                                    onClick = { onFilterSelected(filter.value) },
                                    trailingIcon = if (selectedFilter == filter.value) {
                                        {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = "Selected",
                                                tint = GitGoTheme.colors.primary
                                            )
                                        }
                                    } else null,
                                )
                            }
                        }
                    }

                }
            }
        }
    }
}

@Composable
private fun LoadingState() {
    Column(
        modifier = Modifier.fillMaxSize(),
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
                    modifier = Modifier.size(40.dp),
                    color = GitGoTheme.colors.loaderColor,
                    strokeWidth = 4.dp
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Loading issues...",
            color = GitGoTheme.colors.textSecondary,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun ErrorState(
    error: Throwable,
    onRetry: () -> Unit
) {
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
            text = "Failed to load issues",
            color = GitGoTheme.colors.textColor,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = error.localizedMessage ?: "Unknown error occurred",
            color = GitGoTheme.colors.textSecondary,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = onRetry,
            colors = ButtonDefaults.buttonColors(
                containerColor = GitGoTheme.colors.primary
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Retry",
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun EmptyState(selectedFilter: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Surface(
            shape = CircleShape,
            color = GitGoTheme.colors.infoBackground,
            modifier = Modifier.size(80.dp)
        ) {
            Icon(
                imageVector = when (selectedFilter) {
                    "open" -> Icons.Default.BugReport
                    "closed" -> Icons.Default.CheckCircle
                    else -> Icons.Default.Inbox
                },
                contentDescription = "No issues",
                tint = when (selectedFilter) {
                    "open" -> GitGoTheme.colors.success
                    "closed" -> GitGoTheme.colors.textTertiary
                    else -> GitGoTheme.colors.info
                },
                modifier = Modifier
                    .size(40.dp)
                    .padding(20.dp)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = when (selectedFilter) {
                "open" -> "No open issues"
                "closed" -> "No closed issues"
                else -> "No issues found"
            },
            color = GitGoTheme.colors.textColor,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = when (selectedFilter) {
                "open" -> "This repository doesn't have any open issues"
                "closed" -> "This repository doesn't have any closed issues"
                else -> "This repository doesn't have any issues"
            },
            color = GitGoTheme.colors.textSecondary,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun IssueCard(
    issue: GitHubRepoIssuesModel.GitHubRepoIssuesModelItem,
    onIssueClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = GitGoTheme.colors.card
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            GitGoTheme.colors.outline
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Header with issue state and number
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IssueStateChip(
                    state = issue.state ?: "open",
                    isPullRequest = issue.pullRequest != null
                )

                Text(
                    text = "#${issue.number}",
                    color = GitGoTheme.colors.textTertiary,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Issue title
            Text(
                text = issue.title ?: "No title",
                color = GitGoTheme.colors.textColor,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 22.sp
            )

            // Issue body preview if available
            if (!issue.body.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = issue.body,
                    color = GitGoTheme.colors.textSecondary,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Labels if available
            if (!issue.labels.isNullOrEmpty()) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(issue.labels.filterNotNull().take(3)) { label ->
                        LabelChip(label = label)
                    }
                    if (issue.labels.size > 3) {
                        item {
                            Surface(
                                color = GitGoTheme.colors.surfaceVariant,
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    text = "+${issue.labels.size - 3}",
                                    color = GitGoTheme.colors.textTertiary,
                                    style = MaterialTheme.typography.labelSmall,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Footer with user info and metadata
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // User avatar and name
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = GitGoTheme.colors.outline,
                        modifier = Modifier.size(34.dp)
                    ) {
                        GlideImage(
                            model = issue.user?.avatarUrl,
                            contentDescription = issue.user?.login,
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .padding(1.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = issue.user?.login ?: "Unknown",
                            color = GitGoTheme.colors.textColor,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = formatDate(issue.createdAt),
                            color = GitGoTheme.colors.textTertiary,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                // Comments count and assignees
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if ((issue.comments ?: 0) > 0) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Comment,
                                contentDescription = "Comments",
                                tint = GitGoTheme.colors.iconSecondary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${issue.comments}",
                                color = GitGoTheme.colors.textSecondary,
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    // Assignees
                    if (!issue.assignees.isNullOrEmpty()) {
                        Row {
                            issue.assignees.filterNotNull().take(2).forEach { assignee ->
                                Surface(
                                    shape = CircleShape,
                                    color = GitGoTheme.colors.outline,
                                    modifier = Modifier
                                        .size(20.dp)
                                        .border(1.dp, GitGoTheme.colors.card, CircleShape)
                                ) {
                                    GlideImage(
                                        model = assignee.avatarUrl,
                                        contentDescription = assignee.login,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clip(CircleShape)
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

@Composable
private fun IssueStateChip(
    state: String,
    isPullRequest: Boolean
) {
    val (icon, color, backgroundColor) = when {
        isPullRequest && state == "open" -> Triple(
            Icons.Default.CallMerge,
            GitGoTheme.colors.info,
            GitGoTheme.colors.infoBackground
        )

        isPullRequest && state == "closed" -> Triple(
            Icons.Default.MergeType,
            GitGoTheme.colors.success,
            GitGoTheme.colors.successBackground
        )

        state == "open" -> Triple(
            Icons.Default.BugReport,
            GitGoTheme.colors.success,
            GitGoTheme.colors.successBackground
        )

        else -> Triple(
            Icons.Default.CheckCircle,
            GitGoTheme.colors.textTertiary,
            GitGoTheme.colors.surfaceVariant
        )
    }

    Surface(
        color = backgroundColor,
        shape = RoundedCornerShape(20.dp),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            color.copy(alpha = 0.3f)
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = if (isPullRequest) {
                    if (state == "open") "Open PR" else "Merged"
                } else {
                    state.replaceFirstChar { it.uppercaseChar() }
                },
                color = color,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun LabelChip(
    label: GitHubRepoIssuesModel.GitHubRepoIssuesModelItem.Label
) {
    val labelColor = try {
        Color(android.graphics.Color.parseColor("#${label.color}"))
    } catch (e: Exception) {
        GitGoTheme.colors.primary
    }

    Surface(
        color = labelColor.copy(alpha = 0.1f),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            labelColor.copy(alpha = 0.3f)
        )
    ) {
        Text(
            text = label.name ?: "Unknown",
            color = labelColor,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun LoadingMoreIndicator() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(32.dp),
            color = GitGoTheme.colors.loaderColor,
            strokeWidth = 3.dp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Loading more issues...",
            color = GitGoTheme.colors.textSecondary,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
private fun LoadMoreError(
    error: Throwable,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Failed to load more issues",
            color = GitGoTheme.colors.error,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(
            onClick = onRetry,
            colors = ButtonDefaults.textButtonColors(
                contentColor = GitGoTheme.colors.primary
            )
        ) {
            Text(
                text = "Try Again",
                fontWeight = FontWeight.Medium
            )
        }
    }
}

private fun formatDate(dateString: String?): String {
    if (dateString == null) return "Unknown"

    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("MMM dd", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        date?.let { outputFormat.format(it) } ?: "Unknown"
    } catch (e: Exception) {
        "Unknown"
    }
}

data class IssueFilter(
    val value: String,
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val color: Color
)