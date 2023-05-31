package com.example.photocaptioner.data.database

import android.content.Context
import com.example.photocaptioner.data.UnsplashRepository

class AppContainer(private val context: Context) {
    private val unsplashRepository: UnsplashRepository = UnsplashRepository()
    private val albumsRepository: AlbumsRepository by lazy {
        OfflineAlbumsRepository(
            PhotoCaptionerDatabase.getDatabase(context).albumDao()
        )
    }
    private val imagesRepository: ImagesRepository by lazy {
        OfflineImagesRepository(
            PhotoCaptionerDatabase.getDatabase(context).imageDao()
        )
    }

    fun provideUnsplashRepository(): UnsplashRepository {
        return unsplashRepository
    }

    fun provideAlbumsRepository(): AlbumsRepository {
        return albumsRepository
    }

    fun provideImagesRepository(): ImagesRepository {
        return imagesRepository
    }
}
