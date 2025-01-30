package com.example.shoppingeventsudemy.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatDate(millis: Long?, pattern: String = "EEE MMM dd yyyy") : String? {
    if (millis == null) return null
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())
    return formatter.format(Date(millis))

}