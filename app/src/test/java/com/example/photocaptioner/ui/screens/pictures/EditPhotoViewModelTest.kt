package com.example.photocaptioner.ui.screens.pictures

import androidx.lifecycle.SavedStateHandle
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
class EditPhotoViewModelTest {

    private lateinit var viewModel: EditPhotoViewModel
    private lateinit var albumsRepository: TestAlbumsRepository

    @Before
    fun setup() {
        albumsRepository = TestAlbumsRepository()
        Dispatchers.setMain(Dispatchers.Unconfined)
        val savedStateHandle = SavedStateHandle().apply {
            set(AddPhotoToAlbumDestination.photoIdArg, 1L)
        }
        viewModel = EditPhotoViewModel(savedStateHandle, albumsRepository)
    }

    @Test
    fun saveItemSuccessful() = runBlocking {
        viewModel.updatePhotoDescriptionUiState("Updated Description")

        viewModel.saveItem()

        val updatedPhoto = albumsRepository.getPhoto(1L).first()
        assertEquals("Updated Description", updatedPhoto.description)
    }

    @Test
    fun updatePhotoDescriptionUiState() {
        val expectedUiState = EditPhotoUiState(
            photoDetails = Photo(id = 1L, description = "Updated Description")
        )

        viewModel.updatePhotoDescriptionUiState("Updated Description")

        assertEquals(expectedUiState.photoDetails.id, viewModel.editPhotoUiState.photoDetails.id)
        assertEquals(expectedUiState.photoDetails.description, viewModel.editPhotoUiState.photoDetails.description)
    }
}