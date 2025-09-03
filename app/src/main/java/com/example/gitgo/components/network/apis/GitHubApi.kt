package com.example.gitgo.components.network.apis

import com.example.gitgo.components.network.models.GitHubSearchRepoModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubApi {

    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") q : String,
        @Query("page") page : Int,
        @Query("per_page") perPage : Int
    ): Response<GitHubSearchRepoModel>
}