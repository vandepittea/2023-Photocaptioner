package com.example.photocaptioner.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photocaptioner.data.database.AlbumsRepository
import com.example.photocaptioner.model.AlbumWithImages
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(
    albumsRepository: AlbumsRepository
) : ViewModel() {
       val homeUiState: StateFlow<HomeUiState> =
           albumsRepository.getLatestAlbum()
               .map {HomeUiState(it)
               }
               .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), HomeUiState())
}

data class HomeUiState(
    val recentlyEditedAlbum: AlbumWithImages? = null,
)