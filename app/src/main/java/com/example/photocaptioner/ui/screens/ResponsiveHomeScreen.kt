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
import com.example.photocaptioner.model.Album
import com.example.photocaptioner.model.MapsPhoto
import com.example.photocaptioner.model.NavigationItemContent
import com.example.photocaptioner.model.Photo
import com.example.photocaptioner.ui.utils.PhotoCaptionerNavigationType
import androidx.compose.ui.unit.dp
import com.example.photocaptioner.R
import com.example.photocaptioner.data.Datasource.navigationItemContentList
import com.example.photocaptioner.model.AlbumWithImages
import com.example.photocaptioner.ui.AlbumDetailAndAlbumEditScreen
import com.example.photocaptioner.ui.AlbumDetailAndPhotoEditScreen
import com.example.photocaptioner.ui.AlbumDetailAndPhotoSourceChooserScreen
import com.example.photocaptioner.ui.AlbumsAndAlbumDetailScreen
import com.example.photocaptioner.ui.screens.album.AlbumsScreen
import com.example.photocaptioner.ui.ChoosePicturesSourceScreen
import com.example.photocaptioner.ui.screens.album.EditAlbumScreen
import com.example.photocaptioner.ui.screens.album.EditPhotoScreen
import com.example.photocaptioner.ui.HomeScreen
import com.example.photocaptioner.ui.PhotoCaptionerScreen
import com.example.photocaptioner.ui.StartUpScreen
import com.example.photocaptioner.ui.screens.album.AddAlbumsScreen
import com.example.photocaptioner.ui.screens.album.AddOnlinePicturesScreen
import com.example.photocaptioner.ui.screens.album.AlbumDetailScreen
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
    onAlbumClick: (AlbumWithImages) -> Unit,
    onEditClick: () -> Unit,
    onAddPictureClick: () -> Unit,
    onPhotoClick: (Photo) -> Unit,
    onChooseCamera: () -> Unit,
    onChooseGallery: () -> Unit,
    onChooseMaps: () -> Unit,
    navigateBack: () -> Unit,
    onPhotoSave: () -> Unit,
    modifier: Modifier = Modifier,
    contentType: PhotoCaptionerContentType,
    onRecentlyEditedClick: () -> Unit,
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
                    onPhotoSave = onPhotoSave,
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
                onPhotoSave = onPhotoSave,
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
    onAlbumClick: (AlbumWithImages) -> Unit,
    onEditClick: () -> Unit,
    onAddPictureClick: () -> Unit,
    onPhotoClick: (Photo) -> Unit,
    onChooseCamera: () -> Unit,
    onChooseGallery: () -> Unit,
    onChooseMaps: () -> Unit,
    navigateBack: () -> Unit,
    onPhotoSave: () -> Unit,
    modifier: Modifier = Modifier,
    contentType: PhotoCaptionerContentType,
    onRecentlyEditedClick: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = PhotoCaptionerScreen.Start.name,
        modifier = modifier
    ) {
        composable(PhotoCaptionerScreen.Start.name) {
            StartUpScreen(
                onButtonClick = onStartUpClick
            )
        }

        composable(PhotoCaptionerScreen.Home.name) {
            HomeScreen(
                onTakePictureClick = onTakePictureClick,
                onAlbumsClick = onGoToAlbumsClick,
                onRecentlyEditedClick = onRecentlyEditedClick
            )
        }

        composable(PhotoCaptionerScreen.Albums.name) {
            AlbumsScreen(
                onAddClick = onAddAlbumClick,
                onAlbumClick = onAlbumClick
            )
        }

        composable(PhotoCaptionerScreen.AlbumDetail.name) {
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

        composable(PhotoCaptionerScreen.ChoosePicturesSource.name) {
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

        composable(PhotoCaptionerScreen.AddPictures.name) {
            AddOnlinePicturesScreen()
        }

        composable(PhotoCaptionerScreen.AddAlbum.name) {
            AddAlbumsScreen(
                onChooseCamera = onChooseCamera,
                onChooseGallery = onChooseGallery,
                onChooseMaps = onChooseMaps,
                navigateBack = navigateBack,
                contentType = contentType
            )
        }

        composable(PhotoCaptionerScreen.EditAlbum.name) {
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

        composable(PhotoCaptionerScreen.EditPhoto.name) {
            if (contentType == PhotoCaptionerContentType.LIST_AND_DETAIL) {
                AlbumDetailAndPhotoEditScreen(
                    onEditClick = onEditClick,
                    onAddPictureClick = onAddPictureClick,
                    onPhotoClick = onPhotoClick,
                    onPhotoSave = onPhotoSave
                )
            } else {
                EditPhotoScreen(
                    navigateBack = onPhotoSave
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