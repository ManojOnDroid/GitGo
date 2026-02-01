package com.example.gitgo.modules.homeScreen.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.gitgo.R
import com.example.gitgo.ui.theme.GitGoTheme

@Composable
fun SearchButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = GitGoTheme.colors.primary),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
    ) {
        Icon(Icons.Default.Search, contentDescription = null)
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = stringResource(R.string.start_exploring),
            style = GitGoTheme.typography.titleMedium.copy(
                color = GitGoTheme.colors.whiteText
            ),
            fontWeight = FontWeight.Bold
        )
    }
}