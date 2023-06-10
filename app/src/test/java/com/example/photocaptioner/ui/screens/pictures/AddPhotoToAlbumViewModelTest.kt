package com.example.photocaptioner.ui.screens.pictures

import androidx.lifecycle.SavedStateHandle
import com.example.photocaptioner.data.database.TestAlbumsRepository
import com.example.photocaptioner.model.AlbumWithImages
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class AddPhotoToAlbumViewModelTest {

    private lateinit var viewModel: AddPhotoToAlbumViewModel
    private lateinit var albumsRepository: TestAlbumsRepository

    @Before
    fun setup() {
        albumsRepository = TestAlbumsRepository()
        Dispatchers.setMain(Dispatchers.Unconfined)
        val savedStateHandle = SavedStateHandle().apply {
            set(AddPhotoToAlbumDestination.photoIdArg, 1L)
        }
        viewModel = AddPhotoToAlbumViewModel(savedStateHandle, albumsRepository)
    }

    @Test
    fun selectAlbum() {
        var album: AlbumWithImages

        runBlocking { album = albumsRepository.getAlbums().first().first() }

        viewModel.selectAlbum(album)

        assert(viewModel.addPhotoToAlbumUiState.selectedAlbum == album)
    }


    @Test
    fun addPhotoToAlbum() = runBlocking {
        var album: AlbumWithImages

        runBlocking { album = albumsRepository.getAlbum(2).first() }

        viewModel.selectAlbum(album)
        viewModel.addPhotoToAlbum()

        assertEquals(album.album.id, viewModel.addPhotoToAlbumUiState.photo.albumId)
        assertEquals(viewModel.addPhotoToAlbumUiState.photo.albumId, albumsRepository.getPhoto(1L).first().albumId)
    }
}