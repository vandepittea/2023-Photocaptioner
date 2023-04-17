package com.example.photocaptioner.ui

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
import com.example.photocaptioner.PhotoCaptionerScreen
import com.example.photocaptioner.data.MenuItemType
import com.example.photocaptioner.model.Album
import com.example.photocaptioner.model.MapsPhoto
import com.example.photocaptioner.model.NavigationItemContent
import com.example.photocaptioner.model.Photo
import com.example.photocaptioner.ui.utils.PhotoCaptionerNavigationType
import androidx.compose.ui.unit.dp
import com.example.photocaptioner.R
import com.example.photocaptioner.data.Datasource.navigationItemContentList
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
    recentlyEdited: Album,
    albumList: List<Album> = emptyList(),
    onAddAlbumClick: () -> Unit,
    onAlbumClick: (Album) -> Unit,
    detailedAlbum: Album,
    onDownloadClick: () -> Unit,
    onEditClick: () -> Unit,
    onAddPictureClick: () -> Unit,
    onShareClick: () -> Unit,
    onPhotoClick: (Photo) -> Unit,
    onChooseCamera: () -> Unit,
    onChooseGallery: () -> Unit,
    onChooseMaps: () -> Unit,
    searchValue: String,
    searchedPhotos: List<Pair<Boolean, MapsPhoto>>,
    onSearchChanged: (String) -> Unit,
    onImageSelected: (Int) -> Unit,
    onPictureUploadButtonClick: () -> Unit,
    newPhotos: List<Photo> = emptyList(),
    newTitle: String,
    newDescription: String,
    onAlbumTitleAdd: (String) -> Unit,
    onAlbumDescriptionAdd: (String) -> Unit,
    onAddNewAlbum: () -> Unit,
    albumToEdit: Album,
    onAlbumTitleChange: (String) -> Unit,
    onAlbumDescriptionChange: (String) -> Unit,
    onAlbumSave: () -> Unit,
    photoToEdit: Photo,
    photoDescriptionToEdit: String,
    onPhotoDescriptionChange: (String) -> Unit,
    onPhotoSave: () -> Unit,
    modifier: Modifier = Modifier,
    contentType: PhotoCaptionerContentType,
    isEditingAlbum: Boolean
){
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
            ){
                InAppNavigation(
                    navController,
                    onStartUpClick,
                    onTakePictureClick,
                    onGoToAlbumsClick,
                    recentlyEdited,
                    albumList,
                    onAddAlbumClick,
                    onAlbumClick,
                    detailedAlbum,
                    onDownloadClick,
                    onEditClick,
                    onAddPictureClick,
                    onShareClick,
                    onPhotoClick,
                    onChooseCamera,
                    onChooseGallery,
                    onChooseMaps,
                    searchValue,
                    searchedPhotos,
                    onSearchChanged,
                    onImageSelected,
                    onPictureUploadButtonClick,
                    newPhotos,
                    newTitle,
                    newDescription,
                    onAlbumTitleAdd,
                    onAlbumDescriptionAdd,
                    onAddNewAlbum,
                    albumToEdit,
                    onAlbumTitleChange,
                    onAlbumDescriptionChange,
                    onAlbumSave,
                    photoToEdit,
                    photoDescriptionToEdit,
                    onPhotoDescriptionChange,
                    onPhotoSave,
                    modifier,
                    contentType,
                    isEditingAlbum
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            InAppNavigation(
                navController,
                onStartUpClick,
                onTakePictureClick,
                onGoToAlbumsClick,
                recentlyEdited,
                albumList,
                onAddAlbumClick,
                onAlbumClick,
                detailedAlbum,
                onDownloadClick,
                onEditClick,
                onAddPictureClick,
                onShareClick,
                onPhotoClick,
                onChooseCamera,
                onChooseGallery,
                onChooseMaps,
                searchValue,
                searchedPhotos,
                onSearchChanged,
                onImageSelected,
                onPictureUploadButtonClick,
                newPhotos,
                newTitle,
                newDescription,
                onAlbumTitleAdd,
                onAlbumDescriptionAdd,
                onAddNewAlbum,
                albumToEdit,
                onAlbumTitleChange,
                onAlbumDescriptionChange,
                onAlbumSave,
                photoToEdit,
                photoDescriptionToEdit,
                onPhotoDescriptionChange,
                onPhotoSave,
                modifier,
                contentType,
                isEditingAlbum
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
    recentlyEdited: Album,
    albumList: List<Album> = emptyList(),
    onAddAlbumClick: () -> Unit,
    onAlbumClick: (Album) -> Unit,
    detailedAlbum: Album,
    onDownloadClick: () -> Unit,
    onEditClick: () -> Unit,
    onAddPictureClick: () -> Unit,
    onShareClick: () -> Unit,
    onPhotoClick: (Photo) -> Unit,
    onChooseCamera: () -> Unit,
    onChooseGallery: () -> Unit,
    onChooseMaps: () -> Unit,
    searchValue: String,
    searchedPhotos: List<Pair<Boolean, MapsPhoto>>,
    onSearchChanged: (String) -> Unit,
    onImageSelected: (Int) -> Unit,
    onPictureUploadButtonClick: () -> Unit,
    newPhotos: List<Photo> = emptyList(),
    newTitle: String,
    newDescription: String,
    onAlbumTitleAdd: (String) -> Unit,
    onAlbumDescriptionAdd: (String) -> Unit,
    onAddNewAlbum: () -> Unit,
    albumToEdit: Album,
    onAlbumTitleChange: (String) -> Unit,
    onAlbumDescriptionChange: (String) -> Unit,
    onAlbumSave: () -> Unit,
    photoToEdit: Photo,
    photoDescriptionToEdit: String,
    onPhotoDescriptionChange: (String) -> Unit,
    onPhotoSave: () -> Unit,
    modifier: Modifier = Modifier,
    contentType: PhotoCaptionerContentType,
    isEditingAlbum: Boolean
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
                recentlyEdited = recentlyEdited,
                contentType = contentType
            )
        }

        composable(PhotoCaptionerScreen.Albums.name) {
            AlbumsScreen(
                albumList = albumList,
                onAddClick = onAddAlbumClick,
                onAlbumClick = onAlbumClick
            )
        }

        composable(PhotoCaptionerScreen.AlbumDetail.name) {
            if (contentType == PhotoCaptionerContentType.LIST_AND_DETAIL) {
                AlbumDetailResponsiveScreenChooser(
                    detailedAlbum = detailedAlbum,
                    onDownloadClick = onDownloadClick,
                    onEditClick = onEditClick,
                    onAddPictureClick = onAddPictureClick,
                    onShareClick = onShareClick,
                    onPhotoClick = onPhotoClick,
                    onAlbumTitleChange = onAlbumTitleChange,
                    onAlbumDescriptionChange = onAlbumDescriptionChange,
                    onAlbumSave = onAlbumSave,
                    isEditingAlbum = isEditingAlbum
                )
            } else {
                AlbumDetailScreen(
                    album = detailedAlbum,
                    onDownloadClick = onDownloadClick,
                    onEditClick = onEditClick,
                    onAddClick = onAddPictureClick,
                    onShareClick = onShareClick,
                    onPhotoClick = onPhotoClick
                )
            }
        }

        composable(PhotoCaptionerScreen.ChoosePicturesSource.name) {
            ChoosePicturesSourceScreen(
                onChooseCamera = onChooseCamera,
                onChooseGallery = onChooseGallery,
                onChooseMaps = onChooseMaps
            )
        }

        composable(PhotoCaptionerScreen.AddPictures.name) {
            AddPicturesScreen(
                searchValue = searchValue,
                searchedPhotos = searchedPhotos,
                onSearchChanged = onSearchChanged,
                onImageSelected = onImageSelected,
                onUploadButtonClick = onPictureUploadButtonClick
            )
        }

        composable(PhotoCaptionerScreen.AddAlbum.name) {
            AddAlbumsScreen(
                newPhotos = newPhotos,
                newTitle = newTitle,
                newDescription = newDescription,
                onAlbumTitleChange = onAlbumTitleAdd,
                onAlbumDescriptionChange = onAlbumDescriptionAdd,
                onChooseCamera = onChooseCamera,
                onChooseGallery = onChooseGallery,
                onChooseMaps = onChooseMaps,
                onAddNewAlbum = onAddNewAlbum,
                contentType = contentType
            )
        }

        composable(PhotoCaptionerScreen.EditAlbum.name) {
            EditAlbumScreen(
                albumToEdit = albumToEdit,
                onAlbumTitleChange = onAlbumTitleChange,
                onAlbumDescriptionChange = onAlbumDescriptionChange,
                onSave = onAlbumSave
            )
        }

        composable(PhotoCaptionerScreen.EditPhoto.name) {
            EditPhotoScreen(
                photoToEdit = photoToEdit,
                description = photoDescriptionToEdit,
                onPhotoDescriptionChange = onPhotoDescriptionChange,
                onSave = onPhotoSave
            )
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