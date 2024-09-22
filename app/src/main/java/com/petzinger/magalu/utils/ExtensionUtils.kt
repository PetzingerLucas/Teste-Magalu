package com.petzinger.magalu.utils

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