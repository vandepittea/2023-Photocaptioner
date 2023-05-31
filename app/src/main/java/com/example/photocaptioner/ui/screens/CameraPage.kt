import android.content.Context
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.common.util.concurrent.ListenableFuture
import com.example.photocaptioner.R
import androidx.compose.foundation.Image

@Composable
fun CameraPage() {
    val lens = remember { mutableStateOf(CameraSelector.LENS_FACING_FRONT) }

    Box(modifier = Modifier.fillMaxSize()) {
        CameraPreview(cameraLens = lens.value)
        Controls(onLensChange = { lens.value = switchLens(lens.value) })
    }
}

@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    cameraLens: Int
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val previewView = remember { PreviewView(context) }
    val scaleType = PreviewView.ScaleType.FILL_CENTER

    AndroidView(
        modifier = modifier,
        factory = {
            configurePreviewView(previewView, scaleType)
            previewView
        })

    ProcessCameraProvider.getInstance(context).apply {
        configureCamera(previewView, lifecycleOwner, cameraLens)
    }
}

@Composable
fun Controls(
    onLensChange: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 24.dp),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Button(
            onClick = onLensChange,
            modifier = Modifier.wrapContentSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_cameraswitch_24),
                contentDescription = "Cameraswitch",
                modifier = Modifier
                    .height(50.dp)
            )
        }
    }
}

private fun configurePreviewView(previewView: PreviewView, scaleType: PreviewView.ScaleType) {
    previewView.apply {
        this.scaleType = scaleType
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        implementationMode = PreviewView.ImplementationMode.COMPATIBLE
    }
}

private fun configureCamera(previewView: PreviewView, lifecycleOwner: LifecycleOwner, cameraLens: Int) {
    val preview = androidx.camera.core.Preview.Builder()
        .build()
        .apply {
            setSurfaceProvider(previewView.surfaceProvider)
        }

    val cameraProvider = ProcessCameraProvider.getInstance(previewView.context)
    cameraProvider.get().unbindAll()
    cameraProvider.get().bindToLifecycle(
        lifecycleOwner,
        CameraSelector.Builder().requireLensFacing(cameraLens).build(),
        preview
    )
}

private fun switchLens(lens: Int) = if (CameraSelector.LENS_FACING_FRONT == lens) {
    CameraSelector.LENS_FACING_BACK
} else {
    CameraSelector.LENS_FACING_FRONT
}