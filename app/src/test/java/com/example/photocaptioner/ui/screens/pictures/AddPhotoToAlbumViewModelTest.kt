import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.createSavedStateHandle
import com.example.photocaptioner.data.database.TestAlbumsRepository
import com.example.photocaptioner.model.AlbumWithImages
import com.example.photocaptioner.ui.screens.pictures.AddPhotoToAlbumDestination
import com.example.photocaptioner.ui.screens.pictures.AddPhotoToAlbumViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.After
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
            set(AddPhotoToAlbumDestination.photoIdArg, 1L) // Set a valid photo ID here
        }
        viewModel = AddPhotoToAlbumViewModel(savedStateHandle, albumsRepository)
    }

    @Test
    fun selectAlbum() = runTest {
        var album = AlbumWithImages()

        launch { album = albumsRepository.getAlbums().first().first() }

        viewModel.selectAlbum(album)

        assert(viewModel.addPhotoToAlbumUiState.selectedAlbum == album)
    }

    /*@Test
    fun addPhotoToAlbum() = testScope.runBlockingTest {
        val album = albumsRepository.getAlbums().first().first()
        val photo = albumsRepository.getPhoto(1).first()
        viewModel.selectAlbum(album)

        viewModel.addPhotoToAlbum()

        val updatedPhoto = albumsRepository.getPhoto(1).first()
        assert(updatedPhoto.albumId == album.album.id)
        assert(viewModel.addPhotoToAlbumUiState.photo == updatedPhoto)
    }*/
}