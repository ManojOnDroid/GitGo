package com.example.gitgo.components.network.repositories.interfaces

import androidx.paging.Pager
import com.example.gitgo.components.network.base.NetworkResult
import com.example.gitgo.components.network.models.GitHubRepoDetailsModel
import com.example.gitgo.components.network.models.GitHubRepoIssuesModel
import com.example.gitgo.components.network.models.GitHubSearchRepoModel
import com.example.gitgo.components.network.models.UserDetailsResponse

interface GitHubRepoRepository {
    suspend fun searchRepositories(
        queryMap: HashMap<String, String>
    ) : Pager<Int, GitHubSearchRepoModel.Item>

    suspend fun getRepositoryDetails(
        owner: String,
        repo: String
    ) : NetworkResult<GitHubRepoDetailsModel>

    suspend fun getRepositoryIssues(
        owner: String,
        repo: String,
        state: String
    ) : Pager<Int, GitHubRepoIssuesModel.GitHubRepoIssuesModelItem>

    suspend fun getUserDetails() : NetworkResult<UserDetailsResponse>


}