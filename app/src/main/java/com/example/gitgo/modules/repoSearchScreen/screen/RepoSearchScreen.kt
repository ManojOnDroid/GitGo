package com.example.gitgo.modules.repoSearchScreen.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.gitgo.modules.repoSearchScreen.components.RepoCardComposable
import com.example.gitgo.modules.repoSearchScreen.viewmodel.RepoSearchViewModel
import com.example.gitgo.ui.theme.GitGoTheme

@Composable
fun RepoSearchScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: RepoSearchViewModel = hiltViewModel()
) {
    val query by viewModel.query.collectAsState()
    val repos = viewModel.repos.collectAsLazyPagingItems()
    val focusManager = LocalFocusManager.current

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
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = GitGoTheme.colors.surface.copy(alpha = 0.5f),
                shadowElevation = 0.dp
            ) {
                TextField(
                    value = query,
                    onValueChange = { viewModel.onQueryChanged(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .height(56.dp),
                    placeholder = {
                        Text(
                            "Search repositories...",
                            style = GitGoTheme.typography.bodyLarge,
                            color = GitGoTheme.colors.textSecondary
                        )
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = null,
                            tint = GitGoTheme.colors.primary
                        )
                    },
                    trailingIcon = {
                        if (query.isNotEmpty()) {
                            IconButton(onClick = { viewModel.onQueryChanged("") }) {
                                Icon(
                                    Icons.Default.Close,
                                    contentDescription = "Clear",
                                    tint = GitGoTheme.colors.textSecondary
                                )
                            }
                        }
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = GitGoTheme.colors.surface,
                        unfocusedContainerColor = GitGoTheme.colors.surface,
                        focusedIndicatorColor = Color.Transparent, // Remove underline
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = GitGoTheme.colors.textColor,
                        unfocusedTextColor = GitGoTheme.colors.textColor,
                        cursorColor = GitGoTheme.colors.primary
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() })
                )
            }

            if (query.isEmpty()) {
                EmptySearchState()
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(repos.itemCount) { index ->
                        repos[index]?.let { repo ->
                            RepoCardComposable(repo = repo, navController = navController)
                        }
                    }

                    repos.apply {
                        when {
                            loadState.refresh is LoadState.Loading -> {
                                item {
                                    Box(
                                        modifier = Modifier.fillMaxWidth().height(200.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator(color = GitGoTheme.colors.primary)
                                    }
                                }
                            }
                            loadState.append is LoadState.Loading -> {
                                item {
                                    Box(
                                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.size(24.dp),
                                            strokeWidth = 2.dp,
                                            color = GitGoTheme.colors.primary
                                        )
                                    }
                                }
                            }
                            loadState.refresh is LoadState.NotLoading && repos.itemCount < 1 -> {
                                item { NoResultsState(query) }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptySearchState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 100.dp), // Move visual center up
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            shape = CircleShape,
            color = GitGoTheme.colors.primary.copy(alpha = 0.1f),
            modifier = Modifier.size(120.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = GitGoTheme.colors.primary,
                    modifier = Modifier.size(48.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Explore GitHub",
            style = GitGoTheme.typography.titleLarge,
            color = GitGoTheme.colors.textColor,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Type in the search bar above to find\nrepositories, libraries, and tools.",
            style = GitGoTheme.typography.bodyLarge,
            color = GitGoTheme.colors.textSecondary,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun NoResultsState(query: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.SearchOff,
            contentDescription = null,
            tint = GitGoTheme.colors.textSecondary,
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No results found for \"$query\"",
            style = GitGoTheme.typography.titleMedium,
            color = GitGoTheme.colors.textColor,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = "Try checking your spelling or using different keywords.",
            style = GitGoTheme.typography.bodyMedium,
            color = GitGoTheme.colors.textSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp)
        )
    }
}