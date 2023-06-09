package com.example.photocaptioner.ui.screens.album

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.photocaptioner.R
import com.example.photocaptioner.model.Photo
import com.example.photocaptioner.ui.TopBar
import com.example.photocaptioner.ui.AppViewModelProvider
import com.example.photocaptioner.ui.HomeDestination
import com.example.photocaptioner.ui.screens.navigation.NavigationDestination
import com.example.photocaptioner.ui.screens.pictures.ChoosePicturesSourceScreen
import com.example.photocaptioner.ui.theme.PhotoCaptionerTheme
import com.example.photocaptioner.ui.utils.PhotoCaptionerContentType
import kotlinx.coroutines.launch

object AddAlbumDestination : NavigationDestination {
    override val route = "add_albums"
    override val titleRes = R.string.add_album
    const val albumIdArg = "albumId"
    val routeWithArgs = "$route/{$albumIdArg}"
}

@Composable
fun AddAlbumScreen(
    onChooseCamera: (Long) -> Unit,
    onChooseMaps: (Long) -> Unit,
    navigateBack: (route: String, include: Boolean) -> Unit,
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
        if (contentType == PhotoCaptionerContentType.LIST_ONLY) {
            AddAlbumInnerScreenListOnly(
                newPhotos = viewModel.addAlbumUiState.albumDetails.photos,
                onChooseCamera = onChooseCamera,
                onChooseMaps = onChooseMaps,
                navigateBack = navigateBack,
                viewModel = viewModel,
            )
        } else {
            AddAlbumInnerScreenListAndDetails(
                newPhotos = viewModel.addAlbumUiState.albumDetails.photos,
                onChooseCamera = onChooseCamera,
                onChooseMaps = onChooseMaps,
                navigateBack = navigateBack,
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
                    navigateBack(HomeDestination.route, false)
                }
            })
        }
    }
}

@Composable
fun AddAlbumInnerScreenListOnly(
    newPhotos: List<Photo>,
    onChooseCamera: (Long) -> Unit,
    onChooseMaps: (Long) -> Unit,
    navigateBack: (route: String, include: Boolean) -> Unit,
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
            onChooseMaps = onChooseMaps,
            navigateBack = navigateBack
        )
    }
}

@Composable
fun AddAlbumInnerScreenListAndDetails(
    newPhotos: List<Photo>,
    onChooseCamera: (Long) -> Unit,
    onChooseMaps: (Long) -> Unit,
    navigateBack: (route: String, include: Boolean) -> Unit,
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
            onChooseMaps = onChooseMaps,
            navigateBack = navigateBack,
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
            title = viewModel.addAlbumUiState.albumDetails.album.name,
            description = viewModel.addAlbumUiState.albumDetails.album.description,
            onAlbumTitleChange = { viewModel.updateAlbumTitleUiState(it) },
            onAlbumDescriptionChange = { viewModel.updateAlbumDescriptionUiState(it) }
        )
    }
}

@Composable
fun AddAlbumPhoto(
    newPhotos: List<Photo>,
    onChooseCamera: (Long) -> Unit,
    onChooseMaps: (Long) -> Unit,
    navigateBack: (route: String, include: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        if (newPhotos.isEmpty()) {
            ChoosePicturesSourceScreen(
                onChooseCamera = onChooseCamera,
                onChooseMaps = onChooseMaps,
                navigateBack = navigateBack
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
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
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
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            )
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
        items(imagesList) { photo ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(photo.filePath)
                        .build(),
                    contentDescription = photo.description,
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
        com.example.photocaptioner.ui.Button(
            text = R.string.add_new_album,
            onClick = onAddNewAlbum
        )
    }
}

@Preview
@Composable
fun AddAlbumsScreenPreviewWithoutPhotos() {
    PhotoCaptionerTheme {
        AddAlbumScreen(
            {},
            {},
            {route, include ->},
            PhotoCaptionerContentType.LIST_ONLY
        )
    }
}

@Preview
@Composable
fun AddAlbumsScreenPreviewWithPhotos() {
    PhotoCaptionerTheme {
        AddAlbumScreen(
            {},
            {},
            {route, include ->},
            PhotoCaptionerContentType.LIST_ONLY
        )
    }
}

@Preview(widthDp = 1000)
@Composable
fun AddAlbumsScreenPreviewWithoutPhotosWithExpandedView() {
    PhotoCaptionerTheme {
        AddAlbumScreen(
            {},
            {},
            {route, include ->},
            PhotoCaptionerContentType.LIST_AND_DETAIL)
    }
}

@Preview(widthDp = 1000)
@Composable
fun AddAlbumsScreenPreviewWithPhotosWithExpandedView() {
    PhotoCaptionerTheme {
        AddAlbumScreen(
            {},
            {},
            {route, include ->},
            PhotoCaptionerContentType.LIST_AND_DETAIL
        )
    }
}