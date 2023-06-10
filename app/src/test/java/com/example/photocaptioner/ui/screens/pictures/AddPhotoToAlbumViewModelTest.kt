import androidx.lifecycle.SavedStateHandle
import com.example.photocaptioner.data.database.TestAlbumsRepository
import com.example.photocaptioner.model.Album
import com.example.photocaptioner.model.AlbumWithImages
import com.example.photocaptioner.model.Photo
import com.example.photocaptioner.ui.screens.pictures.AddPhotoToAlbumDestination
import com.example.photocaptioner.ui.screens.pictures.AddPhotoToAlbumUiState
import com.example.photocaptioner.ui.screens.pictures.AddPhotoToAlbumViewModel
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
        var album = AlbumWithImages()

        runBlocking { album = albumsRepository.getAlbums().first().first() }

        viewModel.selectAlbum(album)

        assert(viewModel.addPhotoToAlbumUiState.selectedAlbum == album)
    }


    @Test
    fun addPhotoToAlbum() = runBlocking {
        val album = AlbumWithImages(album = Album(id = 2L))
        val photo = Photo(id = 1L, albumId = 0L)
        viewModel.addPhotoToAlbumUiState = AddPhotoToAlbumUiState(
            photo = photo,
            availableAlbums = listOf(album),
            selectedAlbum = album
        )

        viewModel.addPhotoToAlbum()

        assertEquals(album.album.id, viewModel.addPhotoToAlbumUiState.photo.albumId)
    }
}