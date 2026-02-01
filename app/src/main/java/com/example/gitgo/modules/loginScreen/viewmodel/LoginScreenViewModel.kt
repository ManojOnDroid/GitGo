package com.example.gitgo.modules.loginScreen.viewmodel

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitgo.components.constants.ApiConstants
import com.example.gitgo.components.network.base.NetworkResult
import com.example.gitgo.components.network.repositories.interfaces.loginRepo.LoginRepository
import com.example.gitgo.modules.loginScreen.states.LoginResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val sharedPrefs: SharedPreferences
) : ViewModel() {

    private val _data = MutableStateFlow<LoginResponseState>(LoginResponseState.Undefined)
    val data = _data.asStateFlow()

    fun checkLoginStatus(onLoggedIn: () -> Unit) {
        if (!sharedPrefs.getString("access_token", null).isNullOrEmpty()) {
            onLoggedIn()
        }
    }

    fun handleAuthCallback(code: String?, state: String?) {
        if (!code.isNullOrEmpty() && !state.isNullOrEmpty()) {
            getAccessToken(code, state)
        }
    }

    private fun getAccessToken(code: String, state: String) {
        viewModelScope.launch {
            _data.value = LoginResponseState.Loading

            val result = loginRepository.getAccessToken(
                ApiConstants.CLIENT_ID,
                ApiConstants.CLIENT_SECRET,
                code,
                ApiConstants.REDIRECT_URI,
                state
            )

            when (result) {
                is NetworkResult.Success -> {
                    val response = result.data
                    if (response != null && !response.error.isNullOrEmpty()) {
                        _data.value = LoginResponseState.Error(
                            response.errorDescription ?: "Authentication failed"
                        )
                    } else if (response?.accessToken != null) {
                        sharedPrefs.edit { putString("access_token", response.accessToken) }
                        _data.value = LoginResponseState.Success(response)
                    } else {
                        _data.value = LoginResponseState.Error("Unknown response from server")
                    }
                }

                is NetworkResult.Error -> {
                    _data.value = LoginResponseState.Error(result.message ?: "Auth Failed")
                }

                is NetworkResult.Loading -> {
                    _data.value = LoginResponseState.Loading
                }
            }
        }
    }
}