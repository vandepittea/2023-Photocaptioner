package com.example.photocaptioner.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.photocaptioner.R
import com.example.photocaptioner.data.Datasource
import com.example.photocaptioner.model.Album
import com.example.photocaptioner.model.AlbumWithImages
import com.example.photocaptioner.ui.screens.home.HomeViewModel
import com.example.photocaptioner.ui.screens.navigation.NavigationDestination
import com.example.photocaptioner.ui.theme.PhotoCaptionerTheme

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@Composable
fun HomeScreen(
    onTakePictureClick: () -> Unit,
    onAlbumsClick: () -> Unit,
    onRecentlyEditedClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val homeUiState by viewModel.homeUiState.collectAsState()
    BackHandler(
        onBack = {}
    )
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Buttons(onTakePictureClick, onAlbumsClick)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (homeUiState.recentlyEditedAlbum != null) {
                RecentEdits(
                    recentlyEdited = homeUiState.recentlyEditedAlbum!!,
                    onRecentlyEditedClick = { onRecentlyEditedClick(homeUiState.recentlyEditedAlbum!!.album.id) }
                )
            }
        }
    }
}

@Composable
fun Buttons(
    onTakePictureClick: () -> Unit,
    onAlbumsClick: () -> Unit
) {
    ButtonWithIcon(
        painter = painterResource(id = R.drawable.baseline_camera_alt_24),
        contentDescription = R.string.camera_icon,
        text = R.string.take_picture,
        onClick = onTakePictureClick
    )

    ButtonWithIcon(
        painter = painterResource(id = R.drawable.baseline_photo_album_24),
        contentDescription = R.string.album_icon,
        text = R.string.albums,
        onClick = onAlbumsClick
    )
}

@Composable
fun RecentEdits(
    recentlyEdited: AlbumWithImages,
    onRecentlyEditedClick: () -> Unit
) {
    Text(
        text = stringResource(id = R.string.recently_edited),
        style = MaterialTheme.typography.subtitle1,
        modifier = Modifier.padding(vertical = 16.dp, horizontal = 32.dp)
    )

    ImageWithDescription(
        image = recentlyEdited.photos.first(),
        description = recentlyEdited.album.description,
        onClick = onRecentlyEditedClick
    )
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(){
    PhotoCaptionerTheme {
        HomeScreen({}, {}, {})
    }
}

@Preview(showBackground = true, widthDp = 1000)
@Composable
fun HomeScreenPreviewListAndDetail(){
    PhotoCaptionerTheme {
        HomeScreen({}, {}, {})
    }
}