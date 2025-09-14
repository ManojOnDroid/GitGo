package com.example.gitgo.components.constants

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem (
    val route: String,
    val label: String,
    val icon: ImageVector
)
val bottomNavItems = listOf(
    BottomNavItem(
        route = Destination.HOME_SCREEN,
        label = "Home",
        icon = Icons.Default.Home
    ),
    BottomNavItem(
        route = Destination.REPO_SEARCH_SCREEN,
        label = "Search",
        icon = Icons.Default.Search
    ),
    BottomNavItem(
        route = Destination.SETTINGS_SCREEN,
        label = "Settings",
        icon = Icons.Default.Settings
    )
)

