package com.example.photocaptioner.ui.screens

import androidx.lifecycle.ViewModel
import com.example.photocaptioner.ui.screens.navigation.MenuItemType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PhotoCaptionersViewModel : ViewModel() {

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