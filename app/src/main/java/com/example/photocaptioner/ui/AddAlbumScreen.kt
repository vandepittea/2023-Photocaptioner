package com.example.photocaptioner.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
    newTitle: String,
    newDescription: String,
    onAlbumTitleChange: (String) -> Unit,
    onAlbumDescriptionChange: (String) -> Unit,
    onChooseCamera: () -> Unit,
    onChooseGallery: () -> Unit,
    onChooseMaps: () -> Unit,
    onAddNewAlbum: () -> Unit,
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
                title = newTitle,
                description = newDescription,
                onAlbumTitleChange = onAlbumTitleChange,
                onAlbumDescriptionChange = onAlbumDescriptionChange
            )
            Spacer(modifier = Modifier.height(16.dp))
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
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(MaterialTheme.colors.background)
        ) {
            NewAlbumFooter(onAddNewAlbum = onAddNewAlbum)
        }
    }
}

@Composable
fun TextFields(
    title: String,
    description: String,
    onAlbumTitleChange: (String) -> Unit,
    onAlbumDescriptionChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = { onAlbumTitleChange(it) },
            label = {
                Text(
                    text = stringResource(id = R.string.album_title),
                    style = MaterialTheme.typography.body1
                )
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = description,
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
            painter = painterResource(id = R.drawable.baseline_camera_alt_24),
            contentDescription = R.string.camera_icon,
            text = R.string.take_picture,
            onClick = onChooseCamera
        )
        Spacer(modifier = Modifier.height(8.dp))
        ButtonWithIcon(
            painter = painterResource(id = R.drawable.baseline_camera_alt_24),
            contentDescription = R.string.camera_icon,
            text = R.string.gallery,
            onClick = onChooseGallery
        )
        Spacer(modifier = Modifier.height(8.dp))
        ButtonWithIcon(
            painter = painterResource(id = R.drawable.baseline_camera_alt_24),
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

@Composable
fun NewAlbumFooter(
    onAddNewAlbum: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            text = R.string.add_new_album,
            onClick = onAddNewAlbum
        )
    }
}

@Preview
@Composable
fun AddAlbumsScreenPreviewWithoutPhotos() {
    PhotoCaptionerTheme {
        AddAlbumsScreen(emptyList(), "", "", {}, {}, {}, {}, {}, {})
    }
}

@Preview
@Composable
fun AddAlbumsScreenPreviewWithPhotos() {
    PhotoCaptionerTheme {
        AddAlbumsScreen(Datasource.defaultAlbum.photos, "France", "My first trip to France", {}, {}, {}, {}, {}, {})
    }
}