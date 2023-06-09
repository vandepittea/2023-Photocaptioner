import android.util.Log
import android.view.ViewGroup
import androidx.compose.runtime.*
import androidx.camera.core.CameraSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.mlkit.vision.face.Face
import com.example.photocaptioner.R
import androidx.compose.foundation.Image
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.photocaptioner.model.SourceInfo
import com.example.photocaptioner.ui.AppViewModelProvider
import com.example.photocaptioner.ui.camera.PreviewScaleType
import com.example.photocaptioner.ui.camera.calculateScale
import com.example.photocaptioner.ui.camera.configureCamera
import com.example.photocaptioner.ui.camera.imageCapture
import com.example.photocaptioner.ui.camera.switchLens
import com.example.photocaptioner.ui.screens.navigation.NavigationDestination
import com.example.photocaptioner.ui.screens.pictures.CameraPageViewModel

object CameraPageDestination : NavigationDestination {
    override val route = "Camera"
    override val titleRes = R.string.photo
    const val albumIdArg = "albumId"
    val routeWithArgs = "$route/{$albumIdArg}"
}

@Composable
fun CameraPage(
    onTakePictureFromHome: (photoId: Long) -> Unit,
    navigateBack: (route: String, include: Boolean) -> Unit,
    viewModel: CameraPageViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val lens = remember { mutableStateOf(CameraSelector.LENS_FACING_FRONT) }
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        CameraPreview(cameraLens = lens.value)
        Controls(
            onLensChange = { lens.value = switchLens(lens.value) },
            onTakePicture = {
                viewModel.savePicture(context, imageCapture, onTakePictureFromHome, navigateBack)
            }
        )
    }
}

@Composable
fun CameraPreview(
    cameraLens: Int,

) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    var sourceInfo by remember { mutableStateOf(SourceInfo(10, 10, false)) }
    var detectedFaces by remember { mutableStateOf<List<Face>>(emptyList()) }
    val previewView = remember { PreviewView(context) }

    // Configure and bind the camera preview with face detection
    ProcessCameraProvider.getInstance(context)
            .configureCamera(
                previewView, lifecycleOwner, cameraLens, context,
                setSourceInfo = { sourceInfo = it },
                onFacesDetected = { detectedFaces = it },
            )

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        with(LocalDensity.current) {
            Box(
                modifier = Modifier
                    .size(
                        height = sourceInfo.height.toDp(),
                        width = sourceInfo.width.toDp()
                    )
                    // Calculate the appropriate scale based on constraints and sourceInfo
                    .scale(
                        calculateScale(
                            constraints,
                            sourceInfo,
                            PreviewScaleType.CENTER_CROP
                        )
                    )
            )
            {
                // Render the camera preview and detected faces within the scaled box
                CameraPreview(previewView)
                DetectedFaces(faces = detectedFaces, sourceInfo = sourceInfo)
            }
        }
    }
}

@Composable
private fun CameraPreview(previewView: PreviewView) {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            previewView.apply {
                // Set the scaling and layout parameters for the preview view
                this.scaleType = PreviewView.ScaleType.FILL_CENTER
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )

                implementationMode = PreviewView.ImplementationMode.COMPATIBLE
            }

            previewView
        })
}

@Composable
fun Controls(
    onLensChange: () -> Unit,
    onTakePicture: () -> Unit
) {
    LensChange(onLensChange = onLensChange)
    TakePicture(onTakePicture = onTakePicture)
}

@Composable
fun LensChange(
    onLensChange: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.TopStart,
    ) {
        Button(
            onClick = onLensChange,
            modifier = Modifier.wrapContentSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_cameraswitch_24),
                contentDescription = stringResource(R.string.cameraswitch),
                modifier = Modifier
                    .height(50.dp)
            )
        }
    }
}

@Composable
fun TakePicture(
    onTakePicture: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Button(
            onClick = onTakePicture,
            modifier = Modifier.wrapContentSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_camera_alt_24),
                contentDescription = stringResource(R.string.take_picture),
                modifier = Modifier
                    .height(50.dp)
            )
        }
    }
}

@Composable
fun DetectedFaces(
    faces: List<Face>,
    sourceInfo: SourceInfo
) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val needToMirror = sourceInfo.isImageFlipped

        for (face in faces) {
            val left =
                if (needToMirror) size.width - face.boundingBox.right.toFloat() else face.boundingBox.left.toFloat()

            // Draw a rectangle around each detected face
            drawRect(
                Color.Gray, style = Stroke(2.dp.toPx()),
                topLeft = Offset(left, face.boundingBox.top.toFloat()),
                size = Size(face.boundingBox.width().toFloat(), face.boundingBox.height().toFloat())
            )
        }
    }
}