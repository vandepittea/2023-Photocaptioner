package com.example.photocaptioner.ui.screens.pictures

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photocaptioner.data.database.AlbumsRepository
import com.example.photocaptioner.model.Album
import com.example.photocaptioner.model.AlbumWithImages
import com.example.photocaptioner.model.Photo
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AddPhotoToAlbumViewModel(
    savedStateHandle: SavedStateHandle,
    private val albumsRepository: AlbumsRepository
) : ViewModel() {
    private val photoId: Long = checkNotNull(savedStateHandle[AddPhotoToAlbumDestination.photoIdArg])
    var addPhotoToAlbumUiState by mutableStateOf(AddPhotoToAlbumUiState())

    init {
        viewModelScope.launch {
            addPhotoToAlbumUiState = AddPhotoToAlbumUiState(
                photo = albumsRepository.getPhoto(photoId).first(),
                availableAlbums = albumsRepository.getAlbums().first()
            )
        }
    }

    fun selectAlbum(album: AlbumWithImages) {
        addPhotoToAlbumUiState = addPhotoToAlbumUiState.copy(selectedAlbum = album)
    }

    suspend fun addPhotoToAlbum() {
        addPhotoToAlbumUiState = addPhotoToAlbumUiState.copy(
            photo = addPhotoToAlbumUiState.photo.copy(albumId = addPhotoToAlbumUiState.selectedAlbum.album.id)
        )
        albumsRepository.updatePhoto(addPhotoToAlbumUiState.photo)
    }
}

data class AddPhotoToAlbumUiState(
    val photo: Photo = Photo(),
    val availableAlbums: List<AlbumWithImages> = emptyList(),
    val selectedAlbum: AlbumWithImages = AlbumWithImages(album = Album(name = "Choose an album"))
)