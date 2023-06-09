package com.example.photocaptioner.ui.screens.pictures

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.photocaptioner.R
import com.example.photocaptioner.model.AlbumWithImages
import com.example.photocaptioner.ui.AlbumSelectBox
import com.example.photocaptioner.ui.AppViewModelProvider
import com.example.photocaptioner.ui.screens.navigation.NavigationDestination
import com.example.photocaptioner.ui.theme.PhotoCaptionerTheme
import kotlinx.coroutines.launch

object AddPhotoToAlbumDestination : NavigationDestination {
    override val route = "add_photo_to_album"
    override val titleRes = R.string.add_photo_to_album
    const val photoIdArg = "photoId"
    val routeWithArgs = "$route/{$photoIdArg}"
}

@Composable
fun AddPhotoToAlbumScreen(
    modifier: Modifier = Modifier,
    viewModel: AddPhotoToAlbumViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val addPhotoToAlbumUiState by viewModel.addPhotoToAlbumUiState.collectAsState()
    Box(
        modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(16.dp)
    ) {
        AlbumSelectBox(
            albums = addPhotoToAlbumUiState.availableAlbums,
            onAlbumSelected = {
                coroutineScope.launch {
                    viewModel.addPhotoToAlbum(it)
                }
            }
        )
    }
}



@Preview
@Composable
fun AddPhotoToAlbumScreenPreview() {
    PhotoCaptionerTheme() {
        AddPhotoToAlbumScreen()
    }
}