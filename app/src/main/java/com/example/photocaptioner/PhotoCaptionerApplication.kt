package com.example.photocaptioner

import AppContainer
import android.app.Application

class PhotoCaptionerApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}