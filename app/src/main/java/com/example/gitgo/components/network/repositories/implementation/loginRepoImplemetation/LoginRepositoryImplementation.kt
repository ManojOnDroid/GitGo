package com.example.gitgo.components.network.repositories.implementation.loginRepoImplemetation

import com.example.gitgo.components.network.apis.login.LoginApi
import com.example.gitgo.components.network.base.BaseDataSource
import com.example.gitgo.components.network.base.NetworkResult
import com.example.gitgo.components.network.models.AccessTokenResponse
import com.example.gitgo.components.network.repositories.interfaces.loginRepo.LoginRepository
import javax.inject.Inject

class LoginRepositoryImplementation @Inject constructor(
    private val loginApi: LoginApi
) : BaseDataSource(), LoginRepository {

    override suspend fun getAccessToken(
        clientId: String,
        clientSecret: String,
        code: String,
        redirectUri: String,
        state: String
    ): NetworkResult<AccessTokenResponse> {
        return safeApiCall {
            loginApi.getAccessToken(clientId, clientSecret, code, redirectUri, state)
        }
    }
}