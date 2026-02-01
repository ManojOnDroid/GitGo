package com.example.gitgo.modules.homeScreen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gitgo.ui.theme.GitGoTheme

@Composable
fun HomeBackgroundDecor() {
    Box(modifier = Modifier.fillMaxSize()) {
        Surface(
            modifier = Modifier
                .size(150.dp)
                .offset(x = 100.dp, y = (-50).dp)
                .align(Alignment.TopEnd),
            shape = CircleShape,
            color = GitGoTheme.colors.primary.copy(alpha = 0.1f)
        ) {}

        Surface(
            modifier = Modifier
                .size(120.dp)
                .offset(x = (-30).dp, y = 50.dp)
                .align(Alignment.BottomStart),
            shape = CircleShape,
            color = GitGoTheme.colors.secondary.copy(alpha = 0.08f)
        ) {}

        Surface(
            modifier = Modifier
                .size(80.dp)
                .offset(x = 40.dp)
                .align(Alignment.CenterEnd),
            shape = CircleShape,
            color = GitGoTheme.colors.info.copy(alpha = 0.06f)
        ) {}
    }
}