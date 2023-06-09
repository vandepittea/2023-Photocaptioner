package com.example.photocaptioner.ui.screens.album

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photocaptioner.data.database.AlbumsRepository
import com.example.photocaptioner.model.AlbumWithImages
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
        addAlbumUiState = AlbumUiState(
            albumDetails = addAlbumUiState.albumDetails.copy(album = addAlbumUiState.albumDetails.album.copy(name = title)),
            isEntryValid = validateInput(addAlbumUiState.albumDetails)
        )
    }

    fun updateAlbumDescriptionUiState(description: String) {
        addAlbumUiState = AlbumUiState(
            albumDetails = addAlbumUiState.albumDetails.copy(album = addAlbumUiState.albumDetails.album.copy(description = description)),
            isEntryValid = validateInput(addAlbumUiState.albumDetails)
        )
    }

    suspend fun saveItem() {
        if (validateInput()) {
            run {
                val albumId = albumsRepository.insertAlbum(addAlbumUiState.albumDetails.album)
                albumsRepository.updatePhotosWithoutAlbum(albumId)
            }
        }
    }

    suspend fun deletePhotosWithoutAlbum() {
        run {
            albumsRepository.deletePhotosWithoutAlbum()
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
    val isEntryValid: Boolean = false
)