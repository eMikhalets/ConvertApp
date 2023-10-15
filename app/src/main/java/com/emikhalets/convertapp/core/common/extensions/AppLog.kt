package com.emikhalets.convertapp.core.common.extensions

object AppLog {

    var isInitialized: Boolean = false
        private set

    const val DefaultTag: String = "ConvertAppLog"

    fun initialize() {
        isInitialized = true
    }
}
