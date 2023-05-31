package com.example.photocaptioner.data

import android.content.Context
import com.example.photocaptioner.data.database.AlbumsRepository
import com.example.photocaptioner.data.database.OfflineAlbumsRepository
import com.example.photocaptioner.data.database.PhotoCaptionerDatabase

class AppContainer(private val context: Context) {
    private val unsplashRepository: UnsplashRepository = UnsplashRepository()
    private val albumsRepository: AlbumsRepository by lazy {
        OfflineAlbumsRepository(
            PhotoCaptionerDatabase.getDatabase(context).albumDao()
        )
    }

    fun provideUnsplashRepository(): UnsplashRepository {
        return unsplashRepository
    }

    fun provideAlbumsRepository(): AlbumsRepository {
        return albumsRepository
    }
}
