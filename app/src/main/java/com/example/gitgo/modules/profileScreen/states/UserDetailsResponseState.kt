package com.example.gitgo.modules.profileScreen.states

import com.example.gitgo.components.network.models.UserDetailsResponse

sealed class UserDetailsResponseState {
    data object Loading : UserDetailsResponseState()
    data class Success(val userDetails : UserDetailsResponse) : UserDetailsResponseState()
    data class Error(val message: String) : UserDetailsResponseState()
}