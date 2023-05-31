package com.example.photocaptioner.ui

import android.util.Log
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
import com.example.photocaptioner.ui.utils.PhotoCaptionerContentType

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
    contentType: PhotoCaptionerContentType,
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(16.dp)
    ) {
        Log.d("ContentType", contentType.toString())
        if (contentType == PhotoCaptionerContentType.LIST_ONLY) {
            AddAlbumInnerScreenListOnly(
                newPhotos = newPhotos,
                newTitle = newTitle,
                newDescription = newDescription,
                onAlbumTitleChange = onAlbumTitleChange,
                onAlbumDescriptionChange = onAlbumDescriptionChange,
                onChooseCamera = onChooseCamera,
                onChooseGallery = onChooseGallery,
                onChooseMaps = onChooseMaps
            )
        } else {
            AddAlbumInnerScreenListAndDetails(
                newPhotos = newPhotos,
                newTitle = newTitle,
                newDescription = newDescription,
                onAlbumTitleChange = onAlbumTitleChange,
                onAlbumDescriptionChange = onAlbumDescriptionChange,
                onChooseCamera = onChooseCamera,
                onChooseGallery = onChooseGallery,
                onChooseMaps = onChooseMaps
            )
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
fun AddAlbumInnerScreenListOnly(
    newPhotos: List<Photo>,
    newTitle: String,
    newDescription: String,
    onAlbumTitleChange: (String) -> Unit,
    onAlbumDescriptionChange: (String) -> Unit,
    onChooseCamera: () -> Unit,
    onChooseGallery: () -> Unit,
    onChooseMaps: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        AddAlbumInformation(
            newTitle = newTitle,
            newDescription = newDescription,
            onAlbumTitleChange = onAlbumTitleChange,
            onAlbumDescriptionChange = onAlbumDescriptionChange
        )
        Spacer(modifier = Modifier.height(16.dp))
        AddAlbumPhoto(
            newPhotos = newPhotos,
            onChooseCamera = onChooseCamera,
            onChooseGallery = onChooseGallery,
            onChooseMaps = onChooseMaps
        )
    }
}

@Composable
fun AddAlbumInnerScreenListAndDetails(
    newPhotos: List<Photo>,
    newTitle: String,
    newDescription: String,
    onAlbumTitleChange: (String) -> Unit,
    onAlbumDescriptionChange: (String) -> Unit,
    onChooseCamera: () -> Unit,
    onChooseGallery: () -> Unit,
    onChooseMaps: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        AddAlbumInformation(
            newTitle = newTitle,
            newDescription = newDescription,
            onAlbumTitleChange = onAlbumTitleChange,
            onAlbumDescriptionChange = onAlbumDescriptionChange,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(16.dp))
        AddAlbumPhoto(
            newPhotos = newPhotos,
            onChooseCamera = onChooseCamera,
            onChooseGallery = onChooseGallery,
            onChooseMaps = onChooseMaps,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun AddAlbumInformation(
    newTitle: String,
    newDescription: String,
    onAlbumTitleChange: (String) -> Unit,
    onAlbumDescriptionChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        TopBar(title = R.string.add_new_album)
        AlbumTextFields(
            title = newTitle,
            description = newDescription,
            onAlbumTitleChange = onAlbumTitleChange,
            onAlbumDescriptionChange = onAlbumDescriptionChange
        )
    }
}

@Composable
fun AddAlbumPhoto(
    newPhotos: List<Photo>,
    onChooseCamera: () -> Unit,
    onChooseGallery: () -> Unit,
    onChooseMaps: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        if (newPhotos.isEmpty()) {
            ChoosePicturesSourceScreen(
                onChooseCamera = onChooseCamera,
                onChooseGallery = onChooseGallery,
                onChooseMaps = onChooseMaps
            )
        } else {
            Text(
                text = stringResource(id = R.string.added_pictures),
                style = MaterialTheme.typography.subtitle2,
            )
            Spacer(modifier = Modifier.height(8.dp))
            AlbumImages(
                imagesList = newPhotos
            )
        }
    }
}

@Composable
fun AlbumTextFields(
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
        AddAlbumsScreen(
            emptyList(),
            "",
            "",
            {},
            {},
            {},
            {},
            {},
            {},
            PhotoCaptionerContentType.LIST_ONLY)
    }
}

@Preview
@Composable
fun AddAlbumsScreenPreviewWithPhotos() {
    PhotoCaptionerTheme {
        AddAlbumsScreen(
            Datasource.defaultAlbum.photos,
            "France",
            "My first trip to France",
            {},
            {},
            {},
            {},
            {},
            {},
            PhotoCaptionerContentType.LIST_ONLY
        )
    }
}

@Preview(widthDp = 1000)
@Composable
fun AddAlbumsScreenPreviewWithoutPhotosWithExpandedView() {
    PhotoCaptionerTheme {
        AddAlbumsScreen(
            emptyList(),
            "",
            "",
            {},
            {},
            {},
            {},
            {},
            {},
            PhotoCaptionerContentType.LIST_AND_DETAIL)
    }
}

@Preview(widthDp = 1000)
@Composable
fun AddAlbumsScreenPreviewWithPhotosWithExpandedView() {
    PhotoCaptionerTheme {
        AddAlbumsScreen(
            Datasource.defaultAlbum.photos,
            "France",
            "My first trip to France",
            {},
            {},
            {},
            {},
            {},
            {},
            PhotoCaptionerContentType.LIST_AND_DETAIL
        )
    }
}