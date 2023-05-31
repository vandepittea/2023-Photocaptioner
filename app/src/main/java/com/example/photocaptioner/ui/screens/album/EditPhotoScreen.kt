package com.example.photocaptioner.ui.screens.album

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.photocaptioner.R
import com.example.photocaptioner.data.Datasource
import com.example.photocaptioner.model.Photo
import com.example.photocaptioner.ui.AppViewModelProvider
import com.example.photocaptioner.ui.Button
import com.example.photocaptioner.ui.theme.PhotoCaptionerTheme
import kotlinx.coroutines.launch

@Composable
fun EditPhotoScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EditPhotoViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(16.dp)
    ) {
        Column(
            modifier = modifier
        ) {
            com.example.photocaptioner.ui.TopBar(title = R.string.edit_photo)
            Spacer(modifier = Modifier.height(8.dp))
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(viewModel.editPhotoUiState.photoDetails.filePath)
                    .build(),
                contentDescription = viewModel.editPhotoUiState.photoDetails.description
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = viewModel.editPhotoUiState.photoDetails.description,
                onValueChange = { viewModel.updateAlbumDescriptionUiState(it) },
                label = {
                    Text(
                        text = viewModel.editPhotoUiState.photoDetails.description,
                        style = MaterialTheme.typography.body1
                    )
                }
            )
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(MaterialTheme.colors.background)
        ) {
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
fun EditPhotoScreenPreview() {
    PhotoCaptionerTheme {
        EditPhotoScreen({})
    }
}