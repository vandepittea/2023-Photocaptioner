package com.example.photocaptioner.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.photocaptioner.model.Album
import com.example.photocaptioner.model.Photo

@Composable
fun AlbumDetailResponsiveScreenChooser(
    detailedAlbum: Album,
    onDownloadClick: () -> Unit,
    onEditClick: () -> Unit,
    onAddPictureClick: () -> Unit,
    onShareClick: () -> Unit,
    onPhotoClick: (Photo) -> Unit,
    onAlbumTitleChange: (String) -> Unit,
    onAlbumDescriptionChange: (String) -> Unit,
    onAlbumSave: () -> Unit,
    isEditingAlbum: Boolean,
    modifier: Modifier = Modifier
) {
    if (isEditingAlbum) {
        AlbumDetailAndAlbumEditScreen(
            detailedAlbum = detailedAlbum,
            onDownloadClick = onDownloadClick,
            onEditClick = onEditClick,
            onAddPictureClick = onAddPictureClick,
            onShareClick = onShareClick,
            onPhotoClick = onPhotoClick,
            onAlbumTitleChange = onAlbumTitleChange,
            onAlbumDescriptionChange = onAlbumDescriptionChange,
            onAlbumSave = onAlbumSave
        )
    } else {
        AlbumDetailScreen(
            album = detailedAlbum,
            onDownloadClick = onDownloadClick,
            onEditClick = onEditClick,
            onAddClick = onAddPictureClick,
            onShareClick = onShareClick,
            onPhotoClick = onPhotoClick,
            modifier = modifier
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
            onPhotoClick = onPhotoClick
        )
        EditAlbumScreen(
            albumToEdit = detailedAlbum,
            onAlbumTitleChange = onAlbumTitleChange,
            onAlbumDescriptionChange = onAlbumDescriptionChange,
            onSave = onAlbumSave
        )
    }
}

@Composable
fun AlbumDetailAndPhotoEditScreen(

) {

}

@Composable
fun AlbumDetailAndPhotoSourceChooserScreen(

) {

}

@Composable
fun AlbumDetailAndAddPhotoScreen(

) {

}