package com.example.gitgo.modules.loginScreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitgo.components.network.repositories.interfaces.loginRepo.LoginRepository
import com.example.gitgo.modules.loginScreen.states.LoginResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val loginRepository: LoginRepository
): ViewModel() {

    val data = MutableStateFlow<LoginResponseState>(LoginResponseState.Undefined)
     fun getAccessToken(
        clientId: String,
        clientSecret: String,
        code: String,
        redirectUri: String,
        state: String
    ) {
         viewModelScope.launch {
              try {
                  data.value = LoginResponseState.Loading
                 val data1 = loginRepository.getAccessToken(clientId, clientSecret, code, redirectUri, state)
                  data.value = LoginResponseState.Success(data1)
             } catch (e: Exception) {
                 data.value = LoginResponseState.Error(e.message ?: "Unknown error")
             }
         }
    }

}