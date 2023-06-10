package com.example.photocaptioner.ui

import CameraPageDestination
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import androidx.navigation.compose.rememberNavController
import com.example.photocaptioner.R
import com.example.photocaptioner.data.MenuItemType
import com.example.photocaptioner.model.NavigationItemContent
import com.example.photocaptioner.ui.*
import com.example.photocaptioner.ui.screens.PhotoCaptionersViewModel
import com.example.photocaptioner.ui.screens.ResponsiveHomeScreen
import com.example.photocaptioner.ui.screens.album.AddAlbumDestination
import com.example.photocaptioner.ui.screens.album.AddOnlinePicturesDestination
import com.example.photocaptioner.ui.screens.album.AlbumDetailDestination
import com.example.photocaptioner.ui.screens.album.AlbumsDestination
import com.example.photocaptioner.ui.screens.album.EditAlbumDestination
import com.example.photocaptioner.ui.screens.pictures.AddPhotoToAlbumDestination
import com.example.photocaptioner.ui.screens.pictures.ChoosePicturesDestination
import com.example.photocaptioner.ui.screens.pictures.EditPhotoDestination
import com.example.photocaptioner.ui.utils.PhotoCaptionerContentType
import com.example.photocaptioner.ui.utils.PhotoCaptionerNavigationType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoCaptionerAppTopBar(
    canNavigateBack: Boolean,
    title: String,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (canNavigateBack) {
        TopAppBar(
            title = { Text(title) },
            modifier = modifier,
            navigationIcon = {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back_button)
                    )
                }
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
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
                title = uiState.topBarTitle,
                navigateUp = {
                    navController.navigateUp()
                    viewModel.updateTopBarTitle(navController.currentBackStackEntry?.arguments?.getString("title") ?: "")
                    viewModel.canNavigateBack(
                        navController.currentDestination?.route != HomeDestination.routeWithArgs &&
                        navController.currentDestination?.route != StartUpDestination.route
                    )
                    viewModel.updateCurrentMenuItem(
                        when (navController.currentDestination?.route) {
                            HomeDestination.routeWithArgs -> MenuItemType.Home
                            HomeDestination.route -> MenuItemType.Home
                            AlbumsDestination.route -> MenuItemType.Albums
                            CameraPageDestination.route -> MenuItemType.Photo
                            else -> viewModel.uiState.value.currentMenuItem
                        }
                    )
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
                onMenuItemPress = { navigationItem: NavigationItemContent ->
                    navController.navigate("${navigationItem.route}/${navigationItem.title}")
                    viewModel.updateCurrentMenuItem(menuItemType = navigationItem.menuItemType)
                    viewModel.updateTopBarTitle(title = navigationItem.title)
                },
                onStartUpClick = {
                    navController.navigate("${HomeDestination.route}/${"Home"}")
                    viewModel.updateBottomBarVisibility(true)
                },
                onTakePictureClick = {
                    navController.navigate("${CameraPageDestination.route}/${-2}/${"Take Picture"}")
                    viewModel.canNavigateBack(true)
                    viewModel.updateTopBarTitle("Take Picture")
                    viewModel.updateCurrentMenuItem(menuItemType = MenuItemType.Photo)
                },
                onGoToAlbumsClick = {
                    navController.navigate("${AlbumsDestination.route}/Albums")
                    viewModel.canNavigateBack(true)
                    viewModel.updateTopBarTitle("Albums")
                    viewModel.updateCurrentMenuItem(menuItemType = MenuItemType.Albums)
                },
                onAddAlbumClick = {
                    navController.navigate("${AddAlbumDestination.route}/${-1}/${"Add Album"}")
                    viewModel.canNavigateBack(true)
                    viewModel.updateTopBarTitle("Add Album")
                },
                onAlbumClick = {
                    navController.navigate("${AlbumDetailDestination.route}/${it}/${"Album Detail"}")
                    viewModel.canNavigateBack(true)
                    viewModel.updateTopBarTitle("Album Detail")
                },
                onEditClick = {
                    navController.navigate("${EditAlbumDestination.route}/${it}/${"Edit Album"}")
                    viewModel.canNavigateBack(true)
                    viewModel.updateTopBarTitle("Edit Album")
                },
                onAddPictureClick = {
                    navController.navigate("${ChoosePicturesDestination.route}/${it}/${"Choose Pictures"}")
                    viewModel.canNavigateBack(true)
                    viewModel.updateTopBarTitle("Choose Pictures")
                },
                onPhotoClick = { albumId, photoId ->
                    navController.navigate("${EditPhotoDestination.route}/${albumId}/${photoId}/${"Edit Photo"}")
                    viewModel.canNavigateBack(true)
                    viewModel.updateTopBarTitle("Edit Photo")
                },
                onChooseCamera = {
                    navController.navigate("${CameraPageDestination.route}/${it}/${"Take Picture"}")
                    viewModel.canNavigateBack(true)
                    viewModel.updateTopBarTitle("Take Picture")
                },
                onChooseMaps = {
                    navController.navigate("${AddOnlinePicturesDestination.route}/${it}/${"Add Online Pictures"}")
                    viewModel.canNavigateBack(true)
                    viewModel.updateTopBarTitle("Add Online Pictures")
                },
                navigateBack = { route, include ->
                    navController.popBackStack(route, include)
                    viewModel.updateCurrentMenuItem(
                        when (route) {
                            HomeDestination.routeWithArgs -> MenuItemType.Home
                            HomeDestination.route -> MenuItemType.Home
                            AlbumsDestination.route -> MenuItemType.Albums
                            CameraPageDestination.route -> MenuItemType.Photo
                            else -> viewModel.uiState.value.currentMenuItem
                        }
                    )
                },
                modifier = modifier,
                contentType = contentType,
                onRecentlyEditedClick = {
                    navController.navigate("${AlbumDetailDestination.route}/${it}/${"Album Detail"}")
                    viewModel.canNavigateBack(true)
                    viewModel.updateTopBarTitle("Album Detail")
                    viewModel.updateCurrentMenuItem(menuItemType = MenuItemType.Albums)
                },
                onTakePictureFromHome = {
                    navController.navigate("${AddPhotoToAlbumDestination.route}/${it}/${"Add Photo"}")
                    viewModel.canNavigateBack(true)
                    viewModel.updateTopBarTitle("Add Photo")
                },
                navigateToAlbums = {
                    navController.popBackStack(HomeDestination.routeWithArgs, false)
                    navController.navigate("${AlbumsDestination.route}/Albums")
                    viewModel.canNavigateBack(true)
                    viewModel.updateTopBarTitle("Albums")
                    viewModel.updateCurrentMenuItem(menuItemType = MenuItemType.Albums)
                },
                bottomBarVisible = viewModel.uiState.collectAsState().value.bottomBarVisible
            )
        }
    }
}