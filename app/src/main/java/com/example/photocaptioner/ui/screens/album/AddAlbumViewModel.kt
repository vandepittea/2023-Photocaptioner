package com.example.photocaptioner.ui.screens.album

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photocaptioner.data.database.AlbumsRepository
import com.example.photocaptioner.model.AlbumWithImages
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddAlbumViewModel(
    private val albumsRepository: AlbumsRepository
) : ViewModel() {
    var addAlbumUiState by mutableStateOf(AlbumUiState())
        private set

    init {
        viewModelScope.launch {
            albumsRepository.getPhotosWithoutAlbum().collect {
                addAlbumUiState = AlbumUiState(
                    albumDetails = AlbumWithImages(
                        album = addAlbumUiState.albumDetails.album,
                        photos = it
                    )
                )
            }
        }
    }

    fun updateAlbumTitleUiState(title: String) {
        addAlbumUiState = addAlbumUiState.copy(
            albumDetails = addAlbumUiState.albumDetails.copy(album = addAlbumUiState.albumDetails.album.copy(name = title)),
            isEntryValid = validateInput(addAlbumUiState.albumDetails))
    }

    fun updateAlbumDescriptionUiState(description: String) {
        addAlbumUiState = addAlbumUiState.copy(
            albumDetails = addAlbumUiState.albumDetails.copy(album = addAlbumUiState.albumDetails.album.copy(description = description)),
            isEntryValid = validateInput(addAlbumUiState.albumDetails))
    }

    suspend fun saveItem(navigateToAlbums: () -> Unit) {
        if (validateInput()) {
            withContext(Dispatchers.IO) {
                val albumId = albumsRepository.insertAlbum(addAlbumUiState.albumDetails.album)
                albumsRepository.updatePhotosWithoutAlbum(albumId)
            }
            navigateToAlbums()
        } else {
            addAlbumUiState = addAlbumUiState.copy(isEntryValid = false)
        }
    }

    private fun validateInput(uiState: AlbumWithImages = addAlbumUiState.albumDetails): Boolean {
        return with(uiState) {
            album.name.isNotBlank() && album.description.isNotBlank()
        }
    }
}

data class AlbumUiState(
    val albumDetails: AlbumWithImages = AlbumWithImages(),
    val isEntryValid: Boolean = true
)