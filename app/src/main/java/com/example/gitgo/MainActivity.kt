package com.example.gitgo

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gitgo.components.constants.Destination
import com.example.gitgo.components.constants.bottomNavItems
import com.example.gitgo.modules.homeScreen.screen.HomeScreen
import com.example.gitgo.modules.homeScreen.widgets.GitGoToolbar
import com.example.gitgo.modules.loginScreen.screen.LoginScreen
import com.example.gitgo.modules.profileScreen.screen.ProfileScreen
import com.example.gitgo.modules.repoDetailScreen.screen.RepoDetailsScreen
import com.example.gitgo.modules.repoIssuesScreen.screen.RepoIssuesScreen
import com.example.gitgo.modules.repoSearchScreen.screen.RepoSearchScreen
import com.example.gitgo.modules.settingsScreen.screen.SettingsScreen
import com.example.gitgo.ui.theme.GitGoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val data: Uri? = intent?.data
        if (data != null && data.scheme == "gitgo" && data.host == "callback") {
            val code = data.getQueryParameter("code")
            val state = data.getQueryParameter("state")

            if (!code.isNullOrEmpty() && !state.isNullOrEmpty()) {
                val prefs = getSharedPreferences("auth_prefs", MODE_PRIVATE)
                prefs.edit()
                    .putString("code", code)
                    .putString("state", state)
                    .apply()
            }
        }

        enableEdgeToEdge()
        setContent {
            GitGoTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = GitGoTheme.colors.surface
                ) { padding ->
                    GitHubExplorerApp(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                    )
                }
            }
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun GitHubExplorerApp(modifier: Modifier) {
    val navController = rememberNavController()

    var currentRoute by remember { mutableStateOf(Destination.HOME_SCREEN) }
    var toolbarTitle by remember { mutableStateOf("GitHub Explorer") }
    var showBackButton by remember { mutableStateOf(false) }
    val startDestination = Destination.LOGIN_SCREEN

    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect { backStackEntry ->
            currentRoute = backStackEntry.destination.route ?: Destination.HOME_SCREEN

            when {
                currentRoute == Destination.HOME_SCREEN -> {
                    toolbarTitle = "GitHub Explorer"
                    showBackButton = false
                }
                currentRoute == Destination.REPO_SEARCH_SCREEN -> {
                    toolbarTitle = "Search Repositories"
                    showBackButton = true
                }
                currentRoute.startsWith(Destination.REPO_DETAILS_SCREEN) -> {
                    val repo = backStackEntry.arguments?.getString("repo") ?: "Repository"
                    toolbarTitle = repo
                    showBackButton = true
                }
                currentRoute.startsWith(Destination.REPO_ISSUES_SCREEN) -> {
                    val repo = backStackEntry.arguments?.getString("repo") ?: "Repository"
                    toolbarTitle = "$repo Issues"
                    showBackButton = true
                }
                currentRoute == Destination.LOGIN_SCREEN -> {
                    toolbarTitle = "Login"
                    showBackButton = false
                }
                currentRoute == Destination.PROFILE_SCREEN -> {
                    toolbarTitle = "Profile"
                    showBackButton = true
                }
                currentRoute == Destination.SETTINGS_SCREEN -> {
                    toolbarTitle = "Settings"
                    showBackButton = false
                }
                else -> {
                    toolbarTitle = "GitHub Explorer"
                    showBackButton = currentRoute != "home"
                }
            }
        }
    }

    Scaffold(
        modifier = modifier,
        containerColor = GitGoTheme.colors.surface,
        topBar = {
            GitGoToolbar(
                title = toolbarTitle,
                showBackButton = showBackButton,
                onBackClick = if (showBackButton) {
                    { navController.navigateUp() }
                } else null,
                actions = {
                    if (currentRoute == Destination.HOME_SCREEN) {
                        androidx.compose.material3.IconButton(
                            onClick = { navController.navigate(Destination.REPO_SEARCH_SCREEN) }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = GitGoTheme.colors.iconTint
                            )
                        }
                    }
                },
                modifier = Modifier
            )
        },
        bottomBar = {
            if(currentRoute in bottomNavItems.map { it.route }) {
                NavigationBar(
                    windowInsets = NavigationBarDefaults.windowInsets,
                    containerColor = GitGoTheme.colors.background
                ) {
                    NavigationBar(
                        windowInsets = NavigationBarDefaults.windowInsets,
                        containerColor = GitGoTheme.colors.surface
                    ) {
                        val currentRoute =
                            navController.currentBackStackEntryAsState().value?.destination?.route
                        bottomNavItems.forEach { item ->
                            NavigationBarItem(
                                selected = currentRoute == item.route,
                                onClick = {
                                    navController.navigate(item.route) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                icon = {
                                    Icon(
                                        imageVector = item.icon,
                                        contentDescription = item.label,
                                        tint = if (currentRoute == item.route)
                                            GitGoTheme.colors.primary else GitGoTheme.colors.iconTint
                                    )
                                },
                                label = {
                                    Text(item.label)
                                }
                            )
                        }
                    }
                }
            }
        }
    ) {paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            NavHost(navController = navController, startDestination = startDestination) {
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
                        modifier = Modifier.fillMaxSize(),
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
    }
}
