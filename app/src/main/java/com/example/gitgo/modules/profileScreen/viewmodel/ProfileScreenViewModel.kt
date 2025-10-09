package com.example.gitgo.modules.profileScreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitgo.components.network.repositories.interfaces.GitHubRepoRepository
import com.example.gitgo.modules.profileScreen.states.UserDetailsResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val gitHubSearchRepository: GitHubRepoRepository
): ViewModel() {
    val userDetails = MutableStateFlow<UserDetailsResponseState?>(UserDetailsResponseState.Loading)
    init {
        fetchUserDetails()
    }

    fun fetchUserDetails() {
        viewModelScope.launch {
            try {
               val data = gitHubSearchRepository.getUserDetails()
                if (data != null) {
                    userDetails.value = UserDetailsResponseState.Success(data)
                } else {
                    userDetails.value = UserDetailsResponseState.Error("Something went wrong")
                }
            } catch (e: Exception) {
                userDetails.value = UserDetailsResponseState.Error(e.message ?: "Something went wrong")
            }
        }
    }
}