package com.example.photocaptioner.ui.screens.album

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photocaptioner.data.database.AlbumsRepository
import com.example.photocaptioner.model.AlbumWithImages
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlbumInformationViewModel(
    savedStateHandle: SavedStateHandle,
    private val albumsRepository: AlbumsRepository
) : ViewModel() {
    private var albumId: Long = savedStateHandle[EditAlbumDestination.albumIdArg] ?: -1L

    var albumUiState by mutableStateOf(AlbumUiState())

    init {
        if (albumId == -1L) {
            viewModelScope.launch {
                albumsRepository.getPhotosWithoutAlbum().collect {
                    albumUiState = AlbumUiState(
                        albumDetails = AlbumWithImages(
                            album = albumUiState.albumDetails.album,
                            photos = it
                        )
                    )
                }
            }
        } else {
            viewModelScope.launch {
                albumUiState = AlbumUiState(
                    albumsRepository.getAlbum(albumId)
                        .filterNotNull()
                        .first()
                )
            }

        }
    }

    fun updateAlbumTitleUiState(title: String) {
        albumUiState = albumUiState.copy(
            albumDetails = albumUiState.albumDetails.copy(album = albumUiState.albumDetails.album.copy(name = title)),
            isNameEntryValid = validateNameInput(albumUiState.albumDetails))
    }

    fun updateAlbumDescriptionUiState(description: String) {
        albumUiState = albumUiState.copy(
            albumDetails = albumUiState.albumDetails.copy(album = albumUiState.albumDetails.album.copy(description = description)),
            isDescriptionEntryValid = validateDescriptionInput(albumUiState.albumDetails))
    }

    suspend fun saveItem(navigateToAlbums: () -> Unit) {
        if (validateNameInput() && validateDescriptionInput()) {
            withContext(Dispatchers.IO) {
                if (albumId == -1L) {
                    albumId = albumsRepository.insertAlbum(albumUiState.albumDetails.album)
                }
                albumsRepository.updatePhotosWithoutAlbum(albumId)
            }
            navigateToAlbums()
        } else {
            albumUiState = albumUiState.copy(
                isNameEntryValid = validateNameInput(),
                isDescriptionEntryValid = validateDescriptionInput()
            )
        }
    }

    private fun validateNameInput(uiState: AlbumWithImages = albumUiState.albumDetails): Boolean {
        return with(uiState) {
            album.name.isNotBlank()
        }
    }

    private fun validateDescriptionInput(uiState: AlbumWithImages = albumUiState.albumDetails): Boolean {
        return with(uiState) {
           album.description.isNotBlank()
        }
    }
}

data class AlbumUiState(
    val albumDetails: AlbumWithImages = AlbumWithImages(),
    val isNameEntryValid: Boolean = true,
    val isDescriptionEntryValid: Boolean = true
)