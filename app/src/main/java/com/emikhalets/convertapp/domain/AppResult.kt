package com.emikhalets.convertapp.domain

import com.emikhalets.convertapp.core.common.CodeFailure
import com.emikhalets.convertapp.core.common.CodeSuccess

sealed class AppResult<out T> {

    class Success<T>(val data: T, val code: Int = CodeSuccess) : AppResult<T>()

    class Failure(val error: StringValue, val code: Int = CodeFailure) : AppResult<Nothing>()
}
