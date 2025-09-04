package com.example.gitgo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gitgo.homeScreen.screen.HomeScreen
import com.example.gitgo.searchScreen.screen.SearchScreen
import com.example.gitgo.ui.theme.GitGoTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GitGoTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    containerColor = GitGoTheme.colors.surface) { innerPadding ->
                    GitHubExplorerApp(Modifier.padding(innerPadding))
                }
            }
        }
    }
}


@Composable
fun GitHubExplorerApp(modifier: Modifier) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController,modifier) }
        composable("search") { SearchScreen(navController,modifier) }
    }
}

