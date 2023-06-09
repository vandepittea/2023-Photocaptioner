package com.example.photocaptioner.data

import DownloadWorker
import android.content.Context
import androidx.work.*

class WorkManagerDownloadRepository(private val context: Context) {

    fun downloadAlbum(albumId: Long) {
        val inputData = Data.Builder().putLong(KEY_ALBUM_ID, albumId).build()

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val downloadWorkRequest = OneTimeWorkRequestBuilder<DownloadWorker>()
            .setConstraints(constraints)
            .setInputData(inputData)
            .build()

        WorkManager.getInstance(context)
            .enqueueUniqueWork(WORK_TAG, ExistingWorkPolicy.KEEP, downloadWorkRequest)
    }

    companion object {
        private const val WORK_TAG = "download_album"
        const val KEY_ALBUM_ID = "album_id"
    }
}