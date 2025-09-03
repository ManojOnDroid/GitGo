package com.example.gitgo.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class GitGoColors(
    val primary: Color = Color.Unspecified,
    val secondary: Color = Color.Unspecified,
    val background: Color = Color.Unspecified,
    val surface: Color = Color.Unspecified,
    val card: Color = Color.Unspecified,
    val textColor: Color = Color.Unspecified,
    val loaderColor: Color = Color.Unspecified,
    val iconTint: Color = Color.Unspecified,
    val floatingColor: Color = Color.Unspecified,
    val isLight: Boolean = true
)

val lightGitGoColors = GitGoColors(
    primary = Color(0xFF2965FF),
    secondary = Color(0xFF5C8AFF),
    background = Color(0xFFFFFFFF),
    surface = Color(0xFFF6F6F6),
    card = Color(0xFFC4C2C2),
    textColor = Color(0xFF324054),
    loaderColor = Color(0xFF5C8AFF),
    iconTint = Color(0xFF171718),
    floatingColor = Color(0xFF5C8AFF),
    isLight = true
)

val darkGitGoColors = GitGoColors(
    primary = Color(0xFF3158C7),
    secondary = Color(0xFF5C8AFF),
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    card = Color(0xFF2F2C2C),
    textColor = Color(0xFFFFFFFF),
    loaderColor = Color(0xFF3A6EE0),
    iconTint = Color(0xFFE9E9EC),
    floatingColor = Color(0xFF5C8AFF),
    isLight = false
)
