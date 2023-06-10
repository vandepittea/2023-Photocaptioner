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
import com.example.photocaptioner.model.Photo
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class EditPhotoViewModel(
    savedStateHandle: SavedStateHandle,
    private val albumsRepository: AlbumsRepository
) : ViewModel() {
    private val photoId: Long = checkNotNull(savedStateHandle[EditPhotoDestination.photoIdArg])

    var editPhotoUiState by mutableStateOf(EditPhotoUiState())
        private set

    init {
        viewModelScope.launch {
            editPhotoUiState = EditPhotoUiState(
                albumsRepository.getPhoto(photoId)
                    .first()
            )
        }
    }

    fun updateAlbumDescriptionUiState(description: String) {
        editPhotoUiState = EditPhotoUiState(
            photoDetails = editPhotoUiState.photoDetails.copy(description = description),
            isEntryValid = validateInput(editPhotoUiState.photoDetails)
        )
    }

    private fun validateInput(uiState: Photo = editPhotoUiState.photoDetails): Boolean {
        return with(uiState) {
            description.isNotBlank()
        }
    }

    suspend fun saveItem() {
        if (validateInput()) {
            albumsRepository.updatePhoto(editPhotoUiState.photoDetails)
        }
    }

    fun editPhoto(context: Context) {
        val photoFile = convertPhotoToPhile(context, editPhotoUiState.photoDetails)
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

    private fun convertPhotoToPhile(context: Context, photo: Photo): File? {
        val uri = Uri.parse(photo.filePath)
        val inputStream = context.contentResolver.openInputStream(uri)
        val tempDir = context.cacheDir
        val fileName = "photocaptioner-photo.jpg"
        val tempFile = File(tempDir, fileName)
        val outputStream = FileOutputStream(tempFile)

        try {
            inputStream?.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }
            return tempFile
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            inputStream?.close()
            outputStream.close()
        }

        return null
    }

    private fun getFileUri(context: Context, file: File): Uri {
        return FileProvider.getUriForFile(context, "com.example.photocaptioner.fileprovider", file)
    }
}

data class EditPhotoUiState(
    val photoDetails: Photo = Photo(),
    val isEntryValid: Boolean = false
)