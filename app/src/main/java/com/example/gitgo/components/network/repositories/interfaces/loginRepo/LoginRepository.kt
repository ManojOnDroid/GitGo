package com.example.gitgo.components.network.repositories.interfaces.loginRepo

import com.example.gitgo.components.network.base.NetworkResult
import com.example.gitgo.components.network.models.AccessTokenResponse

interface LoginRepository {
    suspend fun getAccessToken(
        clientId: String,
        clientSecret: String,
        code: String,
        redirectUri: String,
        state: String
    ): NetworkResult<AccessTokenResponse>

}