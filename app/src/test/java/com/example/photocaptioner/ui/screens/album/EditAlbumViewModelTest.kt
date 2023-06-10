package com.example.photocaptioner.ui.screens.album

import androidx.lifecycle.SavedStateHandle
import com.example.photocaptioner.data.database.TestAlbumsRepository
import com.example.photocaptioner.model.Album
import com.example.photocaptioner.model.AlbumWithImages
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class EditAlbumViewModelTest {

    private lateinit var viewModel: EditAlbumViewModel
    private lateinit var albumsRepository: TestAlbumsRepository

    @Before
    fun setup() {
        albumsRepository = TestAlbumsRepository()
        Dispatchers.setMain(Dispatchers.Unconfined)
        val savedStateHandle = SavedStateHandle().apply {
            set(EditAlbumDestination.albumIdArg, 1L)
        }
        viewModel = EditAlbumViewModel(savedStateHandle, albumsRepository)
    }

    @Test
    fun updateAlbumTitleUiState() {
        val expectedUiState = AlbumUiState(
            albumDetails = AlbumWithImages(
                album = Album(id = 1L, name = "Updated Album Name", description = "Description 1"),
                photos = emptyList()
            ),
            isEntryValid = true
        )

        viewModel.updateAlbumTitleUiState("Updated Album Name")

        assertEquals(expectedUiState.albumDetails.album.id, viewModel.editAlbumUiState.albumDetails.album.id)
        assertEquals(expectedUiState.albumDetails.album.name, viewModel.editAlbumUiState.albumDetails.album.name)
        assertEquals(expectedUiState.albumDetails.album.description, viewModel.editAlbumUiState.albumDetails.album.description)
        assertEquals(expectedUiState.isEntryValid, viewModel.editAlbumUiState.isEntryValid)
    }

    @Test
    fun updateAlbumDescriptionUiState() {
        val expectedUiState = AlbumUiState(
            albumDetails = AlbumWithImages(
                album = Album(id = 1L, name = "Album 1", description = "Updated Album Description"),
                photos = emptyList()
            ),
            isEntryValid = true
        )

        viewModel.updateAlbumDescriptionUiState("Updated Album Description")

        assertEquals(expectedUiState.albumDetails.album.id, viewModel.editAlbumUiState.albumDetails.album.id)
        assertEquals(expectedUiState.albumDetails.album.name, viewModel.editAlbumUiState.albumDetails.album.name)
        assertEquals(expectedUiState.albumDetails.album.description, viewModel.editAlbumUiState.albumDetails.album.description)
        assertEquals(expectedUiState.isEntryValid, viewModel.editAlbumUiState.isEntryValid)
    }

    @Test
    fun saveItemValidInput() = runBlocking {
        viewModel.updateAlbumTitleUiState("Updated Album Name")
        viewModel.updateAlbumDescriptionUiState("Updated Album Description")

        viewModel.saveItem()

        val updatedAlbum = albumsRepository.getAlbum(1L).first()
        assertEquals("Updated Album Name", updatedAlbum.album.name)
        assertEquals("Updated Album Description", updatedAlbum.album.description)
    }

    @Test
    fun saveItemInvalidDescriptionInput() = runBlocking {
        val initialDescription = albumsRepository.getAlbum(1L).first().album.description
        val initialTitle = albumsRepository.getAlbum(1L).first().album.name
        viewModel.updateAlbumDescriptionUiState("")
        viewModel.updateAlbumTitleUiState("Updated Album Name")

        viewModel.saveItem()

        val updatedAlbum = albumsRepository.getAlbum(1L).first()
        assertEquals(initialTitle, updatedAlbum.album.name)
        assertEquals(initialDescription, updatedAlbum.album.description)
    }

    @Test
    fun saveItemInvalidTitleInput() = runBlocking {
        val initialDescription = albumsRepository.getAlbum(1L).first().album.description
        val initialTitle = albumsRepository.getAlbum(1L).first().album.name
        viewModel.updateAlbumDescriptionUiState("Updated Album Description")
        viewModel.updateAlbumTitleUiState("")

        viewModel.saveItem()

        val updatedAlbum = albumsRepository.getAlbum(1L).first()
        assertEquals(initialTitle, updatedAlbum.album.name)
        assertEquals(initialDescription, updatedAlbum.album.description)
    }

    @Test
    fun saveItemInvalidTitleAndDescriptionInput() = runBlocking {
        val initialDescription = albumsRepository.getAlbum(1L).first().album.description
        val initialTitle = albumsRepository.getAlbum(1L).first().album.name
        viewModel.updateAlbumDescriptionUiState("")
        viewModel.updateAlbumTitleUiState("")

        viewModel.saveItem()

        val updatedAlbum = albumsRepository.getAlbum(1L).first()
        assertEquals(initialTitle, updatedAlbum.album.name)
        assertEquals(initialDescription, updatedAlbum.album.description)
    }
}