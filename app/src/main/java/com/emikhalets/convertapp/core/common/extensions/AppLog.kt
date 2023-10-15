package com.emikhalets.convertapp.core.common.extensions

object AppLog {

    var isInitialized: Boolean = false
        private set

    fun initialize() {
        isInitialized = true
    }
}