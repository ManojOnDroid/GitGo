package com.example.gitgo.modules.homeScreen.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gitgo.ui.theme.*

@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        GitGoTheme.colors.gradient1,
                        GitGoTheme.colors.gradient2,
                        GitGoTheme.colors.background.copy(alpha = 0.9f),
                        GitGoTheme.colors.surfaceVariant
                    ),
                    startY = 0f,
                    endY = 1200f
                )
            )
    ) {
        // Background decorative elements
        BackgroundDecorations()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            // App Logo and Title
            AppHeader()

            Spacer(modifier = Modifier.height(40.dp))

            // Welcome Message
            WelcomeSection()

            Spacer(modifier = Modifier.height(32.dp))

            // Main Action Button
            MainActionButton(
                onClick = { navController.navigate("repoSearch") }
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Feature Cards
            FeatureSection()

            Spacer(modifier = Modifier.height(32.dp))

            // Quick Actions
            QuickActionsSection(navController = navController)

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun BackgroundDecorations() {
    Box(modifier = Modifier.fillMaxSize()) {
        // Top right circle
        Surface(
            modifier = Modifier
                .size(150.dp)
                .offset(x = 100.dp, y = (-50).dp)
                .align(Alignment.TopEnd),
            shape = CircleShape,
            color = GitGoTheme.colors.primary.copy(alpha = 0.1f)
        ) {}

        // Bottom left circle
        Surface(
            modifier = Modifier
                .size(120.dp)
                .offset(x = (-30).dp, y = 50.dp)
                .align(Alignment.BottomStart),
            shape = CircleShape,
            color = GitGoTheme.colors.secondary.copy(alpha = 0.08f)
        ) {}

        // Middle right small circle
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

@Composable
private fun AppHeader() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // App Icon
        Surface(
            modifier = Modifier.size(100.dp),
            shape = RoundedCornerShape(24.dp),
            color = GitGoTheme.colors.surface,
            shadowElevation = 12.dp
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
                            ),
                        )
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.AccountTree,
                    contentDescription = "GitGo Logo",
                    tint = GitGoTheme.colors.onPrimary,
                    modifier = Modifier.size(50.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // App Name
        Text(
            text = "GitGo",
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.ExtraBold,
            color = GitGoTheme.colors.surface,
            letterSpacing = 1.sp
        )

        Text(
            text = "Explore GitHub Repositories",
            style = MaterialTheme.typography.bodyLarge,
            color = GitGoTheme.colors.surface.copy(alpha = 0.8f),
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun WelcomeSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = GitGoTheme.colors.surface.copy(alpha = 0.9f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.WavingHand,
                contentDescription = "Welcome",
                tint = GitGoTheme.colors.primary,
                modifier = Modifier.size(32.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Welcome to GitGo!",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = GitGoTheme.colors.textColor,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Discover and explore GitHub repositories with ease. Search for projects, view repository details, track issues, and stay connected with the developer community.",
                style = MaterialTheme.typography.bodyLarge,
                color = GitGoTheme.colors.textSecondary,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp
            )
        }
    }
}

@Composable
private fun MainActionButton(
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = GitGoTheme.colors.primary
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 8.dp,
            pressedElevation = 4.dp
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Start Exploring Repositories",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
private fun FeatureSection() {
    Column {
        Text(
            text = "What You Can Do",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = GitGoTheme.colors.surface,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            FeatureCard(
                icon = Icons.Default.Search,
                title = "Search Repositories",
                description = "Find GitHub repositories by name, description, or programming language",
                color = GitGoTheme.colors.info
            )

            FeatureCard(
                icon = Icons.Default.Visibility,
                title = "Repository Details",
                description = "View comprehensive information about repositories including stats and features",
                color = GitGoTheme.colors.success
            )

            FeatureCard(
                icon = Icons.Default.BugReport,
                title = "Track Issues",
                description = "Browse and filter repository issues to stay updated with project status",
                color = GitGoTheme.colors.warning
            )
        }
    }
}

@Composable
private fun FeatureCard(
    icon: ImageVector,
    title: String,
    description: String,
    color: androidx.compose.ui.graphics.Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = GitGoTheme.colors.surface.copy(alpha = 0.9f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(50.dp),
                shape = CircleShape,
                color = color.copy(alpha = 0.1f)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = color,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = GitGoTheme.colors.textColor
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = GitGoTheme.colors.textSecondary,
                    lineHeight = 20.sp
                )
            }
        }
    }
}

@Composable
private fun QuickActionsSection(
    navController: NavController
) {
    Column {
        Text(
            text = "Quick Actions",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = GitGoTheme.colors.surface.copy(alpha = 0.9f),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            QuickActionButton(
                icon = Icons.Default.TrendingUp,
                label = "Trending",
                onClick = { /* Navigate to trending */ },
                modifier = Modifier.weight(1f)
            )

            QuickActionButton(
                icon = Icons.Default.Star,
                label = "Popular",
                onClick = { /* Navigate to popular */ },
                modifier = Modifier.weight(1f)
            )

            QuickActionButton(
                icon = Icons.Default.History,
                label = "Recent",
                onClick = { /* Navigate to recent */ },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun QuickActionButton(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = GitGoTheme.colors.surface.copy(alpha = 0.8f)
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
                contentDescription = label,
                tint = GitGoTheme.colors.primary,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = GitGoTheme.colors.textColor,
                textAlign = TextAlign.Center
            )
        }
    }
}