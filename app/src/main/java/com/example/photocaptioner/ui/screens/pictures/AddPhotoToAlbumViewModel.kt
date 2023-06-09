package com.example.photocaptioner.ui.screens.pictures

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photocaptioner.data.database.AlbumsRepository
import com.example.photocaptioner.model.AlbumWithImages
import com.example.photocaptioner.model.Photo
import com.example.photocaptioner.ui.screens.album.AlbumDetailDestination
import com.example.photocaptioner.ui.screens.album.AlbumUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AddPhotoToAlbumViewModel(
    savedStateHandle: SavedStateHandle,
    private val albumsRepository: AlbumsRepository
) : ViewModel() {
    private val photoId: Long = checkNotNull(savedStateHandle[AddPhotoToAlbumDestination.photoIdArg])
    var addPhotoToAlbumUiState by mutableStateOf(AddPhotoToAlbumUiState())
        private set

    init {
        viewModelScope.launch {
            val photo: Photo = albumsRepository.getPhoto(photoId).first()
            val availableAlbums: List<AlbumWithImages> = albumsRepository.getAlbums().first()
            addPhotoToAlbumUiState = AddPhotoToAlbumUiState(
                photo = photo,
                availableAlbums = availableAlbums
            )
        }
    }

    suspend fun addPhotoToAlbum(albumId: Long) {
        addPhotoToAlbumUiState.photo.copy(albumId = albumId)
        albumsRepository.updatePhoto(addPhotoToAlbumUiState.photo)
    }
}

data class AddPhotoToAlbumUiState(
    val photo: Photo = Photo(),
    val availableAlbums: List<AlbumWithImages> = emptyList(),
)