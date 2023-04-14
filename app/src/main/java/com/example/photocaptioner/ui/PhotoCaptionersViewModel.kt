package com.example.photocaptioner.ui

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import com.example.photocaptioner.R
import com.example.photocaptioner.data.Datasource
import com.example.photocaptioner.model.Album
import com.example.photocaptioner.model.Photo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PhotoCaptionersViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(PhotoCaptionerUiState(
        albumList = Datasource.getAlbums()
    ))
    val uiState: StateFlow<PhotoCaptionerUiState> = _uiState.asStateFlow()

    fun navigateToScreen(@StringRes newScreen: Int, canNavigateBack: Boolean) {
        _uiState.update {
            it.copy(
                canNavigateBack = canNavigateBack,
                currentScreen = newScreen
            )
        }
    }
}

data class PhotoCaptionerUiState(
    val canNavigateBack: Boolean = false,
    @StringRes val currentScreen: Int = R.string.start,
    val recentlyEdited: Photo = Datasource.defaultPhoto,
    val albumList: List<Album> = emptyList(),
)