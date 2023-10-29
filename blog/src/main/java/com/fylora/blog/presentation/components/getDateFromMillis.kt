package com.fylora.blog.presentation.components

import androidx.compose.runtime.Composable
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun getDateFromMillis(
    milliseconds: Long
): String {
    val date = Date(milliseconds)
    val format = SimpleDateFormat("dd MMM yyyy", Locale.US)

    return format.format(date)
}