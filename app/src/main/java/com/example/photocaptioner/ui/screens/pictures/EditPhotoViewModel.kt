package com.example.photocaptioner.ui.screens.pictures

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.FileProvider
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photocaptioner.data.database.AlbumsRepository
import com.example.photocaptioner.intent.convertPhotoToFile
import com.example.photocaptioner.intent.getFileUri
import com.example.photocaptioner.model.Photo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class EditPhotoViewModel(
    savedStateHandle: SavedStateHandle,
    private val albumsRepository: AlbumsRepository
) : ViewModel() {
    private val photoId: Long = checkNotNull(savedStateHandle[EditPhotoDestination.photoIdArg])

    var editPhotoUiState by mutableStateOf(EditPhotoUiState())

    init {
        viewModelScope.launch {
            editPhotoUiState = EditPhotoUiState(
                albumsRepository.getPhoto(photoId)
                    .first()
            )
        }
    }

    fun updatePhotoDescriptionUiState(description: String) {
        editPhotoUiState = editPhotoUiState.copy(
            photoDetails = editPhotoUiState.photoDetails.copy(
                description = description
            )
        )
    }

    suspend fun saveItem() {
        albumsRepository.updatePhoto(editPhotoUiState.photoDetails)
    }

    fun openPhotoInAnotherApp(context: Context) {
        var photoFile: File
        runBlocking { photoFile = convertPhotoToFile(context, editPhotoUiState.photoDetails) }
        val photoUri = photoFile?.let {getFileUri(context, it)}

        val viewIntent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(
                photoUri,
                context.contentResolver.getType(photoUri!!)
            )
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        val chooserIntent = Intent.createChooser(viewIntent, "View Image")
        context.startActivity(chooserIntent)
    }
}

data class EditPhotoUiState(
    val photoDetails: Photo = Photo()
)