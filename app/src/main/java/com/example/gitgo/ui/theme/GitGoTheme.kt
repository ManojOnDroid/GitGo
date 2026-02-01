package com.example.gitgo.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

val LocalGitGoColors = staticCompositionLocalOf { lightGitGoColors }
val LocalGitGoTypography = staticCompositionLocalOf { GitGoTypography() }
@Composable
fun GitGoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) darkGitGoColors else lightGitGoColors
    val typography = getGitGoTypography(colors.textColor)
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colors.surface.toArgb()

            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }
    CompositionLocalProvider(
        LocalGitGoColors provides colors,
        LocalGitGoTypography provides typography
    ) {
        content()
    }
}

object GitGoTheme {
    val colors: GitGoColors
        @Composable
        get() = LocalGitGoColors.current

    val typography: GitGoTypography
        @Composable
        get() = LocalGitGoTypography.current
}
