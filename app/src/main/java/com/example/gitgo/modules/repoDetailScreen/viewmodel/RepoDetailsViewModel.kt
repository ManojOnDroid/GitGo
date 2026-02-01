package com.example.gitgo.modules.repoDetailScreen.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitgo.components.network.base.NetworkResult
import com.example.gitgo.components.network.repositories.interfaces.GitHubRepoRepository
import com.example.gitgo.modules.repoDetailScreen.states.RepoDetailsResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepoDetailsViewModel @Inject constructor(
    private val gitHubRepoRepository: GitHubRepoRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _repoDetails = MutableStateFlow<RepoDetailsResponseState>(RepoDetailsResponseState.Loading)
    val repoDetails = _repoDetails.asStateFlow()
    val owner: String = checkNotNull(savedStateHandle["owner"])
    val repo: String = checkNotNull(savedStateHandle["repo"])

    init {
        fetchRepoDetails(owner, repo)
    }

    fun fetchRepoDetails(owner: String, repo: String) {
        viewModelScope.launch {
            _repoDetails.value = RepoDetailsResponseState.Loading
            when (val result = gitHubRepoRepository.getRepositoryDetails(owner, repo)) {
                is NetworkResult.Success -> {
                    if (result.data != null) {
                        _repoDetails.value = RepoDetailsResponseState.Success(result.data)
                    } else {
                        _repoDetails.value = RepoDetailsResponseState.Error("Repository data is empty")
                    }
                }
                is NetworkResult.Error -> {
                    _repoDetails.value = RepoDetailsResponseState.Error(result.message ?: "Unknown error occurred")
                }
                is NetworkResult.Loading -> {
                    _repoDetails.value = RepoDetailsResponseState.Loading
                }
            }
        }
    }
}