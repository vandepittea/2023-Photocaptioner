package com.example.photocaptioner

import com.example.photocaptioner.data.database.AppContainer
import android.app.Application

class PhotoCaptionerApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}