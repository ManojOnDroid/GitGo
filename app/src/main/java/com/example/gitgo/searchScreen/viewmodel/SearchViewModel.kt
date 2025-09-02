package com.example.gitgo.searchScreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitgo.components.network.models.GitHubSearchRepoModel
import com.example.gitgo.components.network.repositories.interfaces.GitHubSearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val gitHubSearchRepository: GitHubSearchRepository
) : ViewModel() {
    val repos = MutableStateFlow<GitHubSearchRepoModel?>(GitHubSearchRepoModel())
    fun searchRepositories(query: String) {
        viewModelScope.launch {
            repos.value = gitHubSearchRepository.searchRepositories(query)
        }
    }
}