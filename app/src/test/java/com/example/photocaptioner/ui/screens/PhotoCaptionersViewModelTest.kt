package com.example.photocaptioner.ui.screens

import com.example.photocaptioner.data.MenuItemType
import org.junit.Assert
import org.junit.Test

class PhotoCaptionersViewModelTest {
    private val viewModel = PhotoCaptionersViewModel()

    @Test
    fun gameViewModel_Initialization() {
        val gameUiState = viewModel.uiState.value

        Assert.assertTrue(gameUiState.currentMenuItem == MenuItemType.Home)
        Assert.assertFalse(gameUiState.canNavigateBack)
    }

    @Test
    fun gameViewModel_UpdateCurrentMenuItem() {
        viewModel.updateCurrentMenuItem(MenuItemType.Albums)
        val gameUiState = viewModel.uiState.value

        Assert.assertTrue(gameUiState.currentMenuItem == MenuItemType.Albums)
        Assert.assertFalse(gameUiState.canNavigateBack)
    }

    @Test
    fun gameViewModel_CanNavigateBack() {
        viewModel.canNavigateBack(true)
        val gameUiState = viewModel.uiState.value

        Assert.assertTrue(gameUiState.currentMenuItem == MenuItemType.Home)
        Assert.assertTrue(gameUiState.canNavigateBack)
    }
}