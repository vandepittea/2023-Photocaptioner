package com.example.photocaptioner.ui.screens.album

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.photocaptioner.R
import com.example.photocaptioner.ui.AppViewModelProvider
import com.example.photocaptioner.ui.screens.AlbumTextFields
import com.example.photocaptioner.ui.screens.Button
import com.example.photocaptioner.ui.screens.navigation.NavigationDestination
import com.example.photocaptioner.ui.theme.PhotoCaptionerTheme
import kotlinx.coroutines.launch

object EditAlbumDestination : NavigationDestination {
    override val route = "edit_album"
    override val titleRes = R.string.edit_album
    const val albumIdArg = "albumId"
    override val routeWithArgs = "$route/{$albumIdArg}/{title}"
}

@Composable
fun EditAlbumScreen(
    navigateBack: (route: String, include: Boolean) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AlbumInformationViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(16.dp)
    ) {
        Column {
            AlbumTextFields(
                title = viewModel.albumUiState.albumDetails.album.name,
                description = viewModel.albumUiState.albumDetails.album.description,
                validNameEntry = viewModel.albumUiState.isNameEntryValid,
                validDescriptionEntry = viewModel.albumUiState.isDescriptionEntryValid,
                onAlbumTitleChange = { viewModel.updateAlbumTitleUiState(it) },
                onAlbumDescriptionChange = { viewModel.updateAlbumDescriptionUiState(it) }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                text = R.string.save,
                onClick = {
                    coroutineScope.launch {
                        viewModel.saveItem(
                            navigateToAlbums = {
                                navigateBack(AlbumsDestination.route, true)
                            },
                            navigateBack = navigateBack
                        )
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun EditAlbumScreenPreview() {
    PhotoCaptionerTheme {
        EditAlbumScreen({_, _ ->})
    }
}