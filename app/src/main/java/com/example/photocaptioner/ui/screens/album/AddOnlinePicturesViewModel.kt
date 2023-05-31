package com.example.photocaptioner.ui.screens.album

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photocaptioner.data.UnsplashRepository
import com.example.photocaptioner.data.database.AlbumsRepository
import com.example.photocaptioner.model.Photo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class AddOnlinePicturesViewModel(
    private val savedStateHandle: SavedStateHandle,
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
            try {
                val images = unsplashRepository.searchImages(query)
                addOnlinePicturesUiState.update {
                    it.copy(searchedPhotos = images.map { imageUrl -> Pair(false, Photo(
                        0,
                        "",
                        "",
                        LocalDate.now(),
                        imageUrl,
                        albumId
                    )) })
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun selectImage(photoIndex: Int) {
        val photos: List<Pair<Boolean, Photo>> = addOnlinePicturesUiState.value.searchedPhotos.mapIndexed { index, it ->
            if (index == photoIndex) {
                Pair(true, it.second)
            } else {
                Pair(false, it.second)
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
        if (albumId == (-1).toLong()) {
            savedStateHandle["newPhotos"] = newPhotos
        } else {
            viewModelScope.launch {
                newPhotos.forEach {
                    albumsRepository.insertPhoto(it)
                }
            }
        }
    }
}

data class AddOnlinePicturesUiState(
    val searchValue: String = "",
    val searchedPhotos: List<Pair<Boolean, Photo>> = emptyList()
)