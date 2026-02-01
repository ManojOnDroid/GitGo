package com.example.gitgo.modules.profileScreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitgo.components.network.base.NetworkResult
import com.example.gitgo.components.network.repositories.interfaces.GitHubRepoRepository
import com.example.gitgo.modules.profileScreen.states.UserDetailsResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val repository: GitHubRepoRepository
) : ViewModel() {

    private val _userDetails = MutableStateFlow<UserDetailsResponseState>(UserDetailsResponseState.Loading)
    val userDetails = _userDetails.asStateFlow()

    init {
        fetchUserDetails()
    }

    fun fetchUserDetails() {
        viewModelScope.launch {
            _userDetails.value = UserDetailsResponseState.Loading

            when (val result = repository.getUserDetails()) {
                is NetworkResult.Success -> {
                    if (result.data != null) {
                        _userDetails.value = UserDetailsResponseState.Success(result.data)
                    } else {
                        _userDetails.value = UserDetailsResponseState.Error("User data is empty")
                    }
                }
                is NetworkResult.Error -> {
                    _userDetails.value = UserDetailsResponseState.Error(
                        result.message ?: "Failed to load profile"
                    )
                }
                is NetworkResult.Loading -> {
                    _userDetails.value = UserDetailsResponseState.Loading
                }
            }
        }
    }
}