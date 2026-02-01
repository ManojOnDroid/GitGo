package com.example.gitgo.modules.homeScreen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.gitgo.R
import com.example.gitgo.ui.theme.GitGoTheme

@Composable
fun CollapsingHomeHeader(
    collapseFraction: Float,
    modifier: Modifier = Modifier
) {
    val expandedLogoSize = 90.dp
    val collapsedLogoSize = 42.dp
    val expandedLogoY = 40.dp

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    val currentLogoSize = expandedLogoSize + (collapsedLogoSize - expandedLogoSize) * collapseFraction

    val startX = (screenWidth / 2) - (expandedLogoSize / 2)
    val endX = 24.dp
    val currentX = startX + (endX - startX) * collapseFraction

    val startY = expandedLogoY
    val endY = 10.dp
    val currentY = startY + (endY - startY) * collapseFraction

    val textAlpha = (1f - (collapseFraction * 3)).coerceIn(0f, 1f)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = GitGoTheme.typography.displaySmall,
            fontWeight = FontWeight.Black,
            color = GitGoTheme.colors.primary,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 100.dp)
                .graphicsLayer { alpha = textAlpha }
        )

        Box(
            modifier = Modifier
                .offset(x = currentX, y = currentY)
                .size(currentLogoSize)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = 8.dp)
                    .graphicsLayer { alpha = textAlpha }
                    .background(
                        color = GitGoTheme.colors.primary.copy(alpha = 0.2f),
                        shape = CircleShape
                    )
            )

            Surface(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(22.dp * (1 - collapseFraction * 0.5f)),
                color = GitGoTheme.colors.surface,
                shadowElevation = 10.dp * (1 - collapseFraction),
                border = BorderStroke(
                    width = 1.dp,
                    color = GitGoTheme.colors.surface.copy(alpha = 0.5f)
                )
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    GitGoTheme.colors.primary,
                                    GitGoTheme.colors.secondary
                                )
                            )
                        )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_github_logo),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.fillMaxSize(0.5f)
                    )
                }
            }
        }

        if (collapseFraction > 0.9f) {
            Text(
                text = stringResource(R.string.app_name),
                style = GitGoTheme.typography.titleLarge,
                color = GitGoTheme.colors.textColor,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 18.dp)
                    .graphicsLayer { alpha = (collapseFraction - 0.9f) * 10 }
            )
        }
    }
}