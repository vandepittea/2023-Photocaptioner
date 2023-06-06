package com.example.photocaptioner.ui

import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.photocaptioner.PhotoCaptionerApplicationHolder
import com.example.photocaptioner.ui.screens.album.*
import com.example.photocaptioner.ui.screens.home.HomeViewModel
import com.example.photocaptioner.ui.screens.pictures.CameraPageViewModel
import com.example.photocaptioner.ui.screens.pictures.ChoosePicturesSourceViewModel
import com.example.photocaptioner.ui.screens.pictures.EditPhotoViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(
                PhotoCaptionerApplicationHolder.instance.container.provideAlbumsRepository()
            )
        }
        initializer {
            AlbumsViewModel(
                PhotoCaptionerApplicationHolder.instance.container.provideAlbumsRepository()
            )
        }
        initializer {
            AddAlbumViewModel(
                PhotoCaptionerApplicationHolder.instance.container.provideAlbumsRepository()
            )
        }
        initializer {
            AddOnlinePicturesViewModel(
                this.createSavedStateHandle(),
                PhotoCaptionerApplicationHolder.instance.container.provideAlbumsRepository(),
                PhotoCaptionerApplicationHolder.instance.container.provideUnsplashRepository()
            )
        }
        initializer {
            AlbumDetailViewModel(
                this.createSavedStateHandle(),
                PhotoCaptionerApplicationHolder.instance.container.provideAlbumsRepository()
            )
        }
        initializer {
            EditAlbumViewModel(
                this.createSavedStateHandle(),
                PhotoCaptionerApplicationHolder.instance.container.provideAlbumsRepository()
            )
        }
        initializer {
            ChoosePicturesSourceViewModel(
                this.createSavedStateHandle(),
                PhotoCaptionerApplicationHolder.instance.container.provideAlbumsRepository()
            )
        }
        initializer {
            EditPhotoViewModel(
                this.createSavedStateHandle(),
                PhotoCaptionerApplicationHolder.instance.container.provideAlbumsRepository()
            )
        }
        initializer {
            CameraPageViewModel(
                this.createSavedStateHandle(),
                PhotoCaptionerApplicationHolder.instance.container.provideAlbumsRepository()
            )
        }
    }
}