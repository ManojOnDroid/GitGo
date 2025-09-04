package com.example.gitgo.components.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.gitgo.components.network.apis.GitHubApi
import com.example.gitgo.components.network.models.GitHubSearchRepoModel

class GitHubRepoPagingSource(
    private val gitHubApi: GitHubApi,
    private val query: String
) : PagingSource<Int, GitHubSearchRepoModel.Item>() {
    override fun getRefreshKey(state: PagingState<Int, GitHubSearchRepoModel.Item>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GitHubSearchRepoModel.Item> {
        return try {
            val page = params.key ?: 1
            val response = gitHubApi.searchRepositories(query, page, params.loadSize)
            val repos = response.body()?.items ?: emptyList()
            LoadResult.Page(
                data = repos.filterNotNull(),
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (repos.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}