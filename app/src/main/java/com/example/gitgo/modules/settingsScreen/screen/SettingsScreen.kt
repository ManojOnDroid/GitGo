package com.example.gitgo.modules.settingsScreen.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gitgo.components.constants.Destination
import com.example.gitgo.ui.theme.GitGoTheme

@Composable
fun SettingsScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        GitGoTheme.colors.gradient1,
                        GitGoTheme.colors.gradient2,
                        GitGoTheme.colors.background.copy(alpha = 0.9f),
                        GitGoTheme.colors.surfaceVariant
                    ),
                    startY = 0f,
                    endY = 1500f
                )
            )
    ) {
        Row (horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxSize().clickable{
            navController.navigate(Destination.PROFILE_SCREEN)
        }){
            Text("Profile")
            Icon(
                Icons.Default.ArrowForwardIos,
                contentDescription = "next",
                modifier = Modifier.size(16.dp),
                tint = GitGoTheme.colors.iconTint
            )
        }
    }
}