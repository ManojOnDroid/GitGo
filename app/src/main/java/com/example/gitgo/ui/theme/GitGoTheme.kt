package com.example.gitgo.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

val LocalGitGoColors = staticCompositionLocalOf { lightGitGoColors }

@Composable
fun GitGoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) darkGitGoColors else lightGitGoColors

    CompositionLocalProvider(
        LocalGitGoColors provides colors
    ) {
        content()
    }
}

object GitGoTheme {
    val colors: GitGoColors
        @Composable
        get() = LocalGitGoColors.current
}
