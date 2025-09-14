package com.example.gitgo.modules.repoSearchScreen.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.gitgo.modules.repoSearchScreen.components.RepoCardComposable
import com.example.gitgo.modules.repoSearchScreen.viewmodel.RepoSearchViewModel
import com.example.gitgo.ui.theme.GitGoTheme
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RepoSearchScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: RepoSearchViewModel = hiltViewModel()
) {
    val query = remember {
        mutableStateOf("")
    }
    val coroutineScope = rememberCoroutineScope()
    val searchJob = remember {
        mutableStateOf<Job?>(null)
    }
    val repos = viewModel.repos.collectAsLazyPagingItems()
    Box(modifier = modifier.fillMaxSize()) {
        Column {
            Text(text = "Search Public Repos Here", color = GitGoTheme.colors.textColor)
            OutlinedTextField(
                value = query.value,
                onValueChange = {
                    query.value = it
                    searchJob.value?.cancel()
                    searchJob.value = coroutineScope.launch {
                        delay(400)
                        if (query.value.isNotEmpty()) {
                            viewModel.searchRepositories(query.value)
                        }
                    }
                },
                label = {
                    Text(text = "Search")
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = GitGoTheme.colors.floatingColor,
                    unfocusedBorderColor = GitGoTheme.colors.textColor,
                    focusedTextColor = GitGoTheme.colors.floatingColor,
                    unfocusedTextColor = GitGoTheme.colors.textColor
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
            )
            LazyColumn {
                items(repos.itemCount) { index ->
                    val repo = repos[index]
                    if (repo != null) {
                        RepoCardComposable(repo = repo,navController)
                    }
                }

                when (val state = repos.loadState.append) {
                    is LoadState.Loading -> {
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth()
                                    .padding(horizontal = 10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(40.dp),
                                    color = GitGoTheme.colors.loaderColor,
                                    strokeWidth = 3.dp
                                )
                            }
                        }
                    }

                    is LoadState.Error -> {

                    }

                    is LoadState.NotLoading -> {

                    }
                }
            }
        }
    }
}