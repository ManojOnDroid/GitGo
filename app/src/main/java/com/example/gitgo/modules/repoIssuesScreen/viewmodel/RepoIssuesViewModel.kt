package com.example.gitgo.modules.repoIssuesScreen.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.gitgo.components.network.models.GitHubRepoIssuesModel
import com.example.gitgo.components.network.repositories.interfaces.GitHubRepoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepoIssuesViewModel @Inject constructor(
    private val gitHubRepoRepository: GitHubRepoRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _issues = MutableStateFlow<PagingData<GitHubRepoIssuesModel.GitHubRepoIssuesModelItem>>(
        PagingData.empty()
    )
    val issues: StateFlow<PagingData<GitHubRepoIssuesModel.GitHubRepoIssuesModelItem>> =
        _issues.asStateFlow()


    val owner: String = checkNotNull(savedStateHandle["owner"])
    val repo: String = checkNotNull(savedStateHandle["repo"])

    init {
        fetchIssuesWithState(owner, repo, "all")
    }

    fun fetchIssuesWithState(owner: String, repo: String, state: String) {
        viewModelScope.launch {
            gitHubRepoRepository.getRepositoryIssues(owner, repo, state).flow
                .cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    _issues.value = pagingData
                }
        }
    }

}