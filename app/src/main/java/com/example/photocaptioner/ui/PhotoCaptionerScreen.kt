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
import com.example.photocaptioner.R
import com.example.photocaptioner.data.MenuItemType
import com.example.photocaptioner.ui.*
import com.example.photocaptioner.ui.screens.PhotoCaptionersViewModel
import com.example.photocaptioner.ui.screens.ResponsiveHomeScreen
import com.example.photocaptioner.ui.screens.album.AddAlbumDestination
import com.example.photocaptioner.ui.screens.album.AddOnlinePicturesDestination
import com.example.photocaptioner.ui.screens.album.AlbumDetailDestination
import com.example.photocaptioner.ui.screens.album.AlbumsDestination
import com.example.photocaptioner.ui.screens.album.EditAlbumDestination
import com.example.photocaptioner.ui.screens.pictures.ChoosePicturesDestination
import com.example.photocaptioner.ui.screens.pictures.EditPhotoDestination
import com.example.photocaptioner.ui.utils.PhotoCaptionerContentType
import com.example.photocaptioner.ui.utils.PhotoCaptionerNavigationType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoCaptionerAppTopBar(
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text("Needs to change") },
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
    modifier: Modifier = Modifier,
    viewModel: PhotoCaptionersViewModel = PhotoCaptionersViewModel()
) {
    val navigationType: PhotoCaptionerNavigationType
    val contentType: PhotoCaptionerContentType
    val navController = rememberNavController()
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
                    navController.navigate(HomeDestination.route)
                },
                onTakePictureClick = {},
                onGoToAlbumsClick = {
                    navController.navigate(AlbumsDestination.route)
                    viewModel.canNavigateBack(true)
                },
                onAddAlbumClick = {
                    navController.navigate(AddAlbumDestination.route)
                    viewModel.canNavigateBack(true)
                },
                onAlbumClick = {
                    navController.navigate("${AlbumDetailDestination.route}/${it}")
                    viewModel.canNavigateBack(true)
                },
                onEditClick = {
                    navController.navigate("${EditAlbumDestination.route}/${it}")
                    viewModel.canNavigateBack(true)
                },
                onAddPictureClick = {
                    navController.navigate("${ChoosePicturesDestination.route}/${it}")
                    viewModel.canNavigateBack(true)
                },
                onPhotoClick = {
                    navController.navigate("${EditPhotoDestination.route}/${it}")
                    viewModel.canNavigateBack(true)
                },
                onChooseCamera = {},
                onChooseGallery = {},
                onChooseMaps = {
                    navController.navigate("${AddOnlinePicturesDestination.route}/${it}")
                    viewModel.canNavigateBack(true)
                },
                modifier = modifier,
                contentType = contentType,
                onRecentlyEditedClick = {
                    navController.navigate("${AlbumDetailDestination.route}/${it}")
                    viewModel.canNavigateBack(true)
                },
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}