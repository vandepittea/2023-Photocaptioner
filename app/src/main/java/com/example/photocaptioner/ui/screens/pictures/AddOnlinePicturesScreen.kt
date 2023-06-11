package com.example.photocaptioner.ui.screens.pictures

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.photocaptioner.R
import com.example.photocaptioner.model.Photo
import com.example.photocaptioner.ui.AppViewModelProvider
import com.example.photocaptioner.ui.screens.ButtonWithIcon
import com.example.photocaptioner.ui.screens.SearchBox
import com.example.photocaptioner.ui.screens.navigation.NavigationDestination
import com.example.photocaptioner.ui.theme.PhotoCaptionerTheme

object AddOnlinePicturesDestination : NavigationDestination {
    override val route = "add_online_pictures"
    override val titleRes = R.string.upload_pictures
    const val albumIdArg = "albumId"
    const val backNavigationDestinationRouteArg = "backNavigationDestinationRoute"
    override val routeWithArgs = "$route/{$albumIdArg}/{$backNavigationDestinationRouteArg}/{title}"
}

@Composable
fun AddOnlinePicturesScreen(
    backNavigationDestinationRoute: String,
    navigateBack: (route: String, include: Boolean) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddOnlinePicturesViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.addOnlinePicturesUiState.collectAsState()
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Column(
            Modifier
                .fillMaxSize()
        ) {
            SearchBox(
                searchValue = uiState.searchValue,
                onValueChange = {
                    viewModel.updateSearchValue(it)
                    viewModel.searchImages(it)
                }
            )

            PicturesList(
                imagesList = uiState.searchedPhotos,
                onCheckChange = { viewModel.selectImage(it) }
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(MaterialTheme.colors.background)
                .padding(top = 10.dp, bottom = 10.dp)
        ) {
            UploadButton(onClick = {
                viewModel.addPhotosToAlbum()
                navigateBack(backNavigationDestinationRoute, false)
            })
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PicturesList(
    imagesList: List<Pair<Boolean, Photo>>,
    onCheckChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(400.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ){
        itemsIndexed(imagesList) { index, image ->
            Card(
                onClick = { onCheckChange(index) },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.background)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = image.first,
                        colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colors.primary),
                        onCheckedChange = { onCheckChange(index) }
                    )
                    AsyncImage(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data(image.second.filePath)
                            .build(),
                        contentDescription = image.second.description,
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
}

@Composable
fun UploadButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
    ) {
        ButtonWithIcon(
            painter = painterResource(id = R.drawable.baseline_file_upload_24),
            contentDescription = R.string.upload_icon,
            text = R.string.upload,
            onClick = onClick
        )
    }
}

@Preview
@Composable
fun AddPicturesScreenPreview() {
    PhotoCaptionerTheme {
        AddOnlinePicturesScreen(
            "",
            {_, _ ->}
        )
    }
}

@Preview
@Composable
fun AddPicturesScreenPreviewWithPictures() {
    PhotoCaptionerTheme {
        AddOnlinePicturesScreen(
            "",
            {_, _ ->}
        )
    }
}

@Preview(widthDp = 1000)
@Composable
fun AddPicturesScreenPreviewWithExtendedView() {
    PhotoCaptionerTheme {
        AddOnlinePicturesScreen(
            "",
            {_, _ ->}
        )
    }
}

@Preview(widthDp = 1000)
@Composable
fun AddPicturesScreenPreviewWithPicturesWithExtendedView() {
    PhotoCaptionerTheme {
        AddOnlinePicturesScreen(
            "",
            {_, _ ->}
        )
    }
}