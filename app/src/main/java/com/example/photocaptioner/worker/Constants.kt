package com.example.photocaptioner.worker

import android.os.Environment
import java.io.File

const val CHANNEL_ID = "download_channel"
const val NOTIFICATION_ID = 1
const val KEY_ALBUM_ID = "album_id"
const val OUTPUT_FILE_NAME = "album_images.zip"
const val WORK_TAG = "download_album"
val OUTPUT_DIRECTORY: File = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)