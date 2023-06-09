package com.example.photocaptioner.ui.screens.album

import android.net.Uri
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photocaptioner.data.WorkManagerDownloadRepository
import com.example.photocaptioner.data.database.AlbumsRepository
import com.example.photocaptioner.model.Album
import com.example.photocaptioner.model.AlbumWithImages
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AlbumDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val albumsRepository: AlbumsRepository,
    private val workManagerDownloadRepository: WorkManagerDownloadRepository
) : ViewModel(){
    private val albumId: Long = checkNotNull(savedStateHandle[AlbumDetailDestination.albumIdArg])

    val albumDetailUiState: StateFlow<AlbumDetailUiState> =
        albumsRepository.getAlbum(albumId)
            .filterNotNull()
            .map {
                AlbumDetailUiState(albumDetails = it)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = AlbumDetailUiState()
            )

    fun downloadAlbum(uri: Uri?) {
        if (uri.toString().startsWith("content")) {
            viewModelScope.launch {
                workManagerDownloadRepository.downloadAlbum(albumId, uri)
            }
        }
    }

    fun shareAlbum() {
        //TODO: share album
    }
}

data class AlbumDetailUiState(
    val albumDetails: AlbumWithImages = AlbumWithImages()
)