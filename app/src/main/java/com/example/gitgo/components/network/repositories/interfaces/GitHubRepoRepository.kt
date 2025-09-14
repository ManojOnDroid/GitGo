package com.example.gitgo.components.network.repositories.interfaces

import androidx.paging.Pager
import com.example.gitgo.components.network.models.GitHubRepoDetailsModel
import com.example.gitgo.components.network.models.GitHubRepoIssuesModel
import com.example.gitgo.components.network.models.GitHubSearchRepoModel

interface GitHubRepoRepository {
    suspend fun searchRepositories(
        query: String
    ) : Pager<Int, GitHubSearchRepoModel.Item>

    suspend fun getRepositoryDetails(
        owner: String,
        repo: String
    ) : GitHubRepoDetailsModel?

    suspend fun getRepositoryIssues(
        owner: String,
        repo: String,
        state: String
    ) : Pager<Int, GitHubRepoIssuesModel.GitHubRepoIssuesModelItem>

}