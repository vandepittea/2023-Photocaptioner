package com.example.photocaptioner.data

import android.content.Context
import android.net.Uri
import androidx.work.*
import com.example.photocaptioner.worker.DownloadWorker
import com.example.photocaptioner.worker.KEY_ALBUM_ID
import com.example.photocaptioner.worker.WORK_TAG

class WorkManagerDownloadRepository(private val context: Context) {

    fun downloadAlbum(albumId: Long, uri: Uri?) {
        val inputData = Data.Builder()
            .putLong(KEY_ALBUM_ID, albumId)
            .putString("uri", uri.toString())
            .build()

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
}