package com.example.gitgo.modules.homeScreen.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.gitgo.R
import com.example.gitgo.ui.theme.GitGoTheme

@Composable
fun QuickActionsSection() {
    Column {
        Text(
            text = stringResource(R.string.quick_actions),
            style = GitGoTheme.typography.titleMedium,
            color = GitGoTheme.colors.textColor,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            QuickActionCard(
                icon = Icons.Default.TrendingUp,
                labelRes = R.string.action_trending,
                onClick = { /* Handle Trending Click */ },
                modifier = Modifier.weight(1f)
            )
            QuickActionCard(
                icon = Icons.Default.Star,
                labelRes = R.string.action_popular,
                onClick = { /* Handle Popular Click */ },
                modifier = Modifier.weight(1f)
            )
            QuickActionCard(
                icon = Icons.Default.History,
                labelRes = R.string.action_recent,
                onClick = { /* Handle Recent Click */ },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun QuickActionCard(
    icon: ImageVector,
    @StringRes labelRes: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = GitGoTheme.colors.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = GitGoTheme.colors.primary,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(labelRes),
                style = GitGoTheme.typography.bodyMedium,
                color = GitGoTheme.colors.textColor,
                textAlign = TextAlign.Center
            )
        }
    }
}