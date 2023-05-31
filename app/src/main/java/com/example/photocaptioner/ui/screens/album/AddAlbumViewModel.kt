package com.example.photocaptioner.ui.screens.album

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.photocaptioner.data.database.AlbumsRepository
import com.example.photocaptioner.model.AlbumWithImages

class AddAlbumViewModel(private val albumsRepository: AlbumsRepository) : ViewModel() {
    var allAlbumUiState by mutableStateOf(AddAlbumUiState())
        private set

    fun updateAlbumTitleUiState(title: String) {
        allAlbumUiState = AddAlbumUiState(
            albumDetails = allAlbumUiState.albumDetails.copy(album = allAlbumUiState.albumDetails.album.copy(name = title)),
            isEntryValid = validateInput(allAlbumUiState.albumDetails)
        )
    }

    fun updateAlbumDescriptionUiState(description: String) {
        allAlbumUiState = AddAlbumUiState(
            albumDetails = allAlbumUiState.albumDetails.copy(album = allAlbumUiState.albumDetails.album.copy(description = description)),
            isEntryValid = validateInput(allAlbumUiState.albumDetails)
        )
    }

    suspend fun saveItem() {
        if (validateInput()) {
            albumsRepository.insertAlbum(allAlbumUiState.albumDetails.album)
            allAlbumUiState.albumDetails.photos.forEach {
                albumsRepository.insertPhoto(it)
            }
        }
    }

    private fun validateInput(uiState: AlbumWithImages = allAlbumUiState.albumDetails): Boolean {
        return with(uiState) {
            album.name.isNotBlank() && album.description.isNotBlank()
        }
    }
}

data class AddAlbumUiState(
    val albumDetails: AlbumWithImages = AlbumWithImages(),
    val isEntryValid: Boolean = false
)