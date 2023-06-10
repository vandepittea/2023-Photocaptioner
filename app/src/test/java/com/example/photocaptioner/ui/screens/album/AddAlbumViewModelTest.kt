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
    fun updateAlbumTitleUiState() {
        val expectedTitle = "Updated Title"

        viewModel.updateAlbumTitleUiState(expectedTitle)

        assertEquals(expectedTitle, viewModel.addAlbumUiState.albumDetails.album.name)
    }

    @Test
    fun updateAlbumDescriptionUiState() {
        val expectedDescription = "Updated Description"

        viewModel.updateAlbumDescriptionUiState(expectedDescription)

        assertEquals(expectedDescription, viewModel.addAlbumUiState.albumDetails.album.description)
    }

    // TODO
   /* @Test
    fun saveItem() = runBlocking {
        viewModel.updateAlbumTitleUiState("Updated Title")
        viewModel.updateAlbumDescriptionUiState("Updated Description")
        val album = viewModel.addAlbumUiState.albumDetails.album

        viewModel.saveItem { }

        assertEquals(true, viewModel.addAlbumUiState.isEntryValid)

        val savedAlbum = albumsRepository.getAlbums().first()
        assertEquals(album, savedAlbum)

        val photos = albumsRepository.getPhotosWithoutAlbum().first()
        assertEquals(0, photos.size)
    } */

    /*@Test
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
