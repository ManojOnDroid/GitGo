package com.example.gitgo.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.gitgo.components.constants.Destination
import com.example.gitgo.components.constants.bottomNavItems
import com.example.gitgo.modules.homeScreen.widgets.GitGoToolbar
import com.example.gitgo.ui.navigation.NavigationHost
import com.example.gitgo.ui.screens.components.GitGoBottomNavigation

@Composable
fun GitHubExplorerApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Destination.LOGIN_SCREEN
    val showBottomBar = currentRoute in bottomNavItems.map { it.route }
    val showBackButton = when (currentRoute) {
        Destination.HOME_SCREEN, Destination.LOGIN_SCREEN, Destination.PROFILE_SCREEN, Destination.REPO_SEARCH_SCREEN -> false
        else -> true
    }
    val toolbarTitle = when {
        currentRoute == Destination.HOME_SCREEN -> "GitHub Explorer"
        currentRoute == Destination.REPO_SEARCH_SCREEN -> "Search Repositories"
        currentRoute == Destination.LOGIN_SCREEN -> ""
        currentRoute == Destination.PROFILE_SCREEN -> "Profile"
        currentRoute == Destination.SETTINGS_SCREEN -> "Settings"
        currentRoute.contains(Destination.REPO_DETAILS_SCREEN) -> {
            navBackStackEntry?.arguments?.getString("repo") ?: "Repository"
        }
        currentRoute.contains(Destination.REPO_ISSUES_SCREEN) -> {
            val repo = navBackStackEntry?.arguments?.getString("repo") ?: "Repository"
            "$repo Issues"
        }
        else -> "GitGo"
    }
    Scaffold(
        topBar = {
            GitGoToolbar(
                title = toolbarTitle,
                showBackButton = showBackButton,
                onBackClick = { navController.navigateUp() },
            )
        },
        bottomBar = {
            if (showBottomBar) {
                GitGoBottomNavigation(navController, currentRoute)
            }
        }
    ) { padding ->
        NavigationHost(
            navController = navController,
            startDestination = Destination.LOGIN_SCREEN,
            modifier = Modifier.padding(padding)
        )
    }
}