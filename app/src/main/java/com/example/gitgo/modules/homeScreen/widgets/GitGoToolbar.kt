package com.example.gitgo.modules.homeScreen.widgets

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import com.example.gitgo.ui.theme.GitGoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GitGoToolbar(
    title: String,
    showBackButton: Boolean,
    onBackClick: (() -> Unit)?,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                color = GitGoTheme.colors.textColor
            )
        },
        windowInsets = TopAppBarDefaults.windowInsets,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = GitGoTheme.colors.surface,
            scrolledContainerColor = GitGoTheme.colors.surface
        ),
        navigationIcon = {
            if (showBackButton && onBackClick != null) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = GitGoTheme.colors.iconTint
                    )
                }
            }
        },
    )
}

