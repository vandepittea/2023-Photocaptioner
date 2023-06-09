import android.content.Context
import android.os.Environment
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.photocaptioner.PhotoCaptionerApplicationHolder
import com.example.photocaptioner.worker.KEY_ALBUM_ID
import com.example.photocaptioner.worker.OUTPUT_DIRECTORY
import com.example.photocaptioner.worker.OUTPUT_FILE_NAME
import com.example.photocaptioner.worker.showDownloadCompleteNotification
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.*
import java.net.URL
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

class DownloadWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val albumId = inputData.getLong(KEY_ALBUM_ID, -1)

        if (albumId == -1L) {
            return@withContext Result.failure()
        }

        val albumImages = fetchAlbumImages(albumId)

        if (albumImages.isEmpty()) {
            return@withContext Result.failure()
        }

        val outputDirectory = OUTPUT_DIRECTORY
        val zipFile = File(outputDirectory, OUTPUT_FILE_NAME)

        ZipOutputStream(BufferedOutputStream(FileOutputStream(zipFile))).use { zipOutputStream ->
            albumImages.forEachIndexed { index, imageByteArray ->
                val entryName = "image_$index.jpg"
                zipOutputStream.putNextEntry(ZipEntry(entryName))
                zipOutputStream.write(imageByteArray)
                zipOutputStream.closeEntry()
            }
        }

        showDownloadCompleteNotification(applicationContext)

        return@withContext Result.success()
    }

    private fun fetchAlbumImages(albumId: Long): List<ByteArray> {
        val albumsRepository = PhotoCaptionerApplicationHolder.instance.container.provideAlbumsRepository()

        var images: List<ByteArray>
        runBlocking {
            images = albumsRepository.getAlbum(albumId).first().photos.map{
                convertImageToByteArray(it.filePath)
            }
        }
        return images
    }

    private fun convertImageToByteArray(imagePath: String): ByteArray {
        val file = File(imagePath)

        if (file.exists()) {
            // Local image file
            val inputStream = FileInputStream(file)

            return inputStream.use { input ->
                val outputStream = ByteArrayOutputStream()
                val buffer = ByteArray(1024)
                var bytesRead: Int
                while (input.read(buffer).also { bytesRead = it } != -1) {
                    outputStream.write(buffer, 0, bytesRead)
                }
                outputStream.toByteArray()
            }
        } else {
            // Online image URL
            val url = URL(imagePath)

            return url.openStream().use { input ->
                val outputStream = ByteArrayOutputStream()
                val buffer = ByteArray(1024)
                var bytesRead: Int
                while (input.read(buffer).also { bytesRead = it } != -1) {
                    outputStream.write(buffer, 0, bytesRead)
                }
                outputStream.toByteArray()
            }
        }
    }
}