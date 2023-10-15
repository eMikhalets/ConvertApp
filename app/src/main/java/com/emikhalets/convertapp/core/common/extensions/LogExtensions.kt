package com.emikhalets.convertapp.core.common.extensions

import android.util.Log
import com.emikhalets.convertapp.core.common.EmptyString

fun Any.logd(message: String) {
    if (AppLog.isInitialized) {
        val tag = this::class.java.simpleName
        Log.d(tag, message)
    }
}

fun Any.loge(message: String) {
    if (AppLog.isInitialized) {
        val tag = this::class.java.simpleName
        Log.e(tag, message)
    }
}

fun Any.loge(throwable: Throwable) {
    if (AppLog.isInitialized) {
        val tag = this::class.java.simpleName
        Log.e(tag, EmptyString, throwable)
    }
}