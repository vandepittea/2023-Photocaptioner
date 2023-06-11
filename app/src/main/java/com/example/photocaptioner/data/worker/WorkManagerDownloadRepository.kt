package com.example.photocaptioner.data.worker

import android.net.Uri

interface WorkManagerDownloadRepository {
    fun downloadAlbum(albumId: Long, uri: Uri?)
}