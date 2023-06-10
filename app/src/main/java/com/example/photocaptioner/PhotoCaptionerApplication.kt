package com.example.photocaptioner

import com.example.photocaptioner.data.AppContainer
import android.app.Application

class PhotoCaptionerApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        PhotoCaptionerApplicationHolder.instance = this
        container = AppContainer(this)
    }
}

object PhotoCaptionerApplicationHolder {
    lateinit var instance: PhotoCaptionerApplication
}