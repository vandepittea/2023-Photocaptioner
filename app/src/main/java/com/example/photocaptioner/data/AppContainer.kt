import android.app.Application
import com.example.photocaptioner.data.UnsplashRepository

class AppContainer(application: Application) {
    private val unsplashRepository: UnsplashRepository = UnsplashRepository()

    fun provideUnsplashRepository(): UnsplashRepository {
        return unsplashRepository
    }
}
