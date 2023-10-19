package com.emikhalets.convertapp.core.common.date

import com.emikhalets.convertapp.core.common.date.localDate
import com.emikhalets.convertapp.core.common.date.timestamp

fun Long.startOfNextDay(): Long {
    return localDate().atStartOfDay().plusDays(1).timestamp()
}
