package com.me.presentation.base.utils

import java.text.SimpleDateFormat
import java.util.*


fun formatDate(date: Long): String {
    val formatter =
        SimpleDateFormat("hh:mm dd/MM/yyyy", Locale.ENGLISH)
    return formatter.format(Date(date))
}

fun formatDate(date: String): String {
    val formatter =
        SimpleDateFormat("hh:mm dd/MM/yyyy", Locale.ENGLISH)

    return if (date.toLongOrNull() != null) formatter.format(Date(date.toLong() * 1000)) else ""
}
