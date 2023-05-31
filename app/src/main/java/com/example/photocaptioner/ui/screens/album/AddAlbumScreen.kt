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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.photocaptioner.R
import com.example.photocaptioner.data.Datasource
import com.example.photocaptioner.model.Photo
import com.example.photocaptioner.ui.screens.album.AddAlbumViewModel
import com.example.photocaptioner.ui.theme.PhotoCaptionerTheme
import com.example.photocaptioner.ui.utils.PhotoCaptionerContentType
import kotlinx.coroutines.launch

@Composable
fun AddAlbumsScreen(
    newPhotos: List<Photo>,
    onChooseCamera: () -> Unit,
    onChooseGallery: () -> Unit,
    onChooseMaps: () -> Unit,
    navigateBack: () -> Unit,
    contentType: PhotoCaptionerContentType,
    modifier: Modifier = Modifier,
    viewModel: AddAlbumViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(16.dp)
    ) {
        Log.d("ContentType", contentType.toString())
        if (contentType == PhotoCaptionerContentType.LIST_ONLY) {
            AddAlbumInnerScreenListOnly(
                newPhotos = viewModel.allAlbumUiState.albumDetails.photos,
                onChooseCamera = onChooseCamera,
                onChooseGallery = onChooseGallery,
                onChooseMaps = onChooseMaps,
                viewModel = viewModel,
            )
        } else {
            AddAlbumInnerScreenListAndDetails(
                newPhotos = viewModel.allAlbumUiState.albumDetails.photos,
                onChooseCamera = onChooseCamera,
                onChooseGallery = onChooseGallery,
                onChooseMaps = onChooseMaps,
                viewModel = viewModel,
            )
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(MaterialTheme.colors.background)
        ) {
            NewAlbumFooter(onAddNewAlbum = {
                coroutineScope.launch {
                    viewModel.saveItem()
                    navigateBack()
                }
            })
        }
    }
}

@Composable
fun AddAlbumInnerScreenListOnly(
    newPhotos: List<Photo>,
    onChooseCamera: () -> Unit,
    onChooseGallery: () -> Unit,
    onChooseMaps: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddAlbumViewModel
) {
    Column(
        modifier = modifier
    ) {
        AddAlbumInformation(
            viewModel = viewModel
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
    onChooseCamera: () -> Unit,
    onChooseGallery: () -> Unit,
    onChooseMaps: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddAlbumViewModel
) {
    Row(
        modifier = modifier
    ) {
        AddAlbumInformation(
            modifier = Modifier.weight(1f),
            viewModel = viewModel
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
    modifier: Modifier = Modifier,
    viewModel: AddAlbumViewModel
) {
    Column(
        modifier = modifier
    ) {
        TopBar(title = R.string.add_new_album)
        AlbumTextFields(
            title = viewModel.allAlbumUiState.albumDetails.album.name,
            description = viewModel.allAlbumUiState.albumDetails.album.description,
            onAlbumTitleChange = { viewModel.updateAlbumTitleUiState(it) },
            onAlbumDescriptionChange = { viewModel.updateAlbumDescriptionUiState(it) }
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
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(image.filePath)
                        .build(),
                    contentDescription = image.description,
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
            {},
            {},
            {},
            {},
            PhotoCaptionerContentType.LIST_ONLY
        )
    }
}

@Preview
@Composable
fun AddAlbumsScreenPreviewWithPhotos() {
    PhotoCaptionerTheme {
        AddAlbumsScreen(
            Datasource.defaultAlbum.photos,
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
            {},
            {},
            {},
            {},
            PhotoCaptionerContentType.LIST_AND_DETAIL
        )
    }
}