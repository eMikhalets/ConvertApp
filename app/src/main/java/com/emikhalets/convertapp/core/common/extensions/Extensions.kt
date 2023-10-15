package com.emikhalets.convertapp.core.common.extensions

import com.emikhalets.convertapp.domain.AppResult
import com.emikhalets.convertapp.domain.StringValue

suspend fun <T> execute(block: suspend () -> T): AppResult<T> {
    return try {
        AppResult.Success(block())
    } catch (e: Exception) {
        loge(e)
        AppResult.Failure(StringValue.create())
    }
}
