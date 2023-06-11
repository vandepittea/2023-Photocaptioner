package com.example.photocaptioner.ui.screens.pictures

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.photocaptioner.R
import com.example.photocaptioner.ui.screens.AlbumSelectBox
import com.example.photocaptioner.ui.AppViewModelProvider
import com.example.photocaptioner.ui.screens.Button
import com.example.photocaptioner.ui.screens.home.HomeDestination
import com.example.photocaptioner.ui.screens.ImageWithDescriptionAndDate
import com.example.photocaptioner.ui.screens.navigation.NavigationDestination
import com.example.photocaptioner.ui.theme.PhotoCaptionerTheme
import kotlinx.coroutines.launch

object AddPhotoToAlbumDestination : NavigationDestination {
    override val route = "add_photo_to_album"
    override val titleRes = R.string.add_photo_to_album
    const val photoIdArg = "photoId"
    override val routeWithArgs = "$route/{$photoIdArg}/{title}"
}

@Composable
fun AddPhotoToAlbumScreen(
    navigateBack: (route: String, include: Boolean) -> Unit,
    onNewAlbum: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddPhotoToAlbumViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(16.dp)
    ) {
        Column {
            ImageWithDescriptionAndDate(
                photo = viewModel.addPhotoToAlbumUiState.photo,
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Choose album",
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier
                    .padding(top = 16.dp)
            )

            AlbumSelectBox(
                albums = viewModel.addPhotoToAlbumUiState.availableAlbums,
                onAlbumSelected = {
                    viewModel.selectAlbum(it)
                }
            )
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(MaterialTheme.colors.background)
        ) {
            AddPhotoToAlbumFooter(
                albumsAvailable = viewModel.addPhotoToAlbumUiState.availableAlbums.isNotEmpty(),
                onAddPhoto = {
                    coroutineScope.launch {
                        viewModel.addPhotoToAlbum()
                        navigateBack(HomeDestination.routeWithArgs, false)
                    }
                },
                onNewAlbum = onNewAlbum
            )
        }
    }
}

@Composable
fun AddPhotoToAlbumFooter(
    albumsAvailable: Boolean,
    onAddPhoto: () -> Unit,
    onNewAlbum: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        text = R.string.new_album,
        onClick = onNewAlbum,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
    if (albumsAvailable) {
        Button(
            text = R.string.add_photo_to_album,
            onClick = onAddPhoto,
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

@Preview
@Composable
fun AddPhotoToAlbumScreenPreview() {
    PhotoCaptionerTheme {
        AddPhotoToAlbumScreen({ _, _ ->}, {})
    }
}