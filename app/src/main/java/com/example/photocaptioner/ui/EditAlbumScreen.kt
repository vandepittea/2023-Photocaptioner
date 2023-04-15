package com.example.photocaptioner.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.photocaptioner.R
import com.example.photocaptioner.data.Datasource
import com.example.photocaptioner.model.Album
import com.example.photocaptioner.ui.theme.PhotoCaptionerTheme

@Composable
fun EditAlbumScreen(
    albumToEdit: Album,
    onAlbumTitleChange : (String) -> Unit,
    onAlbumDescriptionChange : (String) -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(16.dp)
    ) {
        Column {
            TopBar(title = R.string.edit_album)
            AlbumTextFields(
                title = stringResource(id = albumToEdit.name),
                description = stringResource(id = albumToEdit.description),
                onAlbumTitleChange = onAlbumTitleChange,
                onAlbumDescriptionChange = onAlbumDescriptionChange
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                text = R.string.save,
                onClick = onSave
            )
        }
    }
}

@Preview
@Composable
fun EditAlbumScreenPreview() {
    PhotoCaptionerTheme {
        EditAlbumScreen(Datasource.defaultAlbum, {}, {}, {})
    }
}