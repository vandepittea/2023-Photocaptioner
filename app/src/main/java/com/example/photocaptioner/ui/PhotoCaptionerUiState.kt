package com.example.photocaptioner.ui

import androidx.annotation.StringRes
import com.example.photocaptioner.R
import com.example.photocaptioner.data.Datasource
import com.example.photocaptioner.data.MenuItemType
import com.example.photocaptioner.model.Album
import com.example.photocaptioner.model.MapsPhoto
import com.example.photocaptioner.model.Photo

data class PhotoCaptionerUiState(
    val currentMenuItem: MenuItemType = MenuItemType.Home,
    val canNavigateBack: Boolean = false,
    @StringRes val currentScreen: Int = R.string.start,
    val recentlyEdited: Album = Datasource.defaultAlbum,
    val albumList: List<Album> = emptyList(),
    val selectedAlbum: Album = Datasource.defaultAlbum,
    val searchValue: String = "",
    val searchedPhotos: List<Pair<Boolean, MapsPhoto>> = emptyList(),
    val newPhotos: List<Photo> = emptyList(),
    val newAlbumName: String = "",
    val newAlbumDescription: String = "",
    val selectedPhoto: Photo = Datasource.defaultPhoto,
    val newPhotoDescription: String = "",
    val isEditingAlbum: Boolean = false
)