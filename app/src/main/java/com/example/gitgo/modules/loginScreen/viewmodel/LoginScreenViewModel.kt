package com.example.gitgo.modules.loginScreen.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitgo.components.constants.ApiConstants
import com.example.gitgo.components.network.repositories.interfaces.loginRepo.LoginRepository
import com.example.gitgo.modules.loginScreen.states.LoginResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.core.content.edit

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val sharedPrefs: SharedPreferences
): ViewModel() {

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
            try {
                val response = loginRepository.getAccessToken(
                    ApiConstants.CLIENT_ID,
                    ApiConstants.CLIENT_SECRET,
                    code,
                    ApiConstants.REDIRECT_URI,
                    state
                )
                sharedPrefs.edit { putString("access_token", response.accessToken) }
                _data.value = LoginResponseState.Success(response)
            } catch (e: Exception) {
                _data.value = LoginResponseState.Error(e.message ?: "Auth Failed")
            }
        }
    }
}