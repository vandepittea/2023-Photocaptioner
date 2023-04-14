package com.example.photocaptioner.ui

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import com.example.photocaptioner.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PhotoCaptionersViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(PhotoCaptionerUiState())
    val uiState: StateFlow<PhotoCaptionerUiState> = _uiState.asStateFlow()

    fun navigateToScreen(@StringRes screen: Int, canNavigateBack: Boolean) {
        _uiState.update {
            it.copy(
                canNavigateBack = canNavigateBack,
                selectedScreen = screen
            )
        }
    }
}

data class PhotoCaptionerUiState(
    val canNavigateBack: Boolean = false,
    @StringRes val selectedScreen: Int = R.string.start
)