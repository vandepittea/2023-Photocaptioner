package com.example.photocaptioner.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.photocaptioner.model.Album
import com.example.photocaptioner.model.AlbumWithImages
import com.example.photocaptioner.model.Photo
import com.example.photocaptioner.ui.screens.album.AlbumDetailScreen
import com.example.photocaptioner.ui.screens.album.AlbumsScreen
import com.example.photocaptioner.ui.screens.album.EditAlbumScreen
import com.example.photocaptioner.ui.screens.album.EditPhotoScreen

@Composable
fun AlbumsAndAlbumDetailScreen(
    onAddClick: () -> Unit,
    onAlbumClick: (AlbumWithImages) -> Unit,
    onEditClick: () -> Unit,
    onAddPictureClick: () -> Unit,
    onPhotoClick: (Photo) -> Unit,
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
    onEditClick: () -> Unit,
    onAddPictureClick: () -> Unit,
    onPhotoClick: (Photo) -> Unit,
    navigateBack: () -> Unit,
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
    onEditClick: () -> Unit,
    onAddPictureClick: () -> Unit,
    onPhotoClick: (Photo) -> Unit,
    onPhotoSave: () -> Unit,
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
            navigateBack = onPhotoSave,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun AlbumDetailAndPhotoSourceChooserScreen(
    onEditClick: () -> Unit,
    onAddPictureClick: () -> Unit,
    onPhotoClick: (Photo) -> Unit,
    onChooseCamera: () -> Unit,
    onChooseGallery: () -> Unit,
    onChooseMaps: () -> Unit,
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
            onChooseGallery = onChooseGallery,
            onChooseMaps = onChooseMaps,
            modifier = Modifier.weight(1f)
        )
    }
}