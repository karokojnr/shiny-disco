package com.karokojnr.tchatter.data.model

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toInstant

fun String.toInstant(): kotlinx.datetime.Instant {
    return this.toInstant()
}

fun LocalDateTime.toIso8601String(): String {
    var dateString = this.toString()
    val dateParts = dateString.split(".")
    dateString = dateParts[0]
    dateString += "Z"
    return dateString
}