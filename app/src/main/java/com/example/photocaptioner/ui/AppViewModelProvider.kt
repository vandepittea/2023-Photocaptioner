package com.example.photocaptioner.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.photocaptioner.PhotoCaptionerApplication
import com.example.photocaptioner.ui.screens.album.AddAlbumViewModel
import com.example.photocaptioner.ui.screens.album.AddOnlinePicturesViewModel
import com.example.photocaptioner.ui.screens.album.AlbumDetailViewModel
import com.example.photocaptioner.ui.screens.album.AlbumsViewModel
import com.example.photocaptioner.ui.screens.album.EditAlbumViewModel
import com.example.photocaptioner.ui.screens.home.HomeViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(
                PhotoCaptionerApplication().container.provideAlbumsRepository()
            )
        }
        initializer {
            AlbumsViewModel(
                PhotoCaptionerApplication().container.provideAlbumsRepository()
            )
        }
        initializer {
            AddAlbumViewModel(
                PhotoCaptionerApplication().container.provideAlbumsRepository()
            )
        }
        initializer {
            AddOnlinePicturesViewModel(
                PhotoCaptionerApplication().container.provideAlbumsRepository(),
                PhotoCaptionerApplication().container.provideUnsplashRepository()
            )
        }
        initializer {
            AlbumDetailViewModel(
                PhotoCaptionerApplication().container.provideAlbumsRepository()
            )
        }
        initializer {
            EditAlbumViewModel(
                PhotoCaptionerApplication().container.provideAlbumsRepository()
            )
        }
    }
}

fun CreationExtras.PhotoCaptionerApplication(): PhotoCaptionerApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PhotoCaptionerApplication)