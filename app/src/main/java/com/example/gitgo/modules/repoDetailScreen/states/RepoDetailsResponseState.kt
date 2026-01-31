package com.example.gitgo.modules.repoDetailScreen.states

import com.example.gitgo.components.network.models.GitHubRepoDetailsModel

sealed class RepoDetailsResponseState {
    data class Success(val details : GitHubRepoDetailsModel) : RepoDetailsResponseState()
    data object Loading : RepoDetailsResponseState()
    data class Error(val error : String) : RepoDetailsResponseState()
}