package com.example.photocaptioner.ui.screens.album

import androidx.lifecycle.SavedStateHandle
import com.example.photocaptioner.data.database.TestAlbumsRepository
import com.example.photocaptioner.model.Album
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class AddAlbumViewModelTest {

    private lateinit var viewModel: AddAlbumViewModel
    private lateinit var albumsRepository: TestAlbumsRepository

    @Before
    fun setup() {
        albumsRepository = TestAlbumsRepository()
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = AddAlbumViewModel(albumsRepository)
    }

    @Test
    fun updateAlbumTitleAndAlbumDescriptionUiStateEntryValidTrue() {
        val expectedDescription = "Updated Description"
        val expectedTitle = "Updated Title"

        viewModel.updateAlbumTitleUiState(expectedTitle)
        viewModel.updateAlbumDescriptionUiState(expectedDescription)

        assertEquals(expectedTitle, viewModel.addAlbumUiState.albumDetails.album.name)
        assertEquals(expectedDescription, viewModel.addAlbumUiState.albumDetails.album.description)
        assertEquals(true, viewModel.addAlbumUiState.isEntryValid)
    }

    /*@Test
    fun `saveItem inserts album and updates photos without album`() = runBlocking {
        val album = Album(name = "Test Album", description = "Test Description")
        viewModel.addAlbumUiState = viewModel.addAlbumUiState.copy(
            albumDetails = viewModel.addAlbumUiState.albumDetails.copy(album = album),
            isEntryValid = true
        )

        viewModel.saveItem { }

        val savedAlbum = albumsRepository.getAlbums().first()
        assertEquals(album, savedAlbum)

        val photos = albumsRepository.getPhotosWithoutAlbum().first()
        assertEquals(0, photos.size)
    }

    @Test
    fun `saveItem does not insert album and update photos without album if input is invalid`() = runBlocking {
        val album = Album(name = "", description = "")
        viewModel.addAlbumUiState = viewModel.addAlbumUiState.copy(
            albumDetails = viewModel.addAlbumUiState.albumDetails.copy(album = album),
            isEntryValid = false
        )

        viewModel.saveItem { }

        val savedAlbum = albumsRepository.getAlbums().first()
        assertEquals(null, savedAlbum)

        val photos = albumsRepository.getPhotosWithoutAlbum().first()
        assertEquals(0, photos.size)
    }*/
}
