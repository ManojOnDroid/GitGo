package com.example.gitgo.modules.homeScreen.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.gitgo.R
import com.example.gitgo.ui.theme.GitGoTheme

@Composable
fun FeaturesSection() {
    Column {
        Text(
            text = stringResource(R.string.what_you_can_do),
            style = GitGoTheme.typography.titleLarge,
            color = GitGoTheme.colors.textColor,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        val features = listOf(
            Triple(
                Icons.Default.Search,
                R.string.feature_search_title,
                R.string.feature_search_desc
            ) to GitGoTheme.colors.info,
            Triple(
                Icons.Default.Visibility,
                R.string.feature_details_title,
                R.string.feature_details_desc
            ) to GitGoTheme.colors.success,
            Triple(
                Icons.Default.BugReport,
                R.string.feature_issues_title,
                R.string.feature_issues_desc
            ) to GitGoTheme.colors.warning
        )

        features.forEach { (data, color) ->
            FeatureItemCard(
                icon = data.first,
                titleRes = data.second,
                descRes = data.third,
                themeColor = color
            )
        }
    }
}

@Composable
private fun FeatureItemCard(
    icon: ImageVector,
    @StringRes titleRes: Int,
    @StringRes descRes: Int,
    themeColor: Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = GitGoTheme.colors.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).background(
                GitGoTheme.colors.surface.copy(alpha = 0.9f)
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(50.dp),
                shape = CircleShape,
                color = themeColor.copy(alpha = 0.1f)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = themeColor,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(titleRes),
                    style = GitGoTheme.typography.titleMedium,
                    color = GitGoTheme.colors.textColor
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = stringResource(descRes),
                    style = GitGoTheme.typography.bodyMedium,
                    color = GitGoTheme.colors.textSecondary
                )
            }
        }
    }
}