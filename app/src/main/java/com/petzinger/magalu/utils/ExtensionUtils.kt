package com.petzinger.magalu.utils

import android.view.View
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

fun String.parsedDate(): String {
    val utcFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    utcFormat.timeZone = TimeZone.getTimeZone("UTC")

    val date = utcFormat.parse(this)

    val brFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    brFormat.timeZone = TimeZone.getDefault()

    return brFormat.format(date)
}

fun View.setVisibility(isLoading: Boolean) {
    this.visibility = if (isLoading) View.GONE else View.VISIBLE
}