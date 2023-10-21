package com.emikhalets.convertapp.core.common.date

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long?.formatWithPattern(pattern: String): String {
    val date = this?.let { Date(it) } ?: Date().time
    return SimpleDateFormat(pattern, Locale.getDefault()).format(date)
}

fun Long?.formatFullDate() = formatWithPattern("dd MMMM yyyy")
fun Long?.formatFullWithWeekDate() = formatWithPattern("EEEE, dd MMMM yyyy")
fun Long?.formatMidDate() = formatWithPattern("dd MMM yyyy")
fun Long?.formatMidWithWeekDate() = formatWithPattern("EEE, dd MMM yyyy")
fun Long?.formatShortDate() = formatWithPattern("dd/MM/yyyy")
fun Long?.formatShortWithWeekDate() = formatWithPattern("EEE, dd/MM/yyyy")