package com.example.photocaptioner.ui.screens.pictures

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photocaptioner.data.database.AlbumsRepository
import com.example.photocaptioner.model.AlbumWithImages
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class AddPhotoToAlbumViewModel(
    private val albumsRepository: AlbumsRepository
) : ViewModel() {
    val addPhotoToAlbumUiState: StateFlow<AddPhotoToAlbumUiState> =
        albumsRepository.getAlbums()
            .map { AddPhotoToAlbumUiState(it) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), AddPhotoToAlbumUiState())
}

data class AddPhotoToAlbumUiState(
    val availableAlbums: List<AlbumWithImages> = emptyList(),
)