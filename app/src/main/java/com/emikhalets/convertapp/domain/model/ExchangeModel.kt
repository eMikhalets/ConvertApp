package com.emikhalets.convertapp.domain.model

import com.emikhalets.convertapp.core.common.ZeroLong
import com.emikhalets.convertapp.core.common.date.localDate
import com.emikhalets.convertapp.core.common.date.timestamp
import java.util.Date

data class ExchangeModel(
    val id: Long,
    val main: String,
    val sub: String,
    val value: Double,
    val date: Long,
) {

    constructor(main: String) : this(main, "")

    constructor(main: String, sub: String) : this(0, main, sub, 0.0, 0)

    fun getCode(): String {
        return "$main$sub"
    }

    fun containsPair(base: String, currency: String): Boolean {
        return (main == base && sub == currency) || (main == currency && sub == base)
    }

    fun calculate(base: String, value: Long): Long {
        return when (base) {
            main -> (value * this.value).toLong()
            sub -> (value * (1 / this.value)).toLong()
            else -> 0
        }
    }

    fun isNeedUpdate(): Boolean {
        if (sub.isEmpty()) return false
        if (date == ZeroLong) return true
        val startOfNextDay = date
            .localDate()
            .atStartOfDay()
            .plusDays(1)
            .timestamp()
        return startOfNextDay < Date().time
    }
}

fun List<ExchangeModel>.getUpdatedDate(): Long {
    return minOf { it.date }
}
