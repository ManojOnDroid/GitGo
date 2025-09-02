package com.example.gitgo.searchScreen.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gitgo.searchScreen.components.RepoCardComposable
import com.example.gitgo.searchScreen.viewmodel.SearchViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val query = remember {
        mutableStateOf("")
    }
    val coroutineScope = rememberCoroutineScope()
    val searchJob = remember {
        mutableStateOf<Job?>(null)
    }
    val repos by viewModel.repos.collectAsState()
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopStart
    ) {
        Column {
            Text(text = "Search Public Repos Here")
            OutlinedTextField(
                value = query.value,
                onValueChange = {
                    query.value = it
                    searchJob.value?.cancel()
                    searchJob.value = coroutineScope.launch {
                        delay(300)
                        if (query.value.isNotEmpty()) {
                            viewModel.searchRepositories(query.value)
                        }
                    }
                },
                label = {
                    Text(text = "Search")
                }
            )
            LazyColumn {
                repos?.items?.filterNotNull()?.let { repoList ->
                    items(repoList.size) { index ->
                        RepoCardComposable(repo = repoList[index])
                    }
                }
            }
        }
    }
}