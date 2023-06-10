package com.example.photocaptioner.ui.screens.album

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.photocaptioner.ui.theme.PhotoCaptionerTheme
import com.example.photocaptioner.R
import com.example.photocaptioner.model.Album
import com.example.photocaptioner.model.AlbumWithImages
import com.example.photocaptioner.model.Photo
import com.example.photocaptioner.ui.AlternatingColumn
import com.example.photocaptioner.ui.AppViewModelProvider
import com.example.photocaptioner.ui.screens.navigation.NavigationDestination

object AlbumDetailDestination : NavigationDestination {
    override val route = "album_detail"
    override val titleRes = R.string.album_title
    const val albumIdArg = "albumId"
    val routeWithArgs = "$route/{$albumIdArg}"
}

@Composable
fun AlbumDetailScreen(
    onEditClick: (Long) -> Unit,
    onAddClick: (Long) -> Unit,
    onPhotoClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AlbumDetailViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val context = LocalContext.current
    val albumUiState by viewModel.albumDetailUiState.collectAsState()
    val openDocumentTreeLauncher = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocumentTree()) {
        viewModel.downloadAlbum(it)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            AlbumDetails(
                albumWithImages = albumUiState.albumDetails,
                onDownloadClick = {
                    openDocumentTreeLauncher.launch(null)
                },
                onEditClick = onEditClick,
                onPhotoClick = onPhotoClick,
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(MaterialTheme.colors.background)
        ) {
            AlbumFooter(
                album = albumUiState.albumDetails.album,
                onAddClick = onAddClick,
                onShareClick = { viewModel.shareAlbum(context) },
            )
        }
    }
}

@Composable
fun AlbumDetails(
    albumWithImages: AlbumWithImages,
    onDownloadClick: () -> Unit,
    onEditClick: (Long) -> Unit,
    onPhotoClick: (Long) -> Unit
) {
    AlbumHeader(
        album = albumWithImages.album,
        onDownloadClick = onDownloadClick,
        onEditClick = onEditClick,
    )

    DescriptionRow(
        description = albumWithImages.album.description
    )

    TimeRow(
        lastChanged = "${albumWithImages.album.lastChanged}",
    )

    AlternatingColumn(
        items = albumWithImages.photos,
        onPhotoClick = onPhotoClick,
    )
}

@Composable
fun AlbumHeader(
    album: Album,
    onDownloadClick: () -> Unit,
    onEditClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = album.name,
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier
                .padding(bottom = 4.dp)
                .weight(6f)
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            IconButton(
                onClick = onDownloadClick,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_download_24),
                    contentDescription = stringResource(R.string.download_icon),
                    tint = MaterialTheme.colors.onBackground,
                    modifier = Modifier.size(45.dp)
                )
            }

            IconButton(
                onClick = { onEditClick(album.id) },
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.edit_icon),
                    tint = MaterialTheme.colors.onBackground,
                    modifier = Modifier.size(45.dp)
                )
            }
        }
    }
}

@Composable
fun DescriptionRow(
    description: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(bottom = 16.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_description_24),
            contentDescription = stringResource(id = R.string.description_icon),
            tint = MaterialTheme.colors.onBackground,
            modifier = Modifier.size(40.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = description,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onBackground
        )
    }
}

@Composable
fun TimeRow(
    lastChanged: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_access_time_filled_24),
            contentDescription = stringResource(id = R.string.time_icon),
            tint = MaterialTheme.colors.onBackground,
            modifier = Modifier.size(32.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = lastChanged,
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onBackground,
        )
    }
}

@Composable
fun AlbumFooter(
    album: Album,
    onAddClick: (Long) -> Unit,
    onShareClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 10.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { onAddClick(album.id) }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_add_24),
                contentDescription = stringResource(R.string.add_icon),
                tint = MaterialTheme.colors.onBackground,
                modifier = Modifier.size(45.dp)
            )
        }

        IconButton(
            onClick = onShareClick
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_share_24),
                contentDescription = stringResource(R.string.share_icon),
                tint = MaterialTheme.colors.onBackground,
                modifier = Modifier.size(45.dp)
            )
        }
    }
}

@Preview
@Composable
fun AlbumDetailScreenPreview(){
    PhotoCaptionerTheme {
        AlbumDetailScreen({}, {}, {})
    }
}