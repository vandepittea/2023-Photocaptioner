package com.example.photocaptioner.worker

import android.content.Context
import android.net.Uri
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.documentfile.provider.DocumentFile
import com.example.photocaptioner.PhotoCaptionerApplicationHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.io.*
import java.net.URL
import java.time.LocalDateTime
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

class DownloadWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    private val albumsRepository = PhotoCaptionerApplicationHolder.instance.container.provideAlbumsRepository()

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val albumId = inputData.getLong(KEY_ALBUM_ID, -1)
        val uri: String? = inputData.getString("uri")
        val album = albumsRepository.getAlbum(albumId).first()

        if (albumId == -1L) {
            return@withContext Result.failure()
        }

        val albumImages = album.photos.map { convertImageToByteArray(it.filePath) }

        if (albumImages.isEmpty()) {
            return@withContext Result.failure()
        }

        val documentFile = DocumentFile.fromTreeUri(applicationContext, Uri.parse(uri))
        val zipFileName = album.album.name + LocalDateTime.now() + ".zip"
        val zipFile = documentFile?.createFile("application/zip", zipFileName)

        zipFile?.let { outputFile ->
            applicationContext.contentResolver.openOutputStream(outputFile.uri)?.use { outputStream ->
                ZipOutputStream(BufferedOutputStream(outputStream)).use { zipOutputStream ->
                    albumImages.forEachIndexed { index, image ->
                        val zipEntry = ZipEntry("${index + 1}.jpg")
                        zipOutputStream.putNextEntry(zipEntry)
                        zipOutputStream.write(image)
                        zipOutputStream.closeEntry()
                    }
                }
            }
        }

        showDownloadCompleteNotification(applicationContext)

        return@withContext Result.success()
    }

    private fun convertImageToByteArray(imagePath: String): ByteArray {
        val file = File(imagePath)
        if (file.exists()) {
            // Local image file
            val inputStream = FileInputStream(file)
            return inputStream.use { input ->
                val outputStream = ByteArrayOutputStream()
                val buffer = ByteArray(1024)
                var bytesRead: Int
                while (input.read(buffer).also { bytesRead = it } != -1) {
                    outputStream.write(buffer, 0, bytesRead)
                }
                outputStream.toByteArray()
            }
        } else {
            // Online image URL
            val url = URL(imagePath)
            return url.openStream().use { input ->
                val outputStream = ByteArrayOutputStream()
                val buffer = ByteArray(1024)
                var bytesRead: Int
                while (input.read(buffer).also { bytesRead = it } != -1) {
                    outputStream.write(buffer, 0, bytesRead)
                }
                outputStream.toByteArray()
            }
        }
    }
}