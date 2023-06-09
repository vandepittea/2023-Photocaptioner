package com.example.photocaptioner.ui.screens.album

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photocaptioner.data.WorkManagerDownloadRepository
import com.example.photocaptioner.data.database.AlbumsRepository
import com.example.photocaptioner.model.AlbumWithImages
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.*

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

    fun shareAlbum(context: Context) {
        val firstImageFilePath = albumDetailUiState.value.albumDetails.photos.firstOrNull()?.filePath
        val sharedImageFilePath = firstImageFilePath?.let { copyImageToPublicLocation(it) }

        if (sharedImageFilePath != null) {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "image/jpeg"
                putExtra(Intent.EXTRA_STREAM, Uri.parse("file://$sharedImageFilePath"))
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            context.startActivity(Intent.createChooser(shareIntent, "Share Image"))
        } else {
            // Handle the case where the image copy failed
        }
    }

    private fun copyImageToPublicLocation(sourceFilePath: String): String? {
        val destinationFile = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "shared_image.jpg")

        try {
            val inputStream = FileInputStream(File(sourceFilePath))
            val outputStream = FileOutputStream(destinationFile)
            val buffer = ByteArray(1024)
            var read: Int
            while (inputStream.read(buffer).also { read = it } != -1) {
                outputStream.write(buffer, 0, read)
            }
            outputStream.flush()
            outputStream.close()
            inputStream.close()

            return destinationFile.absolutePath
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

}

data class AlbumDetailUiState(
    val albumDetails: AlbumWithImages = AlbumWithImages()
)