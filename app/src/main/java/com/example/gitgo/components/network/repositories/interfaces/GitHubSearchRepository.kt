package com.example.gitgo.components.network.repositories.interfaces

import com.example.gitgo.components.network.models.GitHubSearchRepoModel

interface GitHubSearchRepository {
    suspend fun searchRepositories(
        query: String
    ) : GitHubSearchRepoModel?
}