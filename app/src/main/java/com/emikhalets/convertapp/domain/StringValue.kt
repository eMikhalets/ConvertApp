package com.emikhalets.convertapp.domain

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.emikhalets.convertapp.R
import com.emikhalets.convertapp.core.common.EmptyString

fun StringValue.asString(context: Context): String {
    val internal = context.getString(R.string.error_internal)
    return when (this) {
        StringValue.Empty -> EmptyString
        StringValue.InternalError -> internal
        is StringValue.Message -> text
        is StringValue.Error -> throwable.message ?: internal
        is StringValue.Resource -> context.getString(resId, *args)
    }
}

@Composable
fun StringValue.asString(): String {
    val internal = stringResource(R.string.error_internal)
    return when (this) {
        StringValue.Empty -> internal
        StringValue.InternalError -> internal
        is StringValue.Message -> text
        is StringValue.Error -> throwable.message ?: internal
        is StringValue.Resource -> stringResource(resId, *args)
    }
}

sealed class StringValue {

    object Empty : StringValue()
    object InternalError : StringValue()
    class Error(val throwable: Throwable) : StringValue()
    class Message(val text: String) : StringValue()
    class Resource(val resId: Int, vararg val args: Any) : StringValue()

    companion object {

        fun create(): StringValue = InternalError
        fun create(message: String): StringValue = Message(message)
        fun create(stringRes: Int): StringValue = Resource(stringRes)
        fun create(throwable: Throwable): StringValue = Error(throwable)
    }
}
