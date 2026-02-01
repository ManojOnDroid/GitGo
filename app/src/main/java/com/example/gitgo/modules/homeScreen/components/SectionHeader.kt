package com.example.gitgo.modules.homeScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.gitgo.R
import com.example.gitgo.modules.homeScreen.models.QuickAction
import com.example.gitgo.ui.theme.GitGoTheme

@Composable
fun SectionHeader(currentAction: QuickAction) {
    val (titleRes, icon) = when (currentAction) {
        QuickAction.TRENDING -> R.string.header_trending to Icons.Default.TrendingUp
        QuickAction.POPULAR -> R.string.header_popular to Icons.Default.Star
        QuickAction.RECENT -> R.string.header_recent to Icons.Default.History
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(titleRes),
            style = GitGoTheme.typography.titleLarge,
            color = GitGoTheme.colors.textColor
        )

        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = GitGoTheme.colors.textSecondary,
            modifier = Modifier.size(24.dp)
        )
    }
}