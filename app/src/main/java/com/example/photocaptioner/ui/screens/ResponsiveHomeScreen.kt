package com.example.photocaptioner.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.*
import androidx.compose.material3.NavigationRailItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.photocaptioner.data.MenuItemType
import com.example.photocaptioner.model.NavigationItemContent
import com.example.photocaptioner.ui.utils.PhotoCaptionerNavigationType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.photocaptioner.R
import com.example.photocaptioner.data.Datasource.navigationItemContentList
import com.example.photocaptioner.ui.AlbumDetailAndAlbumEditScreen
import com.example.photocaptioner.ui.AlbumDetailAndPhotoEditScreen
import com.example.photocaptioner.ui.AlbumDetailAndPhotoSourceChooserScreen
import com.example.photocaptioner.ui.AlbumsAndAlbumDetailScreen
import com.example.photocaptioner.ui.screens.album.AlbumsScreen
import com.example.photocaptioner.ui.HomeDestination
import com.example.photocaptioner.ui.screens.album.EditAlbumScreen
import com.example.photocaptioner.ui.HomeScreen
import com.example.photocaptioner.ui.StartUpDestination
import com.example.photocaptioner.ui.StartUpScreen
import com.example.photocaptioner.ui.screens.album.AddAlbumDestination
import com.example.photocaptioner.ui.screens.album.AddAlbumScreen
import com.example.photocaptioner.ui.screens.album.AddOnlinePicturesDestination
import com.example.photocaptioner.ui.screens.album.AddOnlinePicturesScreen
import com.example.photocaptioner.ui.screens.album.AlbumDetailDestination
import com.example.photocaptioner.ui.screens.album.AlbumDetailScreen
import com.example.photocaptioner.ui.screens.album.AlbumsDestination
import com.example.photocaptioner.ui.screens.album.EditAlbumDestination
import com.example.photocaptioner.ui.screens.pictures.ChoosePicturesDestination
import com.example.photocaptioner.ui.screens.pictures.ChoosePicturesSourceScreen
import com.example.photocaptioner.ui.screens.pictures.EditPhotoDestination
import com.example.photocaptioner.ui.screens.pictures.EditPhotoScreen
import com.example.photocaptioner.ui.utils.PhotoCaptionerContentType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResponsiveHomeScreen(
    navigationType: PhotoCaptionerNavigationType,
    navController: NavHostController,
    currentMenuItem: MenuItemType,
    onMenuItemPress: (MenuItemType) -> Unit,
    onStartUpClick: () -> Unit,
    onTakePictureClick: () -> Unit,
    onGoToAlbumsClick: () -> Unit,
    onAddAlbumClick: () -> Unit,
    onAlbumClick: (Long) -> Unit,
    onEditClick: (Long) -> Unit,
    onAddPictureClick: (Long) -> Unit,
    onPhotoClick: (Long) -> Unit,
    onChooseCamera: () -> Unit,
    onChooseGallery: () -> Unit,
    onChooseMaps: (Long) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    contentType: PhotoCaptionerContentType,
    onRecentlyEditedClick: (Long) -> Unit,
) {
    Row(modifier = modifier.fillMaxSize()) {
        AnimatedVisibility(visible = navigationType == PhotoCaptionerNavigationType.NAVIGATION_RAIL) {
            val navigationRailContentDescription = stringResource(R.string.navigation_rail)
            NavigationRail(
                currentTab = currentMenuItem,
                onTabPressed = onMenuItemPress,
                navigationItemContentList = navigationItemContentList,
                modifier = Modifier.testTag(navigationRailContentDescription)
            )
        }

        AnimatedVisibility(visible = navigationType == PhotoCaptionerNavigationType.PERMANENT_NAVIGATION_DRAWER) {
            val navigationDrawerContentDescription = stringResource(R.string.navigation_drawer)
            PermanentNavigationDrawer(
                drawerContent = {
                    PermanentDrawerSheet(Modifier.width(240.dp)) {
                        NavigationDrawerContent(
                            selectedDestination = currentMenuItem,
                            onTabPressed = onMenuItemPress,
                            navigationItemContentList = navigationItemContentList
                        )
                    }
                },
                modifier = Modifier.testTag(navigationDrawerContentDescription)
            ) {
                InAppNavigation(
                    navController = navController,
                    onStartUpClick = onStartUpClick,
                    onTakePictureClick = onTakePictureClick,
                    onGoToAlbumsClick = onGoToAlbumsClick,
                    onAddAlbumClick = onAddAlbumClick,
                    onAlbumClick = onAlbumClick,
                    onEditClick = onEditClick,
                    onAddPictureClick = onAddPictureClick,
                    onPhotoClick = onPhotoClick,
                    onChooseCamera = onChooseCamera,
                    onChooseGallery = onChooseGallery,
                    onChooseMaps = onChooseMaps,
                    navigateBack = navigateBack,
                    contentType = contentType,
                    onRecentlyEditedClick = onRecentlyEditedClick,
                    modifier = modifier
                )
            }
        }

        Column(modifier.fillMaxSize()) {
            InAppNavigation(
                navController = navController,
                onStartUpClick = onStartUpClick,
                onTakePictureClick = onTakePictureClick,
                onGoToAlbumsClick = onGoToAlbumsClick,
                onAddAlbumClick = onAddAlbumClick,
                onAlbumClick = onAlbumClick,
                onEditClick = onEditClick,
                onAddPictureClick = onAddPictureClick,
                onPhotoClick = onPhotoClick,
                onChooseCamera = onChooseCamera,
                onChooseGallery = onChooseGallery,
                onChooseMaps = onChooseMaps,
                navigateBack = navigateBack,
                contentType = contentType,
                onRecentlyEditedClick = onRecentlyEditedClick,
                modifier = modifier
            )

            AnimatedVisibility(visible = navigationType == PhotoCaptionerNavigationType.BOTTOM_NAVIGATION) {
                val bottomNavigationContentDescription = stringResource(R.string.navigation_bottom)
                BottomNavigationBar(
                    currentTab = currentMenuItem,
                    onTabPressed = onMenuItemPress,
                    navigationItemContentList = navigationItemContentList,
                    modifier = Modifier.testTag(bottomNavigationContentDescription)
                )
            }
        }
    }
}

@Composable
private fun InAppNavigation(
    navController: NavHostController,
    onStartUpClick: () -> Unit,
    onTakePictureClick: () -> Unit,
    onGoToAlbumsClick: () -> Unit,
    onAddAlbumClick: () -> Unit,
    onAlbumClick: (Long) -> Unit,
    onEditClick: (Long) -> Unit,
    onAddPictureClick: (Long) -> Unit,
    onPhotoClick: (Long) -> Unit,
    onChooseCamera: () -> Unit,
    onChooseGallery: () -> Unit,
    onChooseMaps: (Long) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    contentType: PhotoCaptionerContentType,
    onRecentlyEditedClick: (Long) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = StartUpDestination.route,
        modifier = modifier
    ) {
        composable(route = StartUpDestination.route) {
            StartUpScreen(
                onButtonClick = onStartUpClick
            )
        }

        composable(route = HomeDestination.route) {
            HomeScreen(
                onTakePictureClick = onTakePictureClick,
                onAlbumsClick = onGoToAlbumsClick,
                onRecentlyEditedClick = onRecentlyEditedClick
            )
        }

        composable(route = AlbumsDestination.route) {
            AlbumsScreen(
                onAddClick = onAddAlbumClick,
                onAlbumClick = onAlbumClick
            )
        }

        composable(
            route = AlbumDetailDestination.routeWithArgs,
            arguments = listOf(navArgument(AlbumDetailDestination.albumIdArg) { type = NavType.LongType })
        ) {
            if (contentType == PhotoCaptionerContentType.LIST_AND_DETAIL) {
                AlbumsAndAlbumDetailScreen(
                    onAddClick = onAddAlbumClick,
                    onAlbumClick = onAlbumClick,
                    onEditClick = onEditClick,
                    onAddPictureClick = onAddPictureClick,
                    onPhotoClick = onPhotoClick
                )
            } else {
                AlbumDetailScreen(
                    onEditClick = onEditClick,
                    onAddClick = onAddPictureClick,
                    onPhotoClick = onPhotoClick
                )
            }
        }

        composable(
            route = ChoosePicturesDestination.routeWithArgs,
            arguments = listOf(navArgument(ChoosePicturesDestination.albumIdArg) { type = NavType.LongType })
        ) {
            if (contentType == PhotoCaptionerContentType.LIST_AND_DETAIL) {
                AlbumDetailAndPhotoSourceChooserScreen(
                    onEditClick = onEditClick,
                    onAddPictureClick = onAddPictureClick,
                    onPhotoClick = onPhotoClick,
                    onChooseCamera = onChooseCamera,
                    onChooseGallery = onChooseGallery,
                    onChooseMaps = onChooseMaps
                )
            } else {
                ChoosePicturesSourceScreen(
                    onChooseCamera = onChooseCamera,
                    onChooseGallery = onChooseGallery,
                    onChooseMaps = onChooseMaps
                )
            }
        }

        composable(
            route = AddOnlinePicturesDestination.routeWithArgs,
arguments = listOf(navArgument(AddOnlinePicturesDestination.albumIdArg) { type = NavType.LongType })
        ) {
            AddOnlinePicturesScreen(
                navigateBack = navigateBack
            )
        }

        composable(route = AddAlbumDestination.route) {
            AddAlbumScreen(
                onChooseCamera = onChooseCamera,
                onChooseGallery = onChooseGallery,
                onChooseMaps = onChooseMaps,
                navigateBack = navigateBack,
                contentType = contentType
            )
        }

        composable(
            route = EditAlbumDestination.routeWithArgs,
            arguments = listOf(navArgument(EditAlbumDestination.albumIdArg) { type = NavType.LongType })
        ) {
            if (contentType == PhotoCaptionerContentType.LIST_AND_DETAIL) {
                AlbumDetailAndAlbumEditScreen(
                    onEditClick = onEditClick,
                    onAddPictureClick = onAddPictureClick,
                    onPhotoClick = onPhotoClick,
                    navigateBack = navigateBack
                )
            } else {
                EditAlbumScreen(
                    navigateBack = navigateBack
                )
            }
        }

        composable(
            route = EditPhotoDestination.routeWithArgs,
            arguments = listOf(navArgument(EditPhotoDestination.photoIdArg) { type = NavType.LongType })
        ) {
            if (contentType == PhotoCaptionerContentType.LIST_AND_DETAIL) {
                AlbumDetailAndPhotoEditScreen(
                    onEditClick = onEditClick,
                    onAddPictureClick = onAddPictureClick,
                    onPhotoClick = onPhotoClick,
                    navigateBack = navigateBack
                )
            } else {
                EditPhotoScreen(
                    navigateBack = navigateBack
                )
            }
        }
    }
}

@Composable
private fun NavigationRail(
    currentTab: MenuItemType,
    onTabPressed: ((MenuItemType) -> Unit),
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier
) {
    NavigationRail(modifier = modifier.fillMaxHeight()) {
        for (navItem in navigationItemContentList) {
            NavigationRailItem(
                selected = currentTab == navItem.menuItemType,
                onClick = { onTabPressed(navItem.menuItemType) },
                icon = {
                    Icon(
                        painter = painterResource(id = navItem.icon),
                        contentDescription = stringResource(id = navItem.text)
                    )
                }
            )
        }
    }
}

@Composable
private fun BottomNavigationBar(
    currentTab: MenuItemType,
    onTabPressed: ((MenuItemType) -> Unit),
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier = modifier.fillMaxWidth()) {
        for (navItem in navigationItemContentList) {
            NavigationBarItem(
                selected = currentTab == navItem.menuItemType,
                onClick = { onTabPressed(navItem.menuItemType) },
                icon = {
                    Icon(
                        painter = painterResource(id = navItem.icon),
                        contentDescription = stringResource(id = navItem.text)
                    )
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NavigationDrawerContent(
    selectedDestination: MenuItemType,
    onTabPressed: ((MenuItemType) -> Unit),
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .wrapContentWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colors.background)
            .padding(12.dp)
    ) {
        for (navItem in navigationItemContentList) {
            NavigationDrawerItem(
                selected = selectedDestination == navItem.menuItemType,
                label = {
                    Text(
                        text = stringResource(id = navItem.text),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                },
                icon = {
                    Icon(
                        painter = painterResource(id = navItem.icon),
                        contentDescription = stringResource(id = navItem.text)
                    )
                },
                colors = NavigationDrawerItemDefaults.colors(
                    unselectedContainerColor = Color.Transparent
                ),
                onClick = { onTabPressed(navItem.menuItemType) }
            )
        }
    }
}