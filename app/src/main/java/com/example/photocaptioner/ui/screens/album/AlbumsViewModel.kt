package com.example.photocaptioner.ui.screens.album

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photocaptioner.data.database.AlbumsRepository
import com.example.photocaptioner.model.AlbumWithImages
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class AlbumsViewModel(
    albumsRepository: AlbumsRepository
) : ViewModel() {
    val albumsUiState: StateFlow<AlbumsUiState> =
        albumsRepository.getAlbums()
            .map { AlbumsUiState(it) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), AlbumsUiState())
}

data class AlbumsUiState (
    val albums: List<AlbumWithImages> = emptyList()
)