package com.example.photocaptioner.ui.screens.pictures

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.photocaptioner.data.database.AlbumsRepository
import com.example.photocaptioner.model.Photo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Locale

class ChoosePicturesSourceViewModel(
    savedStateHandle: SavedStateHandle,
    private val albumsRepository: AlbumsRepository
) : ViewModel() {
    val albumId: Long = checkNotNull(savedStateHandle[ChoosePicturesDestination.albumIdArg])

    suspend fun onGalleryResult(context: Context, uriList: List<Uri>) {
        uriList.forEach {
            val newUri = savePhotoInApplication(context, it)
            if (newUri.isNotEmpty()) {
                val newPhoto = Photo(
                    albumId = albumId,
                    filePath = newUri
                )
                albumsRepository.insertPhoto(newPhoto)
            }
        }
    }

    private suspend fun savePhotoInApplication(context: Context, contentUri: Uri): String {
        var result = ""
        withContext(Dispatchers.IO) {
            var bitmap: Bitmap?
            var inputStream: InputStream? = null
            val outputStream: FileOutputStream?
            try {
                inputStream = context.contentResolver.openInputStream(contentUri)
                bitmap = BitmapFactory.decodeStream(inputStream)

                val fileNameFormat = "yyyy-MM-dd-HH-mm-ss-SSS"
                val dir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                val file = File(dir, SimpleDateFormat(fileNameFormat, Locale.US).format(System.currentTimeMillis()) + ".jpg")
                outputStream = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                outputStream.flush()
                result = file.absolutePath
            } catch (e: IOException) {
                Log.e("ChoosePicturesSourceViewModel", "Error loading bitmap", e)
            } finally {
                inputStream?.close()
            }
        }
        return result
    }
}