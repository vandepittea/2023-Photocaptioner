package com.example.photocaptioner.data

import android.content.Context
import com.example.photocaptioner.data.api.UnsplashRepository
import com.example.photocaptioner.data.api.UnsplashRepositoryImplementation
import com.example.photocaptioner.data.database.AlbumsRepository
import com.example.photocaptioner.data.database.OfflineAlbumsRepository
import com.example.photocaptioner.data.database.PhotoCaptionerDatabase
import com.example.photocaptioner.data.worker.WorkManagerDownloadRepositoryImplementation

class AppContainer(private val context: Context) {
    private val unsplashRepository: UnsplashRepository = UnsplashRepositoryImplementation()
    private val workManagerDownloadRepository: WorkManagerDownloadRepositoryImplementation = WorkManagerDownloadRepositoryImplementation(context)
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

    fun provideWorkManagerDownloadRepository(): WorkManagerDownloadRepositoryImplementation {
        return workManagerDownloadRepository
    }
}
