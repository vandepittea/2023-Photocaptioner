package com.example.photocaptioner.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.photocaptioner.R
import com.example.photocaptioner.data.Datasource
import com.example.photocaptioner.model.Photo
import com.example.photocaptioner.ui.theme.PhotoCaptionerTheme

@Composable
fun AddAlbumsScreen(
    newPhotos: List<Photo>,
    onAlbumTitleChange: (String) -> Unit,
    onAlbumDescriptionChange: (String) -> Unit,
    onChooseCamera: () -> Unit,
    onChooseGallery: () -> Unit,
    onChooseMaps: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(16.dp)
    ) {
        Column {
            TopBar(title = R.string.add_new_album)
            TextFields(
                onAlbumTitleChange = onAlbumTitleChange,
                onAlbumDescriptionChange = onAlbumDescriptionChange
            )
            if (newPhotos.isEmpty()) {
                ImageOptions(
                    onChooseCamera = onChooseCamera,
                    onChooseGallery = onChooseGallery,
                    onChooseMaps = onChooseMaps
                )
            } else {
                AlbumImages(
                    imagesList = newPhotos
                )
            }
        }
    }
}

@Composable
fun TextFields(
    onAlbumTitleChange: (String) -> Unit,
    onAlbumDescriptionChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        OutlinedTextField(
            value = "",
            onValueChange = { onAlbumTitleChange(it) },
            label = {
                Text(
                    text = stringResource(id = R.string.album_title),
                    style = MaterialTheme.typography.body1
                )
            }
        )
        OutlinedTextField(
            value = "",
            onValueChange = { onAlbumDescriptionChange(it) },
            label = {
                Text(
                    text = stringResource(id = R.string.album_description),
                    style = MaterialTheme.typography.body1
                )
            }
        )
    }
}

@Composable
fun ImageOptions(
    onChooseCamera: () -> Unit,
    onChooseGallery: () -> Unit,
    onChooseMaps: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.choose_picture_source),
            style = MaterialTheme.typography.subtitle2,
        )
        Spacer(modifier = Modifier.height(8.dp))
        ButtonWithIcon(
            painter = painterResource(id = R.drawable.camera),
            contentDescription = R.string.camera_icon,
            text = R.string.take_picture,
            onClick = onChooseCamera
        )
        Spacer(modifier = Modifier.height(8.dp))
        ButtonWithIcon(
            painter = painterResource(id = R.drawable.camera),
            contentDescription = R.string.camera_icon,
            text = R.string.gallery,
            onClick = onChooseGallery
        )
        Spacer(modifier = Modifier.height(8.dp))
        ButtonWithIcon(
            painter = painterResource(id = R.drawable.camera),
            contentDescription = R.string.camera_icon,
            text = R.string.maps,
            onClick = onChooseMaps
        )
    }
}

@Composable
fun AlbumImages(
    imagesList: List<Photo>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        items(imagesList) { image ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = image.image),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Preview
@Composable
fun AddAlbumsScreenPreviewWithoutPhotos() {
    PhotoCaptionerTheme {
        AddAlbumsScreen(emptyList(), {}, {}, {}, {}, {})
    }
}

@Preview
@Composable
fun AddAlbumsScreenPreviewWithPhotos() {
    PhotoCaptionerTheme {
        AddAlbumsScreen(Datasource.defaultAlbum.photos, {}, {}, {}, {}, {})
    }
}