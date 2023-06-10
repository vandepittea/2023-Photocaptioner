package com.example.photocaptioner.ui.screens.pictures

import android.content.Context
import android.os.Environment
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photocaptioner.data.database.AlbumsRepository
import com.example.photocaptioner.model.Photo
import com.example.photocaptioner.ui.screens.album.AddAlbumDestination
import com.example.photocaptioner.ui.screens.album.AlbumDetailDestination
import com.example.photocaptioner.ui.screens.album.EditAlbumDestination
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Locale
import java.util.concurrent.Executors

class CameraPageViewModel(
    savedStateHandle: SavedStateHandle,
    private val albumsRepository: AlbumsRepository
) : ViewModel() {
    private val albumId: Long = checkNotNull(savedStateHandle[EditAlbumDestination.albumIdArg])

    fun savePicture(
        context: Context,
        imageCapture: ImageCapture,
        onTakePictureFromHome: (photoId: Long) -> Unit,
        navigateBack: (route: String, include: Boolean) -> Unit
    ) {
        val fileNameFormat = "yyyy-MM-dd-HH-mm-ss-SSS"

        val photoFile = File(
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            SimpleDateFormat(fileNameFormat, Locale.US).format(System.currentTimeMillis()) + ".jpg"
        )

        val outputFileOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputFileOptions,
            Executors.newSingleThreadExecutor(),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exception: ImageCaptureException) {
                    Log.d("Error saving image", "Error saving image: ${exception.message}", exception)
                }

                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        val newPhoto = Photo(
                        albumId = albumId,
                        filePath = outputFileResults.savedUri.toString(),
                        createdAt = LocalDateTime.now(),
                    )
                    viewModelScope.launch {
                        val newPhotoId = albumsRepository.insertPhoto(newPhoto)
                        when (albumId) {
                            -1L -> {
                                navigateBack(AddAlbumDestination.routeWithArgs, false)
                            }
                            -2L -> {
                                onTakePictureFromHome(newPhotoId)
                            }
                            else -> {
                                navigateBack(AlbumDetailDestination.routeWithArgs, false)
                            }
                        }
                    }
                }
            }
        )
    }
}