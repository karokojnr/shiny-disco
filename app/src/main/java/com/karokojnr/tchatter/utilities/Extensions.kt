package com.karokojnr.tchatter.utilities

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.periodUntil

fun String.isoToTimeAgo(): String {
    return try {
        val instant = Instant.parse(this)
        val now = Clock.System.now()
        val timeZone = TimeZone.currentSystemDefault()

        val period = instant.periodUntil(now, timeZone)
        val days = period.days
        val hours = period.hours
        val minutes = period.minutes
        val seconds = period.seconds

        when {
            days > 0 -> "$days day${if (days > 1) "s" else ""} ago"
            hours > 0 -> "$hours hour${if (hours > 1) "s" else ""} ago"
            minutes > 0 -> "$minutes minute${if (minutes > 1) "s" else ""} ago"
            else -> "$seconds second${if (seconds > 1) "s" else ""} ago"
        }
    } catch (e: Exception) {
        "Invalid date"
    }
}