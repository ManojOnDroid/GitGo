package com.example.gitgo.modules.loginScreen.screen

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gitgo.R
import com.example.gitgo.components.constants.ApiConstants
import com.example.gitgo.components.constants.Destination
import com.example.gitgo.modules.loginScreen.states.LoginResponseState
import com.example.gitgo.modules.loginScreen.viewmodel.LoginScreenViewModel
import com.example.gitgo.ui.theme.GitGoTheme
import androidx.core.net.toUri

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginScreenViewModel = hiltViewModel()
) {
    val state by viewModel.data.collectAsState()
    val context = navController.context

    LaunchedEffect(Unit) {
        viewModel.checkLoginStatus {
            navController.navigate(Destination.HOME_SCREEN) {
                popUpTo(Destination.LOGIN_SCREEN) { inclusive = true }
            }
        }
    }

    LaunchedEffect(state) {
        if (state is LoginResponseState.Success) {
            navController.navigate(Destination.HOME_SCREEN) {
                popUpTo(Destination.LOGIN_SCREEN) { inclusive = true }
            }
        }
    }

    val prefs = remember { context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE) }

    LaunchedEffect(Unit) {
        val savedCode = prefs.getString("code", null)
        val savedState = prefs.getString("state", null)
        if (!savedCode.isNullOrEmpty() && !savedState.isNullOrEmpty()) {
            viewModel.handleAuthCallback(savedCode, savedState)
            prefs.edit().remove("code").remove("state").apply()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GitGoTheme.colors.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_github_logo),
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                tint = GitGoTheme.colors.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.welcome_to_gitgo),
                style = MaterialTheme.typography.headlineMedium,
                color = GitGoTheme.colors.textColor
            )

            Spacer(modifier = Modifier.height(48.dp))

            if (state is LoginResponseState.Loading) {
                CircularProgressIndicator(color = GitGoTheme.colors.primary)
            } else {
                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, ApiConstants.getFullAuthUrl().toUri())
                        context.startActivity(intent)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GitGoTheme.colors.primary,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = stringResource(R.string.login_with_github))
                }
            }

            if (state is LoginResponseState.Error) {
                Text(
                    text = (state as LoginResponseState.Error).message,
                    color = GitGoTheme.colors.error,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}


