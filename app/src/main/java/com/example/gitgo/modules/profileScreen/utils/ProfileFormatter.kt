package com.example.gitgo.modules.profileScreen.utils

import java.text.SimpleDateFormat
import java.util.Locale

object ProfileFormatter {
    private val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    private val outputFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())

    fun date(dateString: String): String {
        return try {
            val date = inputFormat.parse(dateString)
            date?.let { outputFormat.format(it) } ?: dateString
        } catch (e: Exception) { dateString }
    }

    fun count(count: Int): String {
        return when {
            count >= 1_000_000 -> "%.1fM".format(count / 1_000_000.0)
            count >= 1_000 -> "%.1fK".format(count / 1_000.0)
            else -> count.toString()
        }
    }

    fun bytes(bytes: Int): String {
        return when {
            bytes >= 1_073_741_824 -> "%.1f GB".format(bytes / 1_073_741_824.0)
            bytes >= 1_048_576 -> "%.1f MB".format(bytes / 1_048_576.0)
            bytes >= 1024 -> "%.1f KB".format(bytes / 1024.0)
            else -> "$bytes bytes"
        }
    }
}