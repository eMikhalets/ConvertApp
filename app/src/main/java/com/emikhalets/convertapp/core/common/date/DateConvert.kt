package com.emikhalets.convertapp.core.common.date

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

internal val timezone = ZoneId.systemDefault()

fun Long?.localDate(): LocalDate {
    val instant = Instant.ofEpochMilli(this ?: Date().time)
    return LocalDateTime.ofInstant(instant, timezone).toLocalDate()
}

fun Long?.localDateTime(): LocalDateTime {
    val instant = Instant.ofEpochMilli(this ?: Date().time)
    return LocalDateTime.ofInstant(instant, timezone)
}

fun LocalDate.timestamp(zone: ZoneId = timezone): Long {
    return atStartOfDay(zone).toInstant().toEpochMilli()
}

fun LocalDateTime.timestamp(zone: ZoneId = timezone): Long {
    return atZone(zone).toInstant().toEpochMilli()
}
