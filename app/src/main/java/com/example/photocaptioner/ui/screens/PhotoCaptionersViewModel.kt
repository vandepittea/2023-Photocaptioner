package com.example.photocaptioner.ui.screens

import android.util.Log
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.photocaptioner.PhotoCaptionerApplication
import com.example.photocaptioner.data.Datasource
import com.example.photocaptioner.data.MenuItemType
import com.example.photocaptioner.data.UnsplashRepository
import com.example.photocaptioner.model.Album
import com.example.photocaptioner.model.MapsPhoto
import com.example.photocaptioner.model.Photo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PhotoCaptionersViewModel(private val unsplashRepository: UnsplashRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(
        PhotoCaptionerUiState(
            albumList = Datasource.getAlbums(),
            searchedPhotos = emptyList()
        )
    )
    val uiState: StateFlow<PhotoCaptionerUiState> = _uiState.asStateFlow()

    fun updateCurrentMenuItem(menuItemType: MenuItemType) {
        _uiState.update {
            it.copy(
                currentMenuItem = menuItemType
            )
        }
    }

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

    fun searchImages(query: String) {
        viewModelScope.launch {
            try {
                val images = unsplashRepository.searchImages(query)
                _uiState.update {
                    it.copy(searchedPhotos = images.map { imageUrl -> Pair(false, MapsPhoto(imageUrl)) })
                }
            } catch (e: Exception) {
                // Handle error
            }
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

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as PhotoCaptionerApplication)
                val unsplashRepository = application.container.provideUnsplashRepository()
                PhotoCaptionersViewModel(unsplashRepository = unsplashRepository)
            }
        }
    }
}