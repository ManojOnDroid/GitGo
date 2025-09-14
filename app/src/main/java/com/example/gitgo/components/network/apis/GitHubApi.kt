package com.example.gitgo.components.network.apis

import com.example.gitgo.components.network.models.GitHubRepoDetailsModel
import com.example.gitgo.components.network.models.GitHubRepoIssuesModel
import com.example.gitgo.components.network.models.GitHubSearchRepoModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApi {

    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") q : String,
        @Query("page") page : Int,
        @Query("per_page") perPage : Int
    ): Response<GitHubSearchRepoModel>

    @GET("repos/{owner}/{repo}")
    suspend fun getRepositoryDetails(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Response<GitHubRepoDetailsModel>

    @GET("repos/{owner}/{repo}/issues")
    suspend fun getRepositoryIssues(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Query("state") state: String = "all", // open, closed, or all
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ) : Response<GitHubRepoIssuesModel>
}