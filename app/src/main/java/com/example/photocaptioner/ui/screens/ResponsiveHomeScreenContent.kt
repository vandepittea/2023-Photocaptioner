package com.example.photocaptioner.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.photocaptioner.ui.screens.album.AlbumDetailScreen
import com.example.photocaptioner.ui.screens.album.AlbumsScreen
import com.example.photocaptioner.ui.screens.album.EditAlbumScreen
import com.example.photocaptioner.ui.screens.pictures.ChoosePicturesSourceScreen
import com.example.photocaptioner.ui.screens.pictures.EditPhotoScreen

@Composable
fun AlbumsAndAlbumDetailScreen(
    onAddClick: () -> Unit,
    onAlbumClick: (Long) -> Unit,
    onEditClick: (Long) -> Unit,
    onAddPictureClick: (Long) -> Unit,
    onPhotoClick: (albumId: Long, photoId: Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        AlbumsScreen(
            onAddClick = onAddClick,
            onAlbumClick = onAlbumClick,
            modifier = Modifier.weight(1f)
        )
        AlbumDetailScreen(
            onEditClick = onEditClick,
            onAddClick = onAddPictureClick,
            onPhotoClick = onPhotoClick,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun AlbumDetailAndAlbumEditScreen(
    onEditClick: (Long) -> Unit,
    onAddPictureClick: (Long) -> Unit,
    onPhotoClick: (albumId: Long, photoId: Long) -> Unit,
    navigateBack: (route: String, include: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        AlbumDetailScreen(
            onEditClick = onEditClick,
            onAddClick = onAddPictureClick,
            onPhotoClick = onPhotoClick,
            modifier = Modifier.weight(1f)
        )
        EditAlbumScreen(
            navigateBack = navigateBack,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun AlbumDetailAndPhotoEditScreen(
    onEditClick: (Long) -> Unit,
    onAddPictureClick: (Long) -> Unit,
    onPhotoClick: (albumId: Long, photoId: Long) -> Unit,
    navigateBack: (route: String, include: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        AlbumDetailScreen(
            onEditClick = onEditClick,
            onAddClick = onAddPictureClick,
            onPhotoClick = onPhotoClick,
            modifier = Modifier.weight(1f)
        )
        EditPhotoScreen(
            navigateBack = navigateBack,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun AlbumDetailAndPhotoSourceChooserScreen(
    onEditClick: (Long) -> Unit,
    onAddPictureClick: (Long) -> Unit,
    onPhotoClick: (albumId: Long, photoId: Long) -> Unit,
    onChooseCamera: (Long) -> Unit,
    onChooseMaps: (Long) -> Unit,
    navigateBack: (route: String, include: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        AlbumDetailScreen(
            onEditClick = onEditClick,
            onAddClick = onAddPictureClick,
            onPhotoClick = onPhotoClick,
            modifier = Modifier.weight(1f)
        )
        ChoosePicturesSourceScreen(
            onChooseCamera = onChooseCamera,
            onChooseMaps = onChooseMaps,
            navigateBack = navigateBack,
            modifier = Modifier.weight(1f)
        )
    }
}