package com.example.photocaptioner.ui.screens.album

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.photocaptioner.R
import com.example.photocaptioner.data.Datasource
import com.example.photocaptioner.model.Album
import com.example.photocaptioner.ui.AppViewModelProvider
import com.example.photocaptioner.ui.Button
import com.example.photocaptioner.ui.theme.PhotoCaptionerTheme
import kotlinx.coroutines.launch

@Composable
fun EditAlbumScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EditAlbumViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(16.dp)
    ) {
        Column {
            com.example.photocaptioner.ui.TopBar(title = R.string.edit_album)
            AlbumTextFields(
                title = viewModel.editAlbumUiState.albumDetails.album.name,
                description = viewModel.editAlbumUiState.albumDetails.album.description,
                onAlbumTitleChange = { viewModel.updateAlbumTitleUiState(it) },
                onAlbumDescriptionChange = { viewModel.updateAlbumDescriptionUiState(it) }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                text = R.string.save,
                onClick = {
                    coroutineScope.launch {
                        viewModel.saveItem()
                        navigateBack()
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
        EditAlbumScreen({})
    }
}