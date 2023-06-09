package com.example.photocaptioner.ui.screens.pictures

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.photocaptioner.ui.AppViewModelProvider
import com.example.photocaptioner.ui.theme.PhotoCaptionerTheme

@Composable
fun AddPhotoToAlbumScreen(
    modifier: Modifier = Modifier,
    viewModel: AddPhotoToAlbumViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {

}

@Preview
@Composable
fun AddPhotoToAlbumScreenPreview() {
    PhotoCaptionerTheme() {
        AddPhotoToAlbumScreen()
    }
}