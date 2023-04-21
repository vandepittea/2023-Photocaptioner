package com.example.photocaptioner.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.photocaptioner.data.MenuItemType
import com.example.photocaptioner.ui.*
import com.example.photocaptioner.ui.screens.PhotoCaptionersViewModel
import com.example.photocaptioner.ui.utils.PhotoCaptionerContentType
import com.example.photocaptioner.ui.utils.PhotoCaptionerNavigationType

enum class PhotoCaptionerScreen(@StringRes val title: Int) {
    Start(R.string.start),
    Home(R.string.home),
    Albums(R.string.albums),
    AlbumDetail(R.string.album_menu),
    ChoosePicturesSource(R.string.choose_picture_source),
    AddPictures(R.string.upload_pictures),
    AddAlbum(R.string.add_album),
    EditAlbum(R.string.edit_album),
    EditPhoto(R.string.edit_photo)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoCaptionerAppTopBar(
    currentScreen: PhotoCaptionerScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(id = currentScreen.title)) },
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoCaptionerApp(
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier
) {
    val navigationType: PhotoCaptionerNavigationType
    val contentType: PhotoCaptionerContentType
    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentScreen = PhotoCaptionerScreen.valueOf(
        backStackEntry.value?.destination?.route ?: PhotoCaptionerScreen.Start.name
    )
    val viewModel: PhotoCaptionersViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            navigationType = PhotoCaptionerNavigationType.BOTTOM_NAVIGATION
            contentType = PhotoCaptionerContentType.LIST_ONLY
        }

        WindowWidthSizeClass.Medium -> {
            navigationType = PhotoCaptionerNavigationType.NAVIGATION_RAIL
            contentType = PhotoCaptionerContentType.LIST_ONLY
        }

        WindowWidthSizeClass.Expanded -> {
            navigationType = PhotoCaptionerNavigationType.PERMANENT_NAVIGATION_DRAWER
            contentType = PhotoCaptionerContentType.LIST_AND_DETAIL
        }

        else -> {
            navigationType = PhotoCaptionerNavigationType.BOTTOM_NAVIGATION
            contentType = PhotoCaptionerContentType.LIST_ONLY
        }
    }

    Scaffold(
        topBar = {
            PhotoCaptionerAppTopBar(
                currentScreen = currentScreen,
                canNavigateBack = uiState.canNavigateBack,
                navigateUp = {
                    navController.navigateUp()
                }
            )
        }
    ) {paddingValues ->
        Box(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            ResponsiveHomeScreen(
                navigationType = navigationType,
                navController = navController,
                currentMenuItem = uiState.currentMenuItem,
                onMenuItemPress = { menuItemType: MenuItemType ->
                    viewModel.updateCurrentMenuItem(menuItemType = menuItemType)
                    navController.navigate(menuItemType.name)
                },
                onStartUpClick = {
                    viewModel.navigateToScreen(
                        newScreen = R.string.home,
                        canNavigateBack = false
                    )
                    viewModel.updateCurrentMenuItem(menuItemType = MenuItemType.Home)
                    navController.navigate(PhotoCaptionerScreen.Home.name)
                },
                onTakePictureClick = {},
                onGoToAlbumsClick = {
                    viewModel.navigateToScreen(
                        newScreen = R.string.my_albums,
                        canNavigateBack = true
                    )
                    viewModel.updateCurrentMenuItem(menuItemType = MenuItemType.Albums)
                    navController.navigate(PhotoCaptionerScreen.Albums.name)
                },
                recentlyEdited = uiState.recentlyEdited,
                albumList = uiState.albumList,
                onAddAlbumClick = {
                    viewModel.navigateToScreen(
                        newScreen = R.string.add_album,
                        canNavigateBack = true
                    )
                    viewModel.updateCurrentMenuItem(menuItemType = MenuItemType.Albums)
                    navController.navigate(PhotoCaptionerScreen.AddAlbum.name)
                },
                onAlbumClick = {
                    viewModel.selectAlbum(it)
                    viewModel.navigateToScreen(
                        newScreen = it.name,
                        canNavigateBack = true
                    )
                    viewModel.updateCurrentMenuItem(menuItemType = MenuItemType.Albums)
                    navController.navigate(PhotoCaptionerScreen.AlbumDetail.name)
                },
                detailedAlbum = uiState.selectedAlbum,
                onDownloadClick = {},
                onEditClick = {
                    viewModel.navigateToScreen(
                        newScreen = R.string.edit_album,
                        canNavigateBack = true
                    )
                    viewModel.updateCurrentMenuItem(menuItemType = MenuItemType.Albums)
                    navController.navigate(PhotoCaptionerScreen.EditAlbum.name)
                },
                onAddPictureClick = {
                    viewModel.navigateToScreen(
                        newScreen = R.string.choose_picture_source,
                        canNavigateBack = true
                    )
                    viewModel.updateCurrentMenuItem(menuItemType = MenuItemType.Photo)
                    navController.navigate(PhotoCaptionerScreen.ChoosePicturesSource.name)
                },
                onShareClick = {},
                onPhotoClick = {
                    viewModel.selectPhoto(it)
                    viewModel.navigateToScreen(
                        newScreen = R.string.edit_photo,
                        canNavigateBack = true
                    )
                    viewModel.updateCurrentMenuItem(menuItemType = MenuItemType.Albums)
                    navController.navigate(PhotoCaptionerScreen.EditPhoto.name)
                },
                onChooseCamera = {},
                onChooseGallery = {},
                onChooseMaps = {
                    viewModel.navigateToScreen(
                        newScreen = R.string.upload_pictures,
                        canNavigateBack = true
                    )
                    viewModel.updateCurrentMenuItem(menuItemType = MenuItemType.Photo)
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
                onPictureUploadButtonClick = {
                    // Improve when we can use pictures with urls
                    navController.navigateUp()
                },
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
                    viewModel.updateCurrentMenuItem(menuItemType = MenuItemType.Albums)
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
                    viewModel.updateCurrentMenuItem(menuItemType = MenuItemType.Albums)
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
                    viewModel.updateCurrentMenuItem(menuItemType = MenuItemType.Albums)
                    navController.popBackStack(PhotoCaptionerScreen.AlbumDetail.name, false)
                },
                modifier = modifier,
                contentType = contentType,
                onRecentlyEditedClick = {
                    viewModel.selectAlbum(uiState.recentlyEdited)
                    viewModel.navigateToScreen(
                        newScreen = uiState.selectedAlbum.name,
                        canNavigateBack = true
                    )
                    viewModel.updateCurrentMenuItem(menuItemType = MenuItemType.Albums)
                    navController.navigate(PhotoCaptionerScreen.AlbumDetail.name)
                }
            )
        }
    }
}