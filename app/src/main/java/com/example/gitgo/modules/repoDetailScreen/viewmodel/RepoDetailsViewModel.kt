package com.example.gitgo.modules.repoDetailScreen.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitgo.components.network.repositories.interfaces.GitHubRepoRepository
import com.example.gitgo.modules.repoDetailScreen.states.RepoDetailsResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepoDetailsViewModel @Inject constructor(
    private val gitHubRepoRepository: GitHubRepoRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
     val repoDetails =
        MutableStateFlow<RepoDetailsResponseState>(RepoDetailsResponseState.Loading)
     val owner: String = checkNotNull(savedStateHandle["owner"])
     val repo: String = checkNotNull(savedStateHandle["repo"])

    init {
        fetchRepoDetails(owner, repo)
    }
    fun fetchRepoDetails(owner: String, repo: String) {
        viewModelScope.launch {
            repoDetails.value = RepoDetailsResponseState.Loading
            try {
                val details = gitHubRepoRepository.getRepositoryDetails(owner, repo)
                if (details != null) {
                    repoDetails.value = RepoDetailsResponseState.Success(details)
                } else {
                    repoDetails.value = RepoDetailsResponseState.Error("Repository not found")
                }
            } catch (e: Exception) {
                repoDetails.value = RepoDetailsResponseState.Error(e.message ?: "Unknown error")
            }
        }
    }
}