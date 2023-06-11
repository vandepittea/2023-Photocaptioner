package com.example.photocaptioner.intent

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.example.photocaptioner.model.Photo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

suspend fun convertPhotoToFile(context: Context, photo: Photo): File {
    val tempDir = context.cacheDir
    val fileName = "photocaptioner.jpg"
    val tempFile = File(tempDir, fileName)

    if (!photo.filePath.startsWith("http")) {
        var uri: Uri = if(!photo.filePath.startsWith("file://")) {
            Uri.parse("file://" + photo.filePath)
        } else{
            Uri.parse(photo.filePath)
        }

        val inputStream = context.contentResolver.openInputStream(uri)

        inputStream?.use { input ->
            FileOutputStream(tempFile).use { output ->
                input.copyTo(output)
            }
        }
    } else {
        downloadImage(photo.filePath, tempFile)
    }

    return tempFile
}

private suspend fun downloadImage(url: String, outputFile: File) {
    withContext(Dispatchers.IO) {
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.connect()

        val inputStream = connection.inputStream
        val outputStream = FileOutputStream(outputFile)

        try {
            inputStream.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            inputStream?.close()
            outputStream.close()
            connection.disconnect()
        }
    }
}

fun getFileUri(context: Context, file: File): Uri {
    return FileProvider.getUriForFile(context, "com.example.photocaptioner.fileprovider", file)
}