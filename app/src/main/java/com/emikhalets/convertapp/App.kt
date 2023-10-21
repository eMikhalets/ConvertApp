package com.emikhalets.convertapp

import android.app.Application
import com.emikhalets.convertapp.core.common.extensions.AppLog
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AppLog.initialize()
    }
}
