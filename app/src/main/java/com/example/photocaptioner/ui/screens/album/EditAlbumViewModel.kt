package com.example.photocaptioner.ui.screens.album

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photocaptioner.data.database.AlbumsRepository
import com.example.photocaptioner.model.AlbumWithImages
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EditAlbumViewModel(
    savedStateHandle: SavedStateHandle,
    private val albumsRepository: AlbumsRepository
) : ViewModel() {
    private val albumId: Long = checkNotNull(savedStateHandle[EditAlbumDestination.albumIdArg])

    var editAlbumUiState by mutableStateOf(AlbumUiState())

    init {
        viewModelScope.launch {
            editAlbumUiState = AlbumUiState(
                albumsRepository.getAlbum(albumId)
                    .filterNotNull()
                    .first()
            )
        }
    }

    fun updateAlbumTitleUiState(title: String) {
        editAlbumUiState = AlbumUiState(
            albumDetails = editAlbumUiState.albumDetails.copy(album = editAlbumUiState.albumDetails.album.copy(name = title)),
            isEntryValid = validateInput(editAlbumUiState.albumDetails)
        )
    }

    fun updateAlbumDescriptionUiState(description: String) {
        editAlbumUiState = AlbumUiState(
            albumDetails = editAlbumUiState.albumDetails.copy(album = editAlbumUiState.albumDetails.album.copy(description = description)),
            isEntryValid = validateInput(editAlbumUiState.albumDetails)
        )
    }

    suspend fun saveItem() {
        if (validateInput()) {
            albumsRepository.updateAlbum(editAlbumUiState.albumDetails.album)
        }
    }

    private fun validateInput(uiState: AlbumWithImages = editAlbumUiState.albumDetails): Boolean {
        return with(uiState) {
            album.name.isNotBlank() && album.description.isNotBlank()
        }
    }
}