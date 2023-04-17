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
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.photocaptioner.ui.*
import com.example.photocaptioner.ui.theme.PhotoCaptionerTheme
import com.example.photocaptioner.ui.utils.PhotoCaptionerContentType

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
fun PhotoCaptionersAppTopBar(
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
fun PhotoCaptionersApp(
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

    val contentType: PhotoCaptionerContentType
    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            contentType = PhotoCaptionerContentType.LIST_ONLY
        }
        WindowWidthSizeClass.Medium -> {
            contentType = PhotoCaptionerContentType.LIST_ONLY
        }
        WindowWidthSizeClass.Expanded -> {
            contentType = PhotoCaptionerContentType.LIST_AND_DETAIL
        }
        else -> {
            contentType = PhotoCaptionerContentType.LIST_ONLY
        }
    }

    NavHost(
        navController = navController,
        startDestination = PhotoCaptionerScreen.Start.name,
        modifier = modifier
    ) {
        composable(PhotoCaptionerScreen.Start.name) {
            StartUpScreen(
                onButtonClick = {
                    viewModel.navigateToScreen(
                        newScreen = R.string.home,
                        canNavigateBack = false
                    )
                    navController.navigate(PhotoCaptionerScreen.Home.name)
                }
            )
        }
        composable(PhotoCaptionerScreen.Home.name) {
            HomeScreen(
                onTakePictureClick = {},
                onAlbumsClick = {
                    viewModel.navigateToScreen(
                        newScreen = R.string.my_albums,
                        canNavigateBack = true
                    )
                    navController.navigate(PhotoCaptionerScreen.Albums.name)
                },
                recentlyEdited = uiState.recentlyEdited,
                contentType = contentType
            )
        }
        composable(PhotoCaptionerScreen.Albums.name) {
            AlbumsScreen(
                albumList = uiState.albumList,
                onAddClick = {
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
                }
            )
        }
        composable(PhotoCaptionerScreen.AlbumDetail.name) {
            AlbumDetailScreen(
                album = uiState.selectedAlbum,
                onDownloadClick = { /*TODO*/ },
                onEditClick = {
                    viewModel.navigateToScreen(
                        newScreen = R.string.edit_album,
                        canNavigateBack = true
                    )
                    navController.navigate(PhotoCaptionerScreen.EditAlbum.name)
                },
                onAddClick = {
                    viewModel.navigateToScreen(
                        newScreen = R.string.choose_picture_source,
                        canNavigateBack = true
                    )
                    navController.navigate(PhotoCaptionerScreen.ChoosePicturesSource.name)
                },
                onShareClick = { /*TODO*/ },
                onPhotoClick = {
                    viewModel.selectPhoto(it)
                    viewModel.navigateToScreen(
                        newScreen = R.string.edit_photo,
                        canNavigateBack = true
                    )
                    navController.navigate(PhotoCaptionerScreen.EditPhoto.name)
                }
            )
        }
        composable(PhotoCaptionerScreen.ChoosePicturesSource.name) {
            ChoosePicturesSourceScreen(
                onChooseCamera = { /*TODO*/ },
                onChooseGallery = { /*TODO*/ },
                onChooseMaps = {
                    viewModel.navigateToScreen(
                        newScreen = R.string.upload_pictures,
                        canNavigateBack = true
                    )
                    navController.navigate(PhotoCaptionerScreen.AddPictures.name)
                }
            )
        }
        composable(PhotoCaptionerScreen.AddPictures.name) {
            AddPicturesScreen(
                searchValue = uiState.searchValue,
                searchedPhotos = uiState.searchedPhotos,
                onSearchChanged = {
                    viewModel.updateSearchValue(it)
                },
                onImageSelected = {
                    viewModel.selectImage(it)
                },
                onUploadButtonClick = { /*TODO*/ }
            )
        }
        composable(PhotoCaptionerScreen.AddAlbum.name) {
            AddAlbumsScreen(
                newPhotos = uiState.newPhotos,
                newTitle = uiState.newAlbumName,
                newDescription = uiState.newAlbumDescription,
                onAlbumTitleChange = {
                    viewModel.updateNewTitle(it)
                },
                onAlbumDescriptionChange = {
                    viewModel.updateNewDescription(it)
                },
                onChooseCamera = { /*TODO*/ },
                onChooseGallery = { /*TODO*/ },
                onChooseMaps = { /*TODO*/ },
                onAddNewAlbum = {
                    viewModel.addNewAlbum()
                    viewModel.navigateToScreen(
                        newScreen = R.string.my_albums,
                        canNavigateBack = true
                    )
                    navController.navigate(PhotoCaptionerScreen.Albums.name)
                },
                contentType = contentType
            )
        }
        composable(PhotoCaptionerScreen.EditAlbum.name) {
            EditAlbumScreen(
                albumToEdit = uiState.selectedAlbum,
                onAlbumTitleChange = {
                    viewModel.updateSelectedAlbumTitle(it)
                },
                onAlbumDescriptionChange = {
                    viewModel.updateSelectedAlbumDescription(it)
                },
                onSave = {
                    viewModel.saveSelectedAlbum()
                    viewModel.navigateToScreen(
                        newScreen = uiState.selectedAlbum.name,
                        canNavigateBack = true
                    )
                    navController.popBackStack(PhotoCaptionerScreen.AlbumDetail.name, false)
                }
            )
        }
        composable(PhotoCaptionerScreen.EditPhoto.name) {
            EditPhotoScreen(
                photoToEdit = uiState.selectedPhoto,
                description = uiState.newPhotoDescription,
                onPhotoDescriptionChange = {
                    viewModel.updateSelectedPhotoDescription(it)
                },
                onSave = {
                    viewModel.saveSelectedPhoto()
                    viewModel.navigateToScreen(
                        newScreen = uiState.selectedAlbum.name,
                        canNavigateBack = true
                    )
                    navController.popBackStack(PhotoCaptionerScreen.AlbumDetail.name, false)
                }
            )
        }
    }
}

@Preview
@Composable
fun PhotoCaptionersAppPreview() {
    PhotoCaptionerTheme {
        PhotoCaptionersApp(WindowWidthSizeClass.Compact)
    }
}