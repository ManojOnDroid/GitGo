package com.example.gitgo.components.network.repositories.interfaces

import androidx.paging.Pager
import com.example.gitgo.components.network.models.GitHubSearchRepoModel

interface GitHubSearchRepository {
    suspend fun searchRepositories(
        query: String
    ) : Pager<Int, GitHubSearchRepoModel.Item>
}