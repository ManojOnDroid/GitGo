package com.example.gitgo.components.network.repositories.implementation

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.gitgo.components.network.apis.GitHubApi
import com.example.gitgo.components.network.base.BaseDataSource
import com.example.gitgo.components.network.base.NetworkResult
import com.example.gitgo.components.network.models.GitHubRepoDetailsModel
import com.example.gitgo.components.network.models.GitHubRepoIssuesModel
import com.example.gitgo.components.network.models.GitHubSearchRepoModel
import com.example.gitgo.components.network.models.UserDetailsResponse
import com.example.gitgo.components.network.paging.GitHubRepoIssuesPagingSource
import com.example.gitgo.components.network.paging.GitHubRepoPagingSource
import com.example.gitgo.components.network.repositories.interfaces.GitHubRepoRepository
import javax.inject.Inject

class GitHubRepoRepositoryImpl @Inject constructor(private val gitHubApi: GitHubApi) : BaseDataSource(),
    GitHubRepoRepository {

    override suspend fun searchRepositories(queryMap: HashMap<String, String>): Pager<Int, GitHubSearchRepoModel.Item> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                initialLoadSize = 5
            ),
            pagingSourceFactory = {
                GitHubRepoPagingSource(gitHubApi, queryMap)
            }
        )
    }

    override suspend fun getRepositoryIssues(
        owner: String,
        repo: String,
        state: String
    ): Pager<Int, GitHubRepoIssuesModel.GitHubRepoIssuesModelItem> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                initialLoadSize = 5
            ),
            pagingSourceFactory = {
                GitHubRepoIssuesPagingSource(gitHubApi, owner, repo, state)
            }
        )
    }
    override suspend fun getRepositoryDetails(owner: String, repo: String): NetworkResult<GitHubRepoDetailsModel> {
        return safeApiCall {
            gitHubApi.getRepositoryDetails(owner, repo)
        }
    }

    override suspend fun getUserDetails(): NetworkResult<UserDetailsResponse> {
        return safeApiCall {
            gitHubApi.getUserDetails()
        }
    }

}