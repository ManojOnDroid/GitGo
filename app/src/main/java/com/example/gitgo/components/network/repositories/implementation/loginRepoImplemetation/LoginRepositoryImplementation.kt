package com.example.gitgo.components.network.repositories.implementation.loginRepoImplemetation

import com.example.gitgo.components.network.apis.login.LoginApi
import com.example.gitgo.components.network.models.AccessTokenResponse
import com.example.gitgo.components.network.repositories.interfaces.loginRepo.LoginRepository
import javax.inject.Inject

class LoginRepositoryImplementation @Inject constructor(
    private val loginApi: LoginApi
) : LoginRepository{
    override suspend fun getAccessToken(
        clientId: String,
        clientSecret: String,
        code: String,
        redirectUri: String,
        state: String
    ): AccessTokenResponse {
        val response = loginApi.getAccessToken(clientId, clientSecret, code, redirectUri, state)
        if (response.isSuccessful) {
            return response.body()!!
        }else{
            throw Exception("Error getting access token ${response.errorBody()}")
        }
    }
}