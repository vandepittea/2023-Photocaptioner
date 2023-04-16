package com.example.photocaptioner.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.photocaptioner.PhotoCaptionerScreen
import com.example.photocaptioner.model.Album
import com.example.photocaptioner.model.MapsPhoto
import com.example.photocaptioner.model.Photo

@Composable
fun ResponsiveHomeScreen(
    navController: NavHostController,
    onStartUpClick: () -> Unit,
    onTakePictureClick: () -> Unit,
    onGoToAlbumsClick: () -> Unit,
    recentlyEdited: Photo,
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
    modifier: Modifier = Modifier
){
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
                recentlyEdited = recentlyEdited
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
            AlbumDetailScreen(
                album = detailedAlbum,
                onDownloadClick = onDownloadClick,
                onEditClick = onEditClick,
                onAddClick = onAddPictureClick,
                onShareClick = onShareClick,
                onPhotoClick = onPhotoClick
            )
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
                onChooseCamera = { /*TODO*/ },
                onChooseGallery = { /*TODO*/ },
                onChooseMaps = { /*TODO*/ },
                onAddNewAlbum = onAddNewAlbum
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