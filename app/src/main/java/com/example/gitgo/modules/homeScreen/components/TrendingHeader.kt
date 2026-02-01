package com.example.gitgo.modules.homeScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.gitgo.R
import com.example.gitgo.ui.theme.GitGoTheme

@Composable
fun TrendingHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.trending_title),
            style = GitGoTheme.typography.titleLarge,
            color = GitGoTheme.colors.textColor
        )

        Icon(
            imageVector = Icons.Default.TrendingUp,
            contentDescription = null,
            tint = GitGoTheme.colors.surface.copy(alpha = 0.8f),
            modifier = Modifier.size(24.dp)
        )
    }
}