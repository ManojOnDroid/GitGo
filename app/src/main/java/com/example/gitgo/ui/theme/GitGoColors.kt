package com.example.gitgo.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class GitGoColors(
    val primary: Color = Color.Unspecified,
    val primaryVariant: Color = Color.Unspecified,
    val secondary: Color = Color.Unspecified,
    val secondaryVariant: Color = Color.Unspecified,
    val background: Color = Color.Unspecified,
    val surface: Color = Color.Unspecified,
    val surfaceVariant: Color = Color.Unspecified,
    val card: Color = Color.Unspecified,
    val cardElevated: Color = Color.Unspecified,
    val textColor: Color = Color.Unspecified,
    val textSecondary: Color = Color.Unspecified,
    val textTertiary: Color = Color.Unspecified,
    val loaderColor: Color = Color.Unspecified,
    val iconTint: Color = Color.Unspecified,
    val iconSecondary: Color = Color.Unspecified,
    val floatingColor: Color = Color.Unspecified,
    val success: Color = Color.Unspecified,
    val warning: Color = Color.Unspecified,
    val error: Color = Color.Unspecified,
    val info: Color = Color.Unspecified,
    val outline: Color = Color.Unspecified,
    val outlineVariant: Color = Color.Unspecified,
    val divider: Color = Color.Unspecified,
    val shimmer: Color = Color.Unspecified,
    val chipBackground: Color = Color.Unspecified,
    val chipSelected: Color = Color.Unspecified,
    val ripple: Color = Color.Unspecified,
    val shadow: Color = Color.Unspecified,
    val gradient1: Color = Color.Unspecified,
    val gradient2: Color = Color.Unspecified,
    val filterBackground: Color = Color.Unspecified,
    val dropdownBackground: Color = Color.Unspecified,
    val dropdownSelectedBackground: Color = Color.Unspecified,
    val toolbarBackground: Color = Color.Unspecified,
    val toolbarElevation: Color = Color.Unspecified,
    val isLight: Boolean = true,
    val gradientStart: Color = Color.Unspecified,
    val gradientEnd: Color = Color.Unspecified,
    val whiteText: Color = Color.Unspecified,
)

val lightGitGoColors = GitGoColors(
    primary = Color(0xFF2965FF),
    primaryVariant = Color(0xFF1E4FD9),
    secondary = Color(0xFF5C8AFF),
    secondaryVariant = Color(0xFF4A7AE8),
    background = Color(0xFFFCFCFC),
    surface = Color(0xFFFFFFFF),
    surfaceVariant = Color(0xFFF5F5F7),
    card = Color(0xFFFFFFFF),
    cardElevated = Color(0xFFFAFAFA),
    textColor = Color(0xFF1A1A1A),
    textSecondary = Color(0xFF6B6B6B),
    textTertiary = Color(0xFF9E9E9E),
    loaderColor = Color(0xFF5C8AFF),
    iconTint = Color(0xFF2C2C2C),
    iconSecondary = Color(0xFF767676),
    floatingColor = Color(0xFF2965FF),
    success = Color(0xFF10B981),
    warning = Color(0xFFF59E0B),
    error = Color(0xFFEF4444),
    info = Color(0xFF3B82F6),
    outline = Color(0xFFE0E0E0),
    outlineVariant = Color(0xFFF0F0F0),
    divider = Color(0xFFEEEEEE),
    shimmer = Color(0xFFF0F0F0),
    chipBackground = Color(0xFFF1F5F9),
    chipSelected = Color(0xFFE0EAFF),
    ripple = Color(0x1A2965FF),
    shadow = Color(0x1A000000),
    gradient1 = Color(0xFF2965FF),
    gradient2 = Color(0xFF5C8AFF),
    filterBackground = Color(0xFFF8F9FA),
    dropdownBackground = Color(0xFFFFFFFF),
    dropdownSelectedBackground = Color(0xFFE8F0FE),
    toolbarBackground = Color(0xFFFFFFFF),
    toolbarElevation = Color(0x0A000000),
    isLight = true,
    gradientStart = Color(0xFFE6F0FF),
    gradientEnd = Color(0xFFFCFCFC),
    whiteText = Color(0xFFFFFFFF),
    )

val darkGitGoColors = GitGoColors(
    // Primary Colors
    primary = Color(0xFF4A80FF),
    primaryVariant = Color(0xFF366EE0),
    secondary = Color(0xFF6B93FF),
    secondaryVariant = Color(0xFF5A82E8),

    // Background & Surfaces
    background = Color(0xFF0F0F0F),
    surface = Color(0xFF1A1A1A),
    surfaceVariant = Color(0xFF242424),
    card = Color(0xFF1E1E1E),
    cardElevated = Color(0xFF252525),

    // Text Colors
    textColor = Color(0xFFFFFFFF),
    textSecondary = Color(0xFFB3B3B3),
    textTertiary = Color(0xFF737373),

    // Interactive Elements
    loaderColor = Color(0xFF4A80FF),
    iconTint = Color(0xFFE5E5E5),
    iconSecondary = Color(0xFFA0A0A0),
    floatingColor = Color(0xFF4A80FF),

    // Status Colors
    success = Color(0xFF22C55E),
    warning = Color(0xFFFBBF24),
    error = Color(0xFFF87171),
    info = Color(0xFF60A5FA),

    // Borders & Dividers
    outline = Color(0xFF333333),
    outlineVariant = Color(0xFF2A2A2A),
    divider = Color(0xFF2C2C2C),

    // Special Effects
    shimmer = Color(0xFF2A2A2A),
    chipBackground = Color(0xFF262626),
    chipSelected = Color(0xFF1A2A4A),
    ripple = Color(0x1A4A80FF),
    shadow = Color(0x33000000),

    // Gradients
    gradient1 = Color(0xFF4A80FF),
    gradient2 = Color(0xFF6B93FF),

    // Filter Colors
    filterBackground = Color(0xFF262626),
    dropdownBackground = Color(0xFF2A2A2A),
    dropdownSelectedBackground = Color(0xFF1A2D4A),

    toolbarBackground = Color(0xFF1A1A1A),
    toolbarElevation = Color(0x1A000000),

    isLight = false,
    gradientStart = Color(0xFF141E33),
    gradientEnd = Color(0xFF0F0F0F),
    whiteText = Color(0xFFFFFFFF),
)

val GitGoColors.onPrimary: Color
    get() = if (isLight) Color.White else Color.Black

val GitGoColors.onSurface: Color
    get() = textColor

val GitGoColors.onCard: Color
    get() = textColor

// Semantic color shortcuts
val GitGoColors.successBackground: Color
    get() = success.copy(alpha = 0.1f)

val GitGoColors.warningBackground: Color
    get() = warning.copy(alpha = 0.1f)

val GitGoColors.errorBackground: Color
    get() = error.copy(alpha = 0.1f)

val GitGoColors.infoBackground: Color
    get() = info.copy(alpha = 0.1f)

val GitGoColors.primaryBackground: Color
    get() = primary.copy(alpha = 0.1f)
