package com.example.photocaptioner.ui

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.photocaptioner.PhotoCaptionerApplication
import com.example.photocaptioner.PhotoCaptionerApplicationHolder
import com.example.photocaptioner.ui.screens.album.AddAlbumViewModel
import com.example.photocaptioner.ui.screens.album.AddOnlinePicturesViewModel
import com.example.photocaptioner.ui.screens.album.AlbumDetailViewModel
import com.example.photocaptioner.ui.screens.album.AlbumsViewModel
import com.example.photocaptioner.ui.screens.album.EditAlbumViewModel
import com.example.photocaptioner.ui.screens.home.HomeViewModel
import com.example.photocaptioner.ui.screens.pictures.ChoosePicturesSourceViewModel

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
                this.createSavedStateHandle()
            )
        }
    }
}