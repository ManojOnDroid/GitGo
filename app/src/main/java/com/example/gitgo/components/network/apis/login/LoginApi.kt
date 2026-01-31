package com.example.gitgo.components.network.apis.login

import com.example.gitgo.components.network.models.AccessTokenResponse
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginApi {
    @POST("login/oauth/access_token")
    @Headers("Accept: application/json")
    suspend fun getAccessToken(
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String,
        @Query("code") code: String,
        @Query("redirect_uri") redirectUri: String,
        @Query("state") state: String
    ): Response<AccessTokenResponse>
}


