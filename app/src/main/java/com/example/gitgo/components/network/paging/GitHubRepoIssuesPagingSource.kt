package com.example.gitgo.components.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.gitgo.components.network.apis.GitHubApi
import com.example.gitgo.components.network.models.GitHubRepoIssuesModel

class GitHubRepoIssuesPagingSource(
    private val gitHubApi: GitHubApi,
    private val owner: String,
    private val repo: String,
    private val state: String,
) : PagingSource<Int, GitHubRepoIssuesModel.GitHubRepoIssuesModelItem>() {

    override fun getRefreshKey(state: PagingState<Int, GitHubRepoIssuesModel.GitHubRepoIssuesModelItem>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GitHubRepoIssuesModel.GitHubRepoIssuesModelItem> {
        return try {
            val page = params.key ?: 1
            val response = gitHubApi.getRepositoryIssues(owner, repo, state, params.loadSize, page)
            val repos = response.body() ?: emptyList()
            LoadResult.Page(
                data = repos,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (repos.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}