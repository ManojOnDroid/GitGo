package com.example.gitgo.components.network.repositories.implementation

import com.example.gitgo.components.network.apis.GitHubApi
import com.example.gitgo.components.network.models.GitHubSearchRepoModel
import com.example.gitgo.components.network.repositories.interfaces.GitHubSearchRepository
import javax.inject.Inject

class GitHubSearchRepositoryImpl @Inject constructor(private val gitHubApi: GitHubApi) :
    GitHubSearchRepository {

    override suspend fun searchRepositories(query: String): GitHubSearchRepoModel? {
        return gitHubApi.searchRepositories(query).body()
    }

}