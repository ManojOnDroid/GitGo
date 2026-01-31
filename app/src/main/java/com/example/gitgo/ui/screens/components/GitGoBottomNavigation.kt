package com.example.gitgo.ui.screens.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.gitgo.components.constants.Destination
import com.example.gitgo.components.constants.bottomNavItems
import com.example.gitgo.ui.theme.GitGoTheme

@Composable
fun GitGoBottomNavigation(
    navController: NavHostController,
    currentRoute: String?
) {
    NavigationBar(
        containerColor = GitGoTheme.colors.surface,
        windowInsets = NavigationBarDefaults.windowInsets
    ) {
        bottomNavItems.forEach { item ->
            val isSelected = currentRoute == item.route
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        navController.navigate(item.route) {
                            popUpTo(Destination.HOME_SCREEN) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = if (isSelected) GitGoTheme.colors.primary else GitGoTheme.colors.iconTint
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        color = if (isSelected) GitGoTheme.colors.textColor else GitGoTheme.colors.textSecondary
                    )
                }
            )
        }
    }
}