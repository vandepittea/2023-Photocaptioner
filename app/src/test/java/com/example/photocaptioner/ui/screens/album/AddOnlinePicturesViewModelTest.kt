package com.example.photocaptioner.ui.screens.album

import androidx.lifecycle.SavedStateHandle
import com.example.photocaptioner.data.api.UnsplashRepository
import com.example.photocaptioner.data.TestUnsplashRepository
import com.example.photocaptioner.data.database.TestAlbumsRepository
import com.example.photocaptioner.model.Photo
import com.example.photocaptioner.ui.screens.pictures.AddOnlinePicturesDestination
import com.example.photocaptioner.ui.screens.pictures.AddOnlinePicturesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

@ExperimentalCoroutinesApi
class AddOnlinePicturesViewModelTest {

    private lateinit var viewModel: AddOnlinePicturesViewModel
    private lateinit var albumsRepository: TestAlbumsRepository
    private lateinit var unsplashRepository: UnsplashRepository

    @Before
    fun setup() {
        albumsRepository = TestAlbumsRepository()
        unsplashRepository = TestUnsplashRepository()
        Dispatchers.setMain(Dispatchers.Unconfined)
        val savedStateHandle = SavedStateHandle().apply {
            set(AddOnlinePicturesDestination.albumIdArg, 1L)
        }
        viewModel = AddOnlinePicturesViewModel(savedStateHandle, albumsRepository, unsplashRepository)
    }

    @Test
    fun updateSearchValue() {
        val initialUiState = viewModel.addOnlinePicturesUiState.value
        val newSearchValue = "Nature"

        viewModel.updateSearchValue(newSearchValue)

        val expectedUiState = initialUiState.copy(searchValue = newSearchValue)
        assertEquals(expectedUiState, viewModel.addOnlinePicturesUiState.value)
    }

    @Test
    fun searchImages() = runBlocking {
        val query = "Nature"
        val images = listOf(
            "https://example.com/image1.jpg",
            "https://example.com/image2.jpg",
            "https://example.com/image3.jpg"
        )
        val expectedPhotos = images.mapIndexed { index, imageUrl ->
            Pair(false, Photo(index.toLong(), "", LocalDateTime.now(), imageUrl, 1L))
        }

        viewModel.searchImages(query)

        val uiState = viewModel.addOnlinePicturesUiState.value
        val actualPhotos = uiState.searchedPhotos.map { it.second }
        assertEquals(expectedPhotos.size, actualPhotos.size)
        expectedPhotos.forEachIndexed { index, expected ->
            val actual = actualPhotos[index]
            assertEquals(expected.second.description, actual.description)
            assertEquals(expected.second.filePath, actual.filePath)
            assertEquals(expected.second.albumId, actual.albumId)
        }
    }

    @Test
    fun selectImage() {
        val query = "Nature"
        viewModel.searchImages(query)
        val initialUiState = viewModel.addOnlinePicturesUiState.value
        val photoIndex1 = 1
        val photoIndex2 = 0
        val photoIndex3 = 2

        viewModel.selectImage(photoIndex1)
        viewModel.selectImage(photoIndex2)
        viewModel.selectImage(photoIndex3)
        viewModel.selectImage(photoIndex3)

        val selectedPhoto1 = initialUiState.searchedPhotos[photoIndex1]
        val selectedPhoto2 = initialUiState.searchedPhotos[photoIndex2]
        val expectedUiState = initialUiState.copy(
            searchedPhotos = initialUiState.searchedPhotos.toMutableList().apply {
                set(photoIndex1, Pair(!selectedPhoto1.first, selectedPhoto1.second))
                set(photoIndex2, Pair(!selectedPhoto2.first, selectedPhoto2.second))
            }
        )
        assertEquals(expectedUiState, viewModel.addOnlinePicturesUiState.value)
    }

    @Test
    fun addPhotosToAlbum() = runBlocking {
        val imagesToSelect = List(3) { it }
        viewModel.searchImages("Test")
        imagesToSelect.forEachIndexed { idx, it -> if (idx % 2 == 0) viewModel.selectImage(it) }
        val selectedImages = viewModel.addOnlinePicturesUiState.value.searchedPhotos
            .filter { it.first }
            .map { it.second }

        viewModel.addPhotosToAlbum()

        selectedImages.forEach {
            assertEquals(it.albumId, albumsRepository.getAlbum(1L).first().photos.find { photo ->
                photo.filePath == it.filePath
            }?.albumId)
        }
    }
}