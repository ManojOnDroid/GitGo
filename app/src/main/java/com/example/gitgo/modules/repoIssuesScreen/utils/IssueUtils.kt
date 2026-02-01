package com.example.gitgo.modules.repoIssuesScreen.utils

import androidx.compose.ui.graphics.Color
import java.text.SimpleDateFormat
import java.util.Locale

object IssueUtils {
    private val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    private val outputFormat = SimpleDateFormat("MMM dd", Locale.getDefault())

    fun formatDate(dateString: String?): String {
        if (dateString == null) return ""
        return try {
            val date = inputFormat.parse(dateString)
            date?.let { outputFormat.format(it) } ?: ""
        } catch (e: Exception) { "" }
    }

    fun parseColor(hex: String?): Color {
        return try {
            val colorInt = android.graphics.Color.parseColor("#$hex")
            Color(colorInt)
        } catch (e: Exception) {
            Color(0xFF5C8AFF)
        }
    }
}