package com.example.gitgo.components.network.repositories.implementation

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.gitgo.components.network.apis.GitHubApi
import com.example.gitgo.components.network.models.GitHubSearchRepoModel
import com.example.gitgo.components.network.paging.GitHubRepoPagingSource
import com.example.gitgo.components.network.repositories.interfaces.GitHubSearchRepository
import javax.inject.Inject

class GitHubSearchRepositoryImpl @Inject constructor(private val gitHubApi: GitHubApi) :
    GitHubSearchRepository {

    override suspend fun searchRepositories(query: String): Pager<Int, GitHubSearchRepoModel.Item> {
        return Pager(
            config = PagingConfig(
                pageSize = 2,
                enablePlaceholders = false,
                initialLoadSize = 2
            ),
            pagingSourceFactory = {
                GitHubRepoPagingSource(gitHubApi, query)
            }
        )
    }

}