package com.example.gitgo.modules.loginScreen.states

import com.example.gitgo.components.network.models.AccessTokenResponse

sealed class LoginResponseState {
    object Loading : LoginResponseState()
    object Undefined : LoginResponseState()
    data class Success(val data: AccessTokenResponse) : LoginResponseState()
    data class Error(val message: String) : LoginResponseState()
}