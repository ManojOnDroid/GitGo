package com.example.gitgo.modules.repoIssuesScreen.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.gitgo.components.network.models.GitHubRepoIssuesModel
import com.example.gitgo.components.network.repositories.interfaces.GitHubRepoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class RepoIssuesViewModel @Inject constructor(
    private val repository: GitHubRepoRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _currentFilter = MutableStateFlow("all")
    val currentFilter = _currentFilter.asStateFlow()

    val owner: String = checkNotNull(savedStateHandle["owner"])
    val repo: String = checkNotNull(savedStateHandle["repo"])

    @OptIn(ExperimentalCoroutinesApi::class)
    val issues = _currentFilter.flatMapLatest { state ->
        repository.getRepositoryIssues(owner, repo, state).flow
    }.cachedIn(viewModelScope)

    fun updateFilter(filter: String) {
        _currentFilter.value = filter
    }
}