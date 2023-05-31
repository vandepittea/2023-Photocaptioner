package com.example.photocaptioner.ui.screens.album

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.photocaptioner.ui.theme.PhotoCaptionerTheme
import com.example.photocaptioner.R
import com.example.photocaptioner.model.AlbumWithImages
import com.example.photocaptioner.ui.AppViewModelProvider
import com.example.photocaptioner.ui.ButtonIcon
import com.example.photocaptioner.ui.ImageWithDescription

@Composable
fun AlbumsScreen(
    onAddClick: () -> Unit,
    onAlbumClick: (AlbumWithImages) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AlbumsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val albumsUiState by viewModel.albumsUiState.collectAsState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        TopBar(
            onAddClick = onAddClick
        )

        AlbumList(
            albumList = albumsUiState.albums,
            onAlbumClick = onAlbumClick
        )
    }
}


@Composable
fun TopBar(
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.my_albums),
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier
                .padding(end = 8.dp)
        )

        ButtonIcon(
            onClick = onAddClick,
            icon = Icons.Default.Add,
            description = R.string.add_album
        )
    }
}

@Composable
fun AlbumList(
    albumList: List<AlbumWithImages>,
    onAlbumClick: (AlbumWithImages) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(400.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(albumList) { albumWithImages ->
            ImageWithDescription(
                image = albumWithImages.photos[0],
                description = albumWithImages.album.name,
                onClick = { onAlbumClick(albumWithImages) }
            )
        }
    }
}

@Preview
@Composable
fun AlbumsScreenPreview(){
    PhotoCaptionerTheme {
        AlbumsScreen({}, {})
    }
}

@Preview(widthDp = 1000)
@Composable
fun AlbumsScreenPreviewWithExtendedScreen(){
    PhotoCaptionerTheme {
        AlbumsScreen({}, {})
    }
}