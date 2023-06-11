package com.example.photocaptioner.ui.screens.album

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photocaptioner.data.worker.WorkManagerDownloadRepositoryImplementation
import com.example.photocaptioner.data.database.AlbumsRepository
import com.example.photocaptioner.intent.convertPhotoToFile
import com.example.photocaptioner.intent.getFileUri
import com.example.photocaptioner.model.AlbumWithImages
import com.example.photocaptioner.model.Photo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

class AlbumDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val albumsRepository: AlbumsRepository,
    private val workManagerDownloadRepository: WorkManagerDownloadRepositoryImplementation
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

    fun shareAlbum(context: Context) {
        val firstPhoto = albumDetailUiState.value.albumDetails.photos.firstOrNull()
        val albumName = albumDetailUiState.value.albumDetails.album.name

        if (firstPhoto != null) {
            var file: File

            runBlocking { file = convertPhotoToFile(context, firstPhoto) }

            val fileUri = file?.let { getFileUri(context, it) }

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "image/*"
                putExtra(Intent.EXTRA_STREAM, fileUri)
                putExtra(Intent.EXTRA_TEXT, "Check my album $albumName on PhotoCaptioner!")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            val chooserIntent = Intent.createChooser(shareIntent, "Share Image")
            chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(chooserIntent)
        } else {
            Toast.makeText(context, "Cannot share. The album '$albumName' has no images.", Toast.LENGTH_SHORT).show()
        }
    }

    fun deleteAlbum() {
        viewModelScope.launch {
            albumDetailUiState.value.albumDetails.photos.forEach { albumsRepository.deletePhoto(it.id) }
            albumsRepository.deleteAlbum(albumId)
        }
    }
}

data class AlbumDetailUiState(
    val albumDetails: AlbumWithImages = AlbumWithImages()
)