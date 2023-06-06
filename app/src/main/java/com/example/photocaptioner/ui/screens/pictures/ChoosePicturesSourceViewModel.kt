package com.example.photocaptioner.ui.screens.pictures

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.photocaptioner.data.database.AlbumsRepository
import com.example.photocaptioner.model.Photo

class ChoosePicturesSourceViewModel(
    savedStateHandle: SavedStateHandle,
    private val albumsRepository: AlbumsRepository
) : ViewModel() {
    val albumId: Long = checkNotNull(savedStateHandle[ChoosePicturesDestination.albumIdArg])

    suspend fun onGalleryResult(uriList: List<Uri>) {
        uriList.forEach {
            val newPhoto: Photo = Photo(
                albumId = albumId,
                filePath = it.toString()
            )
            albumsRepository.insertPhoto(newPhoto)
        }
    }
}