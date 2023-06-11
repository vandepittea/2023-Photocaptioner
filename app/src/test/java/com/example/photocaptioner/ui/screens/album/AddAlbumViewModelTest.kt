package com.example.photocaptioner.ui.screens.album

import com.example.photocaptioner.data.database.TestAlbumsRepository
import com.example.photocaptioner.model.Photo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class AddAlbumViewModelTest {

    private lateinit var viewModel: AlbumInformationViewModel
    private lateinit var albumsRepository: TestAlbumsRepository

    @Before
    fun setup() {
        albumsRepository = TestAlbumsRepository()
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = AlbumInformationViewModel(albumsRepository)
    }

    @Test
    fun updateAlbumTitleUiState() {
        val expectedTitle = "Updated Title"

        viewModel.updateAlbumTitleUiState(expectedTitle)

        assertEquals(expectedTitle, viewModel.albumUiState.albumDetails.album.name)
    }

    @Test
    fun updateAlbumDescriptionUiState() {
        val expectedDescription = "Updated Description"

        viewModel.updateAlbumDescriptionUiState(expectedDescription)

        assertEquals(expectedDescription, viewModel.albumUiState.albumDetails.album.description)
    }

    @Test
    fun saveItem() {
        var newAlbumId: Long
        var photosWithoutAlbum: List<Long>

        viewModel.updateAlbumTitleUiState("Updated Title")
        viewModel.updateAlbumDescriptionUiState("Updated Description")

        runBlocking { newAlbumId = albumsRepository.getAlbums().first().size.toLong() + 1 }
        runBlocking { photosWithoutAlbum = albumsRepository.getPhotosWithoutAlbum().first().map { it.id } }

        runBlocking { viewModel.saveItem { } }

        photosWithoutAlbum.forEach {
            run {
                var photo: Photo
                runBlocking { photo = albumsRepository.getPhoto(it).first() }
                assertEquals(newAlbumId, photo.albumId)
            }
        }
    }

    @Test
    fun saveItemWithInvalidTitle() {
        var photosWithoutAlbum: List<Long>

        viewModel.updateAlbumTitleUiState("")
        viewModel.updateAlbumDescriptionUiState("Updated Description")

        runBlocking { photosWithoutAlbum = albumsRepository.getPhotosWithoutAlbum().first().map { it.id } }

        runBlocking { viewModel.saveItem { } }

        photosWithoutAlbum.forEach {
            run {
                var photo: Photo
                runBlocking { photo = albumsRepository.getPhoto(it).first() }
                assertEquals(-1, photo.albumId)
            }
        }
    }

    @Test
    fun saveItemWithInvalidDescription() {
        var photosWithoutAlbum: List<Long>

        viewModel.updateAlbumTitleUiState("Updated Title")
        viewModel.updateAlbumDescriptionUiState("")

        runBlocking { photosWithoutAlbum = albumsRepository.getPhotosWithoutAlbum().first().map { it.id } }

        runBlocking { viewModel.saveItem { } }

        photosWithoutAlbum.forEach {
            run {
                var photo: Photo
                runBlocking { photo = albumsRepository.getPhoto(it).first() }
                assertEquals(-1, photo.albumId)
            }
        }
    }

    @Test
    fun saveItemWithInvalidTitleAndDescription() {
        var photosWithoutAlbum: List<Long>

        viewModel.updateAlbumTitleUiState("")
        viewModel.updateAlbumDescriptionUiState("")

        runBlocking { photosWithoutAlbum = albumsRepository.getPhotosWithoutAlbum().first().map { it.id } }

        runBlocking { viewModel.saveItem { } }

        photosWithoutAlbum.forEach {
            run {
                var photo: Photo
                runBlocking { photo = albumsRepository.getPhoto(it).first() }
                assertEquals(-1, photo.albumId)
            }
        }
    }
}
