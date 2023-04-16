package com.example.photocaptioner

import androidx.annotation.StringRes
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.photocaptioner.ui.*

enum class PhotoCaptionerScreen {
    Start,
    Home,
    Albums,
    AlbumDetail,
    ChoosePicturesSource,
    AddPictures,
    AddAlbum,
    EditAlbum,
    EditPhoto
}

@Composable
fun PhotoCaptionerAppTopBar(
    @StringRes currentScreen: Int,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(id = currentScreen)) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun PhotoCaptionerApp(
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentScreen = PhotoCaptionerScreen.valueOf(
        backStackEntry.value?.destination?.route ?: PhotoCaptionerScreen.Start.name
    )
    val viewModel: PhotoCaptionersViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    ResponsiveHomeScreen(
        navController = navController,
        onStartUpClick = {
            viewModel.navigateToScreen(
                newScreen = R.string.home,
                canNavigateBack = false
            )
            navController.navigate(PhotoCaptionerScreen.Home.name)
        },
        onTakePictureClick = {},
        onGoToAlbumsClick = {
            viewModel.navigateToScreen(
                newScreen = R.string.my_albums,
                canNavigateBack = true
            )
            navController.navigate(PhotoCaptionerScreen.Albums.name)
        },
        albumList = uiState.albumList,
        recentlyEdited = uiState.recentlyEdited,
        onAddAlbumClick = {
            viewModel.navigateToScreen(
                newScreen = R.string.add_album,
                canNavigateBack = true
            )
            navController.navigate(PhotoCaptionerScreen.AddAlbum.name)
        },
        onAlbumClick = {
            viewModel.selectAlbum(it)
            viewModel.navigateToScreen(
                newScreen = it.name,
                canNavigateBack = true
            )
            navController.navigate(PhotoCaptionerScreen.AlbumDetail.name)
        },
        detailedAlbum = uiState.selectedAlbum,
        onDownloadClick = {},
        onEditClick = {
            viewModel.navigateToScreen(
                newScreen = R.string.edit_album,
                canNavigateBack = true
            )
            navController.navigate(PhotoCaptionerScreen.EditAlbum.name)
        },
        onAddPictureClick = {
            viewModel.navigateToScreen(
                newScreen = R.string.choose_picture_source,
                canNavigateBack = true
            )
            navController.navigate(PhotoCaptionerScreen.ChoosePicturesSource.name)
        },
        onShareClick = {},
        onPhotoClick = {
            viewModel.selectPhoto(it)
            viewModel.navigateToScreen(
                newScreen = R.string.edit_photo,
                canNavigateBack = true
            )
            navController.navigate(PhotoCaptionerScreen.EditPhoto.name)
        },
        onChooseCamera = {},
        onChooseGallery = {},
        onChooseMaps = {
            viewModel.navigateToScreen(
                newScreen = R.string.upload_pictures,
                canNavigateBack = true
            )
            navController.navigate(PhotoCaptionerScreen.AddPictures.name)
        },
        searchValue = uiState.searchValue,
        searchedPhotos = uiState.searchedPhotos,
        onSearchChanged = {
            viewModel.updateSearchValue(it)
        },
        onImageSelected = {
            viewModel.selectImage(it)
        },
        onPictureUploadButtonClick = {},
        newPhotos = uiState.newPhotos,
        newTitle = uiState.newAlbumName,
        newDescription = uiState.newAlbumDescription,
        onAlbumTitleAdd = {
            viewModel.updateNewTitle(it)
        },
        onAlbumDescriptionAdd = {
            viewModel.updateNewDescription(it)
        },
        onAddNewAlbum = {
            viewModel.addNewAlbum()
            viewModel.navigateToScreen(
                newScreen = R.string.my_albums,
                canNavigateBack = true
            )
            navController.navigate(PhotoCaptionerScreen.Albums.name)
        },
        albumToEdit = uiState.selectedAlbum,
        onAlbumTitleChange = {
            viewModel.updateSelectedAlbumTitle(it)
        },
        onAlbumDescriptionChange = {
            viewModel.updateSelectedAlbumDescription(it)
        },
        onAlbumSave = {
            viewModel.saveSelectedAlbum()
            viewModel.navigateToScreen(
                newScreen = uiState.selectedAlbum.name,
                canNavigateBack = true
            )
            navController.popBackStack(PhotoCaptionerScreen.AlbumDetail.name, false)
        },
        photoToEdit = uiState.selectedPhoto,
        photoDescriptionToEdit = uiState.newPhotoDescription,
        onPhotoDescriptionChange = {
            viewModel.updateSelectedPhotoDescription(it)
        },
        onPhotoSave = {
            viewModel.saveSelectedPhoto()
            viewModel.navigateToScreen(
                newScreen = uiState.selectedAlbum.name,
                canNavigateBack = true
            )
            navController.popBackStack(PhotoCaptionerScreen.AlbumDetail.name, false)
        }
    )
}