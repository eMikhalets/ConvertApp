package com.emikhalets.convertapp.core.common.extensions

import com.emikhalets.convertapp.domain.model.AppResult
import com.emikhalets.convertapp.domain.model.StringValue

suspend fun <T> executeDatabase(block: suspend () -> T): AppResult<T> {
    return try {
        AppResult.Success(block())
    } catch (e: Exception) {
        loge(e)
        AppResult.Failure(StringValue.create())
    }
}
