package com.example.photocaptioner.ui.screens.album

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photocaptioner.data.database.AlbumsRepository
import com.example.photocaptioner.model.Album
import com.example.photocaptioner.model.AlbumWithImages
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class AlbumDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val albumsRepository: AlbumsRepository
) : ViewModel(){
    private val albumId: Long = checkNotNull(savedStateHandle[AlbumDetailDestination.albumIdArg])

    val albumDetailUiState: StateFlow<AlbumDetailUiState> =
        albumsRepository.getAlbum(albumId)
            .filterNotNull()
            .map {
                AlbumDetailUiState(albumDetails = it)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = AlbumDetailUiState()
            )

    fun downloadAlbum() {
        //TODO: download album
    }

    fun shareAlbum() {
        //TODO: share album
    }
}

data class AlbumDetailUiState(
    val albumDetails: AlbumWithImages = AlbumWithImages()
)