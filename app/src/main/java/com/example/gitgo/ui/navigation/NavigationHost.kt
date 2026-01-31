package com.example.gitgo.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.gitgo.components.constants.Destination
import com.example.gitgo.modules.homeScreen.screen.HomeScreen
import com.example.gitgo.modules.loginScreen.screen.LoginScreen
import com.example.gitgo.modules.profileScreen.screen.ProfileScreen
import com.example.gitgo.modules.repoDetailScreen.screen.RepoDetailsScreen
import com.example.gitgo.modules.repoIssuesScreen.screen.RepoIssuesScreen
import com.example.gitgo.modules.repoSearchScreen.screen.RepoSearchScreen
import com.example.gitgo.modules.settingsScreen.screen.SettingsScreen

@Composable
fun NavigationHost(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier
) {
    NavHost(navController = navController, startDestination = startDestination,modifier = modifier) {
        composable(Destination.HOME_SCREEN) {
            HomeScreen(navController)
        }
        composable(Destination.REPO_SEARCH_SCREEN) {
            RepoSearchScreen(navController)
        }
        composable(
            route = "${Destination.REPO_DETAILS_SCREEN}/{owner}/{repo}",
            arguments = listOf(
                navArgument("owner") { type = NavType.StringType },
                navArgument("repo") { type = NavType.StringType }
            )
        ) {
            RepoDetailsScreen(
                navController = navController,
                modifier = Modifier.fillMaxSize()
            )
        }
        composable(
            route = "${Destination.REPO_ISSUES_SCREEN}/{owner}/{repo}",
            arguments = listOf(
                navArgument("owner") { type = NavType.StringType },
                navArgument("repo") { type = NavType.StringType }
            )
        ) {
            RepoIssuesScreen(
                navController = navController,
                modifier = Modifier.fillMaxSize()
            )
        }
        composable(
            route = Destination.SETTINGS_SCREEN,
        ) {
            SettingsScreen(
                navController = navController,
                modifier = Modifier.fillMaxSize(),
            )
        }

        composable(
            route = Destination.LOGIN_SCREEN,
        ) {
            LoginScreen(
                navController = navController,
            )
        }

        composable(
            route = Destination.PROFILE_SCREEN,
        ) {
            ProfileScreen(
                navController = navController,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}