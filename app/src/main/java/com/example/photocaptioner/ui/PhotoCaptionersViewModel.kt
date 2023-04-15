package com.example.photocaptioner.ui

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import com.example.photocaptioner.R
import com.example.photocaptioner.data.Datasource
import com.example.photocaptioner.model.Album
import com.example.photocaptioner.model.MapsPhoto
import com.example.photocaptioner.model.Photo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PhotoCaptionersViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(
        PhotoCaptionerUiState(
            albumList = Datasource.getAlbums(),
            searchedPhotos = listOf(
                Pair(false, MapsPhoto("https://picsum.photos/200/300")),
                Pair(false, MapsPhoto("https://picsum.photos/seed/picsum/200/300")),
                Pair(false, MapsPhoto("https://picsum.photos/id/237/200/300")),
                Pair(false, MapsPhoto("https://picsum.photos/id/238/200/300")),
                Pair(false, MapsPhoto("https://picsum.photos/id/239/200/300"))
            )
        )
    )
    val uiState: StateFlow<PhotoCaptionerUiState> = _uiState.asStateFlow()

    fun navigateToScreen(@StringRes newScreen: Int, canNavigateBack: Boolean) {
        _uiState.update {
            it.copy(
                canNavigateBack = canNavigateBack,
                currentScreen = newScreen
            )
        }
    }

    fun selectAlbum(album: Album) {
        _uiState.update {
            it.copy(
                selectedAlbum = album
            )
        }
    }

    fun updateSearchValue(newSearchValue: String) {
        _uiState.update {
            it.copy(
                searchValue = newSearchValue
            )
        }
    }

    fun selectImage(mapsPhotoIndex: Int) {
        val photos:  List<Pair<Boolean, MapsPhoto>> = _uiState.value.searchedPhotos.mapIndexed { index, it ->
            if (index == mapsPhotoIndex) {
                Pair(!it.first, it.second)
            } else {
                Pair(it.first, it.second)
            }
        }
        _uiState.update {
            it.copy(
                searchedPhotos = photos
            )
        }
    }

    fun updateNewTitle(newTitle: String) {
        _uiState.update {
            it.copy(
                newAlbumName = newTitle
            )
        }
    }

    fun updateNewDescription(newDescription: String) {
        _uiState.update {
            it.copy(
                newAlbumDescription = newDescription
            )
        }
    }

    fun addNewAlbum() {
        val newAlbum = Datasource.defaultAlbum.copy()
        _uiState.update {
            it.copy(
                albumList = it.albumList + newAlbum,
                newAlbumName = "",
                newAlbumDescription = "",
                searchedPhotos = emptyList(),
                searchValue = ""
            )
        }
    }

    fun updateSelectedAlbumTitle(newTitle: String) {
        _uiState.update {
            it.copy(
                newAlbumName = newTitle
            )
        }
    }

    fun updateSelectedAlbumDescription(newDescription: String) {
        _uiState.update {
            it.copy(
                newAlbumDescription = newDescription
            )
        }
    }

    fun saveSelectedAlbum() {
        //  TODO: save the album when working with database
    }

    fun updateSelectedPhotoDescription(newDescription: String) {
        _uiState.update {
            it.copy(
                newPhotoDescription = newDescription
            )
        }
    }

    fun saveSelectedPhoto() {
        //  TODO: save the photo when working with database
    }

    fun selectPhoto(photo: Photo) {
        _uiState.update {
            it.copy(
                selectedPhoto = photo
                //TODO: change selected photo description when working with database
            )
        }
    }


}

data class PhotoCaptionerUiState(
    val canNavigateBack: Boolean = false,
    @StringRes val currentScreen: Int = R.string.start,
    val recentlyEdited: Photo = Datasource.defaultPhoto,
    val albumList: List<Album> = emptyList(),
    val selectedAlbum: Album = Datasource.defaultAlbum,
    val searchValue: String = "",
    val searchedPhotos: List<Pair<Boolean, MapsPhoto>> = emptyList(),
    val newPhotos: List<Photo> = emptyList(),
    val newAlbumName: String = "",
    val newAlbumDescription: String = "",
    val selectedPhoto: Photo = Datasource.defaultPhoto,
    val newPhotoDescription: String = ""
)