package com.example.gitgo.modules.profileScreen.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.gitgo.R
import com.example.gitgo.components.constants.Destination
import com.example.gitgo.components.network.models.UserDetailsResponse
import com.example.gitgo.modules.profileScreen.states.UserDetailsResponseState
import com.example.gitgo.modules.profileScreen.utils.ProfileFormatter
import com.example.gitgo.modules.profileScreen.viewmodel.ProfileScreenViewModel
import com.example.gitgo.ui.theme.GitGoTheme
import java.text.SimpleDateFormat
import java.util.Locale
@Composable
fun ProfileScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: ProfileScreenViewModel = hiltViewModel()
) {
    val userDetailsState by viewModel.userDetails.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        GitGoTheme.colors.gradientStart,
                        GitGoTheme.colors.gradientEnd,
                        GitGoTheme.colors.background
                    )
                )
            )
    ) {
        when (val state = userDetailsState) {
            is UserDetailsResponseState.Loading -> LoadingView()
            is UserDetailsResponseState.Error -> ErrorView(state.message)
            is UserDetailsResponseState.Success -> {
                ProfileContent(
                    user = state.userDetails,
                    onLogoutClick = {
                        val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE).edit()
                        prefs.remove("access_token")
                        prefs.apply()

                        Toast.makeText(context, "Logged out successfully", Toast.LENGTH_SHORT).show()

                        navController.navigate(Destination.LOGIN_SCREEN) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}


@Composable
private fun ProfileContent(
    user: UserDetailsResponse,
    onLogoutClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { ProfileHeaderCard(user) }
        item { UserInfoCard(user) }
        item { RepoStatsCard(user) }

        if (user.plan != null) {
            item { PlanInfoCard(user.plan, user.diskUsage) }
        }

        item { Spacer(modifier = Modifier.height(8.dp)) }
        item { LogoutCard(onLogoutClick) }
        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun ProfileHeaderCard(user: UserDetailsResponse) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = GitGoTheme.colors.card)
    ) {
        Box(
            modifier = Modifier.background(
                Brush.verticalGradient(
                    colors = listOf(
                        GitGoTheme.colors.primary.copy(alpha = 0.1f),
                        GitGoTheme.colors.card
                    )
                )
            )
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    modifier = Modifier.size(88.dp),
                    shape = CircleShape,
                    color = GitGoTheme.colors.outline,
                    shadowElevation = 8.dp
                ) {
                    GlideImage(
                        model = user.avatarUrl,
                        contentDescription = "Avatar",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(2.dp)
                            .clip(CircleShape)
                            .border(2.dp, GitGoTheme.colors.surface, CircleShape)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = user.name ?: "Unknown User",
                    style = GitGoTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = GitGoTheme.colors.textColor,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(4.dp))

                Surface(
                    color = GitGoTheme.colors.primary.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        text = "@${user.login ?: "unknown"}",
                        style = GitGoTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = GitGoTheme.colors.primary,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                    )
                }

                if (!user.bio.isNullOrBlank()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = user.bio,
                        style = GitGoTheme.typography.bodyLarge,
                        color = GitGoTheme.colors.textSecondary,
                        textAlign = TextAlign.Center,
                        lineHeight = 24.sp
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    StatItem(user.publicRepos ?: 0, stringResource(R.string.profile_repos))
                    StatItem(user.followers ?: 0, stringResource(R.string.profile_followers))
                    StatItem(user.following ?: 0, stringResource(R.string.profile_following))
                }
            }
        }
    }
}

@Composable
private fun UserInfoCard(user: UserDetailsResponse) {
    SectionCard(
        title = stringResource(R.string.profile_info_title),
        icon = Icons.Default.Info,
        iconColor = GitGoTheme.colors.info
    ) {
        if (!user.company.isNullOrBlank()) {
            InfoRow(Icons.Default.Business, stringResource(R.string.profile_label_company), user.company)
        }
        if (!user.location.isNullOrBlank()) {
            InfoRow(Icons.Default.LocationOn, stringResource(R.string.profile_label_location), user.location)
        }
        if (!user.email.isNullOrBlank()) {
            InfoRow(Icons.Default.Email, stringResource(R.string.profile_label_email), user.email)
        }
        if (!user.blog.isNullOrBlank()) {
            InfoRow(Icons.Default.Link, stringResource(R.string.profile_label_website), user.blog, isLink = true)
        }
        if (!user.twitterUsername.isNullOrBlank()) {
            InfoRow(Icons.Default.Tag, stringResource(R.string.profile_label_twitter), "@${user.twitterUsername}")
        }

        user.createdAt?.let {
            InfoRow(Icons.Default.CalendarMonth, stringResource(R.string.profile_label_joined), ProfileFormatter.date(it))
        }

        InfoRow(
            Icons.Default.AccountCircle,
            stringResource(R.string.profile_label_type),
            user.type ?: stringResource(R.string.profile_default_type)
        )
    }
}

@Composable
private fun RepoStatsCard(user: UserDetailsResponse) {
    SectionCard(
        title = stringResource(R.string.profile_repo_stats_title),
        icon = Icons.Default.Folder,
        iconColor = GitGoTheme.colors.success
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            RepoStatItem(
                user.publicRepos ?: 0,
                stringResource(R.string.profile_stat_public_repos),
                Icons.Default.Public,
                GitGoTheme.colors.success,
                Modifier.weight(1f)
            )
            RepoStatItem(
                user.ownedPrivateRepos ?: 0,
                stringResource(R.string.profile_stat_private_repos),
                Icons.Default.Lock,
                GitGoTheme.colors.warning,
                Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            RepoStatItem(
                user.publicGists ?: 0,
                stringResource(R.string.profile_stat_public_gists),
                Icons.Default.Code,
                GitGoTheme.colors.info,
                Modifier.weight(1f)
            )
            RepoStatItem(
                user.privateGists ?: 0,
                stringResource(R.string.profile_stat_private_gists),
                Icons.Default.VisibilityOff,
                GitGoTheme.colors.textSecondary,
                Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun PlanInfoCard(plan: UserDetailsResponse.Plan, diskUsage: Int?) {
    SectionCard(
        title = stringResource(R.string.profile_plan_title),
        icon = Icons.Default.Star,
        iconColor = GitGoTheme.colors.warning
    ) {
        InfoRow(
            Icons.Default.CardMembership,
            stringResource(R.string.profile_label_plan),
            plan.name ?: stringResource(R.string.profile_plan_free)
        )

        InfoRow(
            Icons.Default.Storage,
            stringResource(R.string.profile_label_space),
            ProfileFormatter.bytes(plan.space ?: 0)
        )

        diskUsage?.let {
            InfoRow(
                Icons.Default.SdCard,
                stringResource(R.string.profile_label_disk_usage),
                ProfileFormatter.bytes(it)
            )
        }
    }
}

@Composable
private fun LogoutCard(onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = GitGoTheme.colors.card),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Logout, null, tint = GitGoTheme.colors.error)
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(R.string.btn_logout),
                style = GitGoTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = GitGoTheme.colors.error
            )
        }
    }
}


@Composable
private fun SectionCard(
    title: String,
    icon: ImageVector,
    iconColor: Color,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = GitGoTheme.colors.card),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = CircleShape,
                    color = iconColor.copy(alpha = 0.1f),
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(icon, null, tint = iconColor, modifier = Modifier.padding(8.dp))
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(title, style = GitGoTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = GitGoTheme.colors.textColor)
            }
            Spacer(modifier = Modifier.height(16.dp))
            content()
        }
    }
}

@Composable
private fun StatItem(count: Int, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(ProfileFormatter.count(count), style = GitGoTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = GitGoTheme.colors.textColor)
        Text(label, style = GitGoTheme.typography.bodyMedium, color = GitGoTheme.colors.textSecondary)
    }
}

@Composable
private fun RepoStatItem(count: Int, label: String, icon: ImageVector, color: Color, modifier: Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = color.copy(alpha = 0.1f),
        border = BorderStroke(1.dp, color.copy(alpha = 0.3f))
    ) {
        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(icon, null, tint = color, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(ProfileFormatter.count(count), style = GitGoTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = GitGoTheme.colors.textColor)
            Text(label, style = GitGoTheme.typography.bodySmall, color = GitGoTheme.colors.textSecondary, textAlign = TextAlign.Center, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}

@Composable
private fun InfoRow(icon: ImageVector, label: String, value: String, isLink: Boolean = false) {
    Row(modifier = Modifier.padding(vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, null, tint = GitGoTheme.colors.iconSecondary, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(label, style = GitGoTheme.typography.bodyMedium, color = GitGoTheme.colors.textSecondary, modifier = Modifier.width(100.dp))
        Text(value, style = GitGoTheme.typography.bodyMedium, color = if (isLink) GitGoTheme.colors.primary else GitGoTheme.colors.textColor, maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.weight(1f))
    }
}

@Composable
private fun LoadingView() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(color = GitGoTheme.colors.primary)
        Spacer(modifier = Modifier.height(16.dp))
        Text(stringResource(R.string.profile_loading), color = GitGoTheme.colors.textSecondary)
    }
}

@Composable
private fun ErrorView(message: String) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(Icons.Default.Error, null, tint = GitGoTheme.colors.error, modifier = Modifier.size(48.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text(stringResource(R.string.profile_error_title), style = GitGoTheme.typography.titleLarge, color = GitGoTheme.colors.textColor)
        Text(message, color = GitGoTheme.colors.textSecondary, textAlign = TextAlign.Center)
    }
}