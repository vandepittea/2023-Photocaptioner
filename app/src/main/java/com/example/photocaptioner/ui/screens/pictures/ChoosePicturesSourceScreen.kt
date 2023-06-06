package com.example.photocaptioner.ui.screens.pictures

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.photocaptioner.R
import com.example.photocaptioner.ui.AppViewModelProvider
import com.example.photocaptioner.ui.ImageOptions
import com.example.photocaptioner.ui.TopBar
import com.example.photocaptioner.ui.screens.navigation.NavigationDestination
import com.example.photocaptioner.ui.theme.PhotoCaptionerTheme
import kotlinx.coroutines.launch

object ChoosePicturesDestination : NavigationDestination {
    override val route = "choose_pictures"
    override val titleRes = R.string.choose_picture_source
    const val albumIdArg = "albumId"
    val routeWithArgs = "$route/{$albumIdArg}"
}

@Composable
fun ChoosePicturesSourceScreen(
    onChooseCamera: () -> Unit,
    onChooseMaps: (Long) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ChoosePicturesSourceViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) {
        coroutineScope.launch {
            viewModel.onGalleryResult(it)
            navigateBack()
        }
    }
    Box(
        modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(16.dp)
    ) {
        Column {
            TopBar(title = R.string.choose_picture_source)
            ImageOptions(
                onChooseCamera = onChooseCamera,
                onChooseGallery = {
                    galleryLauncher.launch("image/*")
                },
                onChooseMaps = { onChooseMaps(viewModel.albumId) }
            )
        }
    }
}

@Preview
@Composable
fun ChoosePicturesSourceScreenPreview() {
    PhotoCaptionerTheme {
        ChoosePicturesSourceScreen(
            onChooseCamera = {},
            onChooseMaps = {},
            navigateBack = {}
        )
    }
}