package com.example.photocaptioner.ui.screens.album

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photocaptioner.data.database.AlbumsRepository
import com.example.photocaptioner.model.AlbumWithImages
import com.example.photocaptioner.model.Photo
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EditPhotoViewModel(
    private val albumsRepository: AlbumsRepository
) : ViewModel() {
    private val photoId: Long = 0 //TODO: manier om photoId te krijgen vinden (navigation arguments van vb oef??)

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
}

data class EditPhotoUiState(
    val photoDetails: Photo = Photo(),
    val isEntryValid: Boolean = false
)