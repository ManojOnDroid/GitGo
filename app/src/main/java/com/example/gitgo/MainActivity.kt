package com.example.gitgo

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.gitgo.ui.screens.GitHubExplorerApp
import com.example.gitgo.ui.theme.GitGoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleDeepLink(intent?.data)
        enableEdgeToEdge()
        setContent {
            GitGoTheme {
                    GitHubExplorerApp()
            }
        }
    }
    private fun handleDeepLink(data: Uri?) {
        if (data != null && data.scheme == "gitgo" && data.host == "callback") {
            val code = data.getQueryParameter("code")
            val state = data.getQueryParameter("state")

            if (!code.isNullOrEmpty() && !state.isNullOrEmpty()) {
                getSharedPreferences("auth_prefs", MODE_PRIVATE).edit()
                    .putString("code", code)
                    .putString("state", state)
                    .apply()
            }
        }
    }
}
