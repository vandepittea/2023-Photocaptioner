package com.example.photocaptioner.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.photocaptioner.model.Album
import com.example.photocaptioner.model.Photo
import com.example.photocaptioner.ui.screens.album.AlbumDetailScreen
import com.example.photocaptioner.ui.screens.album.AlbumsScreen
import com.example.photocaptioner.ui.screens.album.EditAlbumScreen
import com.example.photocaptioner.ui.screens.album.EditPhotoScreen

@Composable
fun AlbumsAndAlbumDetailScreen(
    albumList: List<Album>,
    onAddClick: () -> Unit,
    onAlbumClick: (Album) -> Unit,
    detailedAlbum: Album,
    onDownloadClick: () -> Unit,
    onEditClick: () -> Unit,
    onAddPictureClick: () -> Unit,
    onShareClick: () -> Unit,
    onPhotoClick: (Photo) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        AlbumsScreen(
            albumList = albumList,
            onAddClick = onAddClick,
            onAlbumClick = onAlbumClick,
            modifier = Modifier.weight(1f)
        )
        AlbumDetailScreen(
            album = detailedAlbum,
            onDownloadClick = onDownloadClick,
            onEditClick = onEditClick,
            onAddClick = onAddPictureClick,
            onShareClick = onShareClick,
            onPhotoClick = onPhotoClick,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun AlbumDetailAndAlbumEditScreen(
    detailedAlbum: Album,
    onDownloadClick: () -> Unit,
    onEditClick: () -> Unit,
    onAddPictureClick: () -> Unit,
    onShareClick: () -> Unit,
    onPhotoClick: (Photo) -> Unit,
    onAlbumTitleChange: (String) -> Unit,
    onAlbumDescriptionChange: (String) -> Unit,
    onAlbumSave: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        AlbumDetailScreen(
            album = detailedAlbum,
            onDownloadClick = onDownloadClick,
            onEditClick = onEditClick,
            onAddClick = onAddPictureClick,
            onShareClick = onShareClick,
            onPhotoClick = onPhotoClick,
            modifier = Modifier.weight(1f)
        )
        EditAlbumScreen(
            albumToEdit = detailedAlbum,
            onAlbumTitleChange = onAlbumTitleChange,
            onAlbumDescriptionChange = onAlbumDescriptionChange,
            onSave = onAlbumSave,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun AlbumDetailAndPhotoEditScreen(
    detailedAlbum: Album,
    onDownloadClick: () -> Unit,
    onEditClick: () -> Unit,
    onAddPictureClick: () -> Unit,
    onShareClick: () -> Unit,
    onPhotoClick: (Photo) -> Unit,
    photoToEdit: Photo,
    photoDescriptionToEdit: String,
    onPhotoDescriptionChange: (String) -> Unit,
    onPhotoSave: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        AlbumDetailScreen(
            album = detailedAlbum,
            onDownloadClick = onDownloadClick,
            onEditClick = onEditClick,
            onAddClick = onAddPictureClick,
            onShareClick = onShareClick,
            onPhotoClick = onPhotoClick,
            modifier = Modifier.weight(1f)
        )
        EditPhotoScreen(
            photoToEdit = photoToEdit,
            description = photoDescriptionToEdit,
            onPhotoDescriptionChange = onPhotoDescriptionChange,
            navigateBack = onPhotoSave,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun AlbumDetailAndPhotoSourceChooserScreen(
    detailedAlbum: Album,
    onDownloadClick: () -> Unit,
    onEditClick: () -> Unit,
    onAddPictureClick: () -> Unit,
    onShareClick: () -> Unit,
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
            album = detailedAlbum,
            onDownloadClick = onDownloadClick,
            onEditClick = onEditClick,
            onAddClick = onAddPictureClick,
            onShareClick = onShareClick,
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