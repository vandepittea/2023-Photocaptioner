package com.example.photocaptioner.ui.screens

import com.example.photocaptioner.data.MenuItemType
import org.junit.Assert
import org.junit.Test

class PhotoCaptionersViewModelTest {
    private val viewModel = PhotoCaptionersViewModel()

    @Test
    fun photoCaptionersViewModel_UpdateCurrentMenuItem() {
        viewModel.updateCurrentMenuItem(MenuItemType.Albums)
        val gameUiState = viewModel.uiState.value

        Assert.assertTrue(gameUiState.currentMenuItem == MenuItemType.Albums)
        Assert.assertFalse(gameUiState.canNavigateBack)
    }

    @Test
    fun photoCaptionersViewModel_CanNavigateBack() {
        viewModel.canNavigateBack(true)
        val gameUiState = viewModel.uiState.value

        Assert.assertTrue(gameUiState.currentMenuItem == MenuItemType.Home)
        Assert.assertTrue(gameUiState.canNavigateBack)
    }

    @Test
    fun photoCaptionersViewModel_UpdateTopBarTitle() {
        viewModel.updateTopBarTitle("Test Title")
        val gameUiState = viewModel.uiState.value

        Assert.assertTrue(gameUiState.currentMenuItem == MenuItemType.Home)
        Assert.assertFalse(gameUiState.canNavigateBack)
        Assert.assertTrue(gameUiState.topBarTitle == "Test Title")
    }

    @Test
    fun photoCaptionersViewModel_UpdateBottomBarVisibility() {
        viewModel.updateBottomBarVisibility(true)
        val gameUiState = viewModel.uiState.value

        Assert.assertTrue(gameUiState.currentMenuItem == MenuItemType.Home)
        Assert.assertFalse(gameUiState.canNavigateBack)
        Assert.assertTrue(gameUiState.bottomBarVisible)
    }
}