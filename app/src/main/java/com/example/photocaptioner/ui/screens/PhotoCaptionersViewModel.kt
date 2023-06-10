package com.example.photocaptioner.ui.screens

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.photocaptioner.PhotoCaptionerApplication
import com.example.photocaptioner.data.database.AlbumsRepository
import com.example.photocaptioner.data.Datasource
import com.example.photocaptioner.data.MenuItemType
import com.example.photocaptioner.data.UnsplashRepository
import com.example.photocaptioner.model.Album
import com.example.photocaptioner.model.MapsPhoto
import com.example.photocaptioner.model.Photo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PhotoCaptionersViewModel() : ViewModel() {

    private val _uiState = MutableStateFlow(
        PhotoCaptionerUiState()
    )
    val uiState: StateFlow<PhotoCaptionerUiState> = _uiState.asStateFlow()

    fun updateCurrentMenuItem(menuItemType: MenuItemType) {
        _uiState.update {
            it.copy(
                currentMenuItem = menuItemType
            )
        }
    }

    fun canNavigateBack(canNavigateBack: Boolean) {
        _uiState.update {
            it.copy(
                canNavigateBack = canNavigateBack
            )
        }
    }

    fun updateTopBarTitle(title: String) {
        _uiState.update {
            it.copy(
                topBarTitle = title
            )
        }
    }

    fun updateBottomBarVisibility(visible: Boolean) {
        _uiState.update {
            it.copy(
                bottomBarVisible = visible
            )
        }
    }
}

data class PhotoCaptionerUiState(
    val currentMenuItem: MenuItemType = MenuItemType.Home,
    val canNavigateBack: Boolean = false,
    val topBarTitle: String = "",
    val bottomBarVisible: Boolean = false
)