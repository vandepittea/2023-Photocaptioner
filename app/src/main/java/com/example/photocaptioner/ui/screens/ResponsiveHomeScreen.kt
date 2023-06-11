package com.example.photocaptioner.ui.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.*
import androidx.compose.material3.NavigationRailItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.photocaptioner.ui.screens.navigation.MenuItemType
import com.example.photocaptioner.ui.screens.navigation.NavigationItemContent
import com.example.photocaptioner.ui.utils.PhotoCaptionerNavigationType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.photocaptioner.R
import com.example.photocaptioner.data.Datasource.navigationItemContentList
import com.example.photocaptioner.ui.screens.album.AlbumsScreen
import com.example.photocaptioner.ui.screens.home.HomeDestination
import com.example.photocaptioner.ui.screens.album.EditAlbumScreen
import com.example.photocaptioner.ui.screens.home.HomeScreen
import com.example.photocaptioner.ui.screens.home.StartUpDestination
import com.example.photocaptioner.ui.screens.home.StartUpScreen
import com.example.photocaptioner.ui.screens.album.AddAlbumDestination
import com.example.photocaptioner.ui.screens.album.AddAlbumScreen
import com.example.photocaptioner.ui.screens.pictures.AddOnlinePicturesDestination
import com.example.photocaptioner.ui.screens.pictures.AddOnlinePicturesScreen
import com.example.photocaptioner.ui.screens.album.AlbumDetailDestination
import com.example.photocaptioner.ui.screens.album.AlbumDetailScreen
import com.example.photocaptioner.ui.screens.album.AlbumsDestination
import com.example.photocaptioner.ui.screens.album.EditAlbumDestination
import com.example.photocaptioner.ui.screens.pictures.AddPhotoToAlbumDestination
import com.example.photocaptioner.ui.screens.pictures.AddPhotoToAlbumScreen
import com.example.photocaptioner.ui.screens.pictures.CameraPage
import com.example.photocaptioner.ui.screens.pictures.CameraPageDestination
import com.example.photocaptioner.ui.screens.pictures.ChoosePicturesDestination
import com.example.photocaptioner.ui.screens.pictures.ChoosePicturesSourceScreen
import com.example.photocaptioner.ui.screens.pictures.EditPhotoDestination
import com.example.photocaptioner.ui.screens.pictures.EditPhotoScreen
import com.example.photocaptioner.ui.utils.PhotoCaptionerContentType

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResponsiveHomeScreen(
    navigationType: PhotoCaptionerNavigationType,
    navController: NavHostController,
    currentMenuItem: MenuItemType,
    onMenuItemPress: (NavigationItemContent) -> Unit,
    onStartUpClick: () -> Unit,
    onTakePictureClick: () -> Unit,
    onGoToAlbumsClick: () -> Unit,
    onAddAlbumClick: () -> Unit,
    onAlbumClick: (Long) -> Unit,
    onEditClick: (Long) -> Unit,
    onAddPictureClick: (albumId: Long, navBackRout: String) -> Unit,
    onPhotoClick: (albumId: Long, photoId: Long) -> Unit,
    onChooseCamera: (Long) -> Unit,
    onChooseMaps: (albumId: Long, navBackRout: String) -> Unit,
    navigateToAlbums: () -> Unit,
    navigateBack: (route: String, include: Boolean) -> Unit,
    modifier: Modifier = Modifier,
    contentType: PhotoCaptionerContentType,
    onRecentlyEditedClick: (Long) -> Unit,
    onTakePictureFromHome: (photoId: Long) -> Unit,
    bottomBarVisible: Boolean
) {
    Row(modifier = modifier.fillMaxSize()) {
        if (bottomBarVisible) {
            AnimatedVisibility(visible = navigationType == PhotoCaptionerNavigationType.NAVIGATION_RAIL) {
                val navigationRailContentDescription = stringResource(R.string.navigation_rail)
                NavigationRail(
                    currentTab = currentMenuItem,
                    onTabPressed = onMenuItemPress,
                    navigationItemContentList = navigationItemContentList,
                    modifier = Modifier.testTag(navigationRailContentDescription)
                )
            }
        }

        AnimatedVisibility(visible = navigationType == PhotoCaptionerNavigationType.PERMANENT_NAVIGATION_DRAWER) {
            val navigationDrawerContentDescription = stringResource(R.string.navigation_drawer)
            PermanentNavigationDrawer(
                drawerContent = {
                    if (bottomBarVisible) {
                        PermanentDrawerSheet(Modifier.width(240.dp)) {
                            NavigationDrawerContent(
                                selectedDestination = currentMenuItem,
                                onTabPressed = onMenuItemPress,
                                navigationItemContentList = navigationItemContentList
                            )
                        }
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
                    onChooseMaps = onChooseMaps,
                    onTakePictureFromHome = onTakePictureFromHome,
                    navigateToAlbums = navigateToAlbums,
                    navigateBack = navigateBack,
                    modifier = modifier,
                    contentType = contentType,
                    onRecentlyEditedClick = onRecentlyEditedClick,
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
                onChooseMaps = onChooseMaps,
                onTakePictureFromHome = onTakePictureFromHome,
                navigateToAlbums = navigateToAlbums,
                navigateBack = navigateBack,
                modifier = modifier.weight(1f),
                contentType = contentType,
                onRecentlyEditedClick = onRecentlyEditedClick,
            )

            if (bottomBarVisible) {
                Box {
                    this@Row.AnimatedVisibility(
                        visible = navigationType == PhotoCaptionerNavigationType.BOTTOM_NAVIGATION,
                        modifier = Modifier.align(Alignment.BottomCenter)
                    ) {
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
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
private fun InAppNavigation(
    navController: NavHostController,
    onStartUpClick: () -> Unit,
    onTakePictureClick: () -> Unit,
    onGoToAlbumsClick: () -> Unit,
    onAddAlbumClick: () -> Unit,
    onAlbumClick: (Long) -> Unit,
    onEditClick: (Long) -> Unit,
    onAddPictureClick: (albumId: Long, navBackRout: String) -> Unit,
    onPhotoClick: (albumId: Long, photoId: Long) -> Unit,
    onChooseCamera: (Long) -> Unit,
    onChooseMaps: (albumId: Long, navBackRout: String) -> Unit,
    navigateToAlbums: () -> Unit,
    navigateBack: (route: String, include: Boolean) -> Unit,
    modifier: Modifier = Modifier,
    contentType: PhotoCaptionerContentType,
    onRecentlyEditedClick: (Long) -> Unit,
    onTakePictureFromHome: (photoId: Long) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = StartUpDestination.route,
        modifier = modifier
    ) {
        composable(
            route = StartUpDestination.routeWithArgs,
            arguments = listOf(navArgument("title") { type = NavType.StringType })
        ) {
            StartUpScreen(
                onButtonClick = onStartUpClick
            )
        }

        composable(
            route = HomeDestination.routeWithArgs,
            arguments = listOf(navArgument("title") { type = NavType.StringType })
        ) {
            HomeScreen(
                onTakePictureClick = onTakePictureClick,
                onAlbumsClick = onGoToAlbumsClick,
                onRecentlyEditedClick = onRecentlyEditedClick
            )
        }

        composable(
            route = CameraPageDestination.routeWithArgs,
            arguments = listOf(
                navArgument(AlbumDetailDestination.albumIdArg) { type = NavType.LongType },
                navArgument("title") { type = NavType.StringType }
            )
        ) {
            CameraPage(
                onTakePictureFromHome = onTakePictureFromHome,
                navigateBack = navigateBack
            )
        }

        composable(
            route = AlbumsDestination.routeWithArgs,
            arguments = listOf(navArgument("title") { type = NavType.StringType })
        ) {
            AlbumsScreen(
                onAddClick = onAddAlbumClick,
                onAlbumClick = onAlbumClick
            )
        }

        composable(
            route = AlbumDetailDestination.routeWithArgs,
            arguments = listOf(
                navArgument(AlbumDetailDestination.albumIdArg) { type = NavType.LongType },
                navArgument("title") { type = NavType.StringType }
            )
        ) {
            if (contentType == PhotoCaptionerContentType.LIST_AND_DETAIL) {
                AlbumsAndAlbumDetailScreen(
                    onAddClick = onAddAlbumClick,
                    onAlbumClick = onAlbumClick,
                    onEditClick = onEditClick,
                    onAddPictureClick = { onAddPictureClick(it, AlbumDetailDestination.routeWithArgs) },
                    onPhotoClick = onPhotoClick,
                    navigateBack = navigateBack
                )
            } else {
                AlbumDetailScreen(
                    onEditClick = onEditClick,
                    onAddClick = { onAddPictureClick(it, AlbumDetailDestination.routeWithArgs) },
                    onPhotoClick = onPhotoClick,
                    navigateBack = navigateBack
                )
            }
        }

        composable(
            route = ChoosePicturesDestination.routeWithArgs,
            arguments = listOf(
                navArgument(ChoosePicturesDestination.albumIdArg) { type = NavType.LongType },
                navArgument(ChoosePicturesDestination.backNavigationDestinationRouteArg) { type = NavType.StringType },
                navArgument("title") { type = NavType.StringType }
            )
        ) {
            val backNavigationDestinationRoute =
                navController.currentBackStackEntry?.arguments?.getString("backNavigationDestinationRoute")?.replace(" ", "/")
                    ?: ""
            if (contentType == PhotoCaptionerContentType.LIST_AND_DETAIL) {
                AlbumDetailAndPhotoSourceChooserScreen(
                    onEditClick = onEditClick,
                    onAddPictureClick = { onAddPictureClick(it, AlbumDetailDestination.routeWithArgs) },
                    onPhotoClick = onPhotoClick,
                    onChooseCamera = onChooseCamera,
                    onChooseMaps = onChooseMaps,
                    navigateBack = navigateBack
                )
            } else {
                ChoosePicturesSourceScreen(
                    onChooseCamera = onChooseCamera,
                    onChooseMaps = { onChooseMaps(it, backNavigationDestinationRoute) },
                    navigateBack = navigateBack
                )
            }
        }

        composable(
            route = AddOnlinePicturesDestination.routeWithArgs,
            arguments = listOf(
                navArgument(AddOnlinePicturesDestination.albumIdArg) { type = NavType.LongType },
                navArgument(AddOnlinePicturesDestination.backNavigationDestinationRouteArg) { type = NavType.StringType },
                navArgument("title") { type = NavType.StringType }
            )
        ) {
            AddOnlinePicturesScreen(
                backNavigationDestinationRoute = it.arguments?.getString(AddOnlinePicturesDestination.backNavigationDestinationRouteArg)?.replace(" ", "/") ?: "",
                navigateBack = navigateBack
            )
        }

        composable(
            route = AddAlbumDestination.routeWithArgs,
            arguments = listOf(
                navArgument(AddAlbumDestination.albumIdArg) { type = NavType.LongType },
                navArgument("title") { type = NavType.StringType }
            )
        ) {
            AddAlbumScreen(
                onChooseCamera = onChooseCamera,
                onChooseMaps = { onChooseMaps(it, AddAlbumDestination.routeWithArgs) },
                navigateToAlbums = navigateToAlbums,
                navigateBack = navigateBack,
                contentType = contentType
            )
        }

        composable(
            route = EditAlbumDestination.routeWithArgs,
            arguments = listOf(
                navArgument(EditAlbumDestination.albumIdArg) { type = NavType.LongType },
                navArgument("title") { type = NavType.StringType }
            )
        ) {
            if (contentType == PhotoCaptionerContentType.LIST_AND_DETAIL) {
                AlbumDetailAndAlbumEditScreen(
                    onEditClick = onEditClick,
                    onAddPictureClick = { onAddPictureClick(it, AlbumDetailDestination.routeWithArgs) },
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
            arguments = listOf(
                navArgument(EditPhotoDestination.albumIdArg) { type = NavType.LongType },
                navArgument(EditPhotoDestination.photoIdArg) { type = NavType.LongType },
                navArgument("title") { type = NavType.StringType }
            )
        ) {
            if (contentType == PhotoCaptionerContentType.LIST_AND_DETAIL) {
                AlbumDetailAndPhotoEditScreen(
                    onEditClick = onEditClick,
                    onAddPictureClick = { onAddPictureClick(it, AlbumDetailDestination.routeWithArgs) },
                    onPhotoClick = onPhotoClick,
                    navigateBack = navigateBack
                )
            } else {
                EditPhotoScreen(
                    navigateBack = navigateBack
                )
            }
        }

        composable(
            route = AddPhotoToAlbumDestination.routeWithArgs,
            arguments = listOf(
                navArgument(AddPhotoToAlbumDestination.photoIdArg) { type = NavType.LongType },
                navArgument("title") { type = NavType.StringType }
            )
        ) {
            AddPhotoToAlbumScreen(
                navigateBack = navigateBack,
                onNewAlbum = onAddAlbumClick
            )
        }
    }
}

@Composable
private fun NavigationRail(
    currentTab: MenuItemType,
    onTabPressed: ((NavigationItemContent) -> Unit),
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier
) {
    NavigationRail(modifier = modifier.fillMaxHeight()) {
        for (navItem in navigationItemContentList) {
            NavigationRailItem(
                selected = currentTab == navItem.menuItemType,
                onClick = { onTabPressed(navItem) },
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
    onTabPressed: ((NavigationItemContent) -> Unit),
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        for (navItem in navigationItemContentList) {
            NavigationBarItem(
                selected = currentTab == navItem.menuItemType,
                onClick = { onTabPressed(navItem) },
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
    onTabPressed: ((NavigationItemContent) -> Unit),
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
                onClick = { onTabPressed(navItem) }
            )
        }
    }
}