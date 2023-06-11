package com.example.photocaptioner.ui.screens.pictures

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photocaptioner.data.api.UnsplashRepository
import com.example.photocaptioner.data.database.AlbumsRepository
import com.example.photocaptioner.model.Photo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class AddOnlinePicturesViewModel(
    savedStateHandle: SavedStateHandle,
    private val albumsRepository: AlbumsRepository,
    private val unsplashRepository: UnsplashRepository
) : ViewModel() {
    private val albumId: Long = checkNotNull(savedStateHandle[AddOnlinePicturesDestination.albumIdArg])

    val addOnlinePicturesUiState = MutableStateFlow(
            AddOnlinePicturesUiState()
    )


    fun updateSearchValue(newSearchValue: String) {
        addOnlinePicturesUiState.update {
            it.copy(
                searchValue = newSearchValue
            )
        }
    }

    fun searchImages(query: String) {
        viewModelScope.launch {
            val images = unsplashRepository.searchImages(query)
            addOnlinePicturesUiState.update {
                it.copy(searchedPhotos = images.map { imageUrl -> Pair(false, Photo(
                    0,
                    "",
                    LocalDateTime.now(),
                    imageUrl,
                    albumId
                )) })
            }
        }
    }

    fun selectImage(photoIndex: Int) {
        val photos: List<Pair<Boolean, Photo>> = addOnlinePicturesUiState.value.searchedPhotos.mapIndexed { index, it ->
            if (index == photoIndex) {
                Pair(!it.first, it.second)
            } else {
                Pair(it.first, it.second)
            }
        }
        addOnlinePicturesUiState.update {
            it.copy(
                searchedPhotos = photos
            )
        }
    }

    fun addPhotosToAlbum() {
        val newPhotos: List<Photo> = addOnlinePicturesUiState.value.searchedPhotos.filter { it.first }.map { it.second }
        viewModelScope.launch {
            newPhotos.forEach {
                albumsRepository.insertPhoto(it)
            }
        }
    }
}

data class AddOnlinePicturesUiState(
    val searchValue: String = "",
    val searchedPhotos: List<Pair<Boolean, Photo>> = emptyList()
)