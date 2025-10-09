package com.example.gitgo.modules.loginScreen.screen

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gitgo.components.constants.ApiConstants
import com.example.gitgo.components.constants.Destination
import com.example.gitgo.modules.loginScreen.states.LoginResponseState
import com.example.gitgo.modules.loginScreen.viewmodel.LoginScreenViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: LoginScreenViewModel = hiltViewModel()
) {
    val context = navController.context
    val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    // 🔹 Step 1: Check if token already saved
    val savedToken = prefs.getString("access_token", null)
    LaunchedEffect(savedToken) {
        if (!savedToken.isNullOrEmpty()) {
            navController.navigate(Destination.HOME_SCREEN) {
                popUpTo(Destination.LOGIN_SCREEN) { inclusive = true }
            }
        }
    }

    val savedCode = prefs.getString("code", null)
    val savedState = prefs.getString("state", null)

    LaunchedEffect(savedCode, savedState) {
        if (!savedCode.isNullOrEmpty() && !savedState.isNullOrEmpty()) {
            viewModel.getAccessToken(
                clientId = ApiConstants.CLIENT_ID,
                clientSecret = ApiConstants.CLIENT_SECRET,
                code = savedCode,
                redirectUri = ApiConstants.REDIRECT_URI,
                state = savedState
            )
            prefs.edit().remove("code").remove("state").apply()
        }
    }

    val state by viewModel.data.collectAsState()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when (val data = state) {
            is LoginResponseState.Loading -> CircularProgressIndicator()

            is LoginResponseState.Success -> {
                val token = data.data.accessToken
                prefs.edit().putString("access_token", token).apply()
                navController.navigate(Destination.HOME_SCREEN) {
                    popUpTo(Destination.LOGIN_SCREEN) { inclusive = true }
                }
            }

            is LoginResponseState.Error -> {
                Text("Login failed: ${data.message}")
            }

            LoginResponseState.Undefined -> Unit
        }

        // Show login button only if not already logged in
        if (state !is LoginResponseState.Success && savedToken.isNullOrEmpty()) {
            Button(onClick = {
                val loginUrl = "https://github.com/login/oauth/authorize" +
                        "?client_id=${ApiConstants.CLIENT_ID}" +
                        "&redirect_uri=${ApiConstants.REDIRECT_URI}" +
                        "&scope=repo,user" +
                        "&state=12345"

                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(loginUrl))
                context.startActivity(intent)
            }) {
                Text(text = "Login with GitHub")
            }
        }
    }
}


