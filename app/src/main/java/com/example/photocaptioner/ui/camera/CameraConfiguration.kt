package com.example.photocaptioner.ui.camera

import FaceDetectorProcessor
import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.ui.unit.Constraints
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.example.photocaptioner.model.SourceInfo
import com.google.android.gms.tasks.TaskExecutors
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.face.Face
import java.util.concurrent.Executor

lateinit var imageCapture: ImageCapture

public fun ListenableFuture<ProcessCameraProvider>.configureCamera(
    previewView: PreviewView,
    lifecycleOwner: LifecycleOwner,
    cameraLens: Int,
    context: Context,
    setSourceInfo: (SourceInfo) -> Unit,
    onFacesDetected: (List<Face>) -> Unit
): ListenableFuture<ProcessCameraProvider> {
    val cameraSelector = CameraSelector.Builder().requireLensFacing(cameraLens).build()

    val preview = Preview.Builder()
        .build()
        .apply {
            setSurfaceProvider(previewView.surfaceProvider)
        }

    imageCapture = ImageCapture.Builder()
        .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
        .setFlashMode(ImageCapture.FLASH_MODE_AUTO)
        .build()

    // Create an analysis use case for processing image frames
    val analysis = createAnalysisUseCase(cameraLens, setSourceInfo, onFacesDetected)

    // Add a listener to the future to bind the use cases when available
    addListener({
        get().apply {
            unbindAll()

            // Bind the preview and analysis use cases to the lifecycle and camera selector
            bindToLifecycle(lifecycleOwner, cameraSelector, preview, imageCapture)
            bindToLifecycle(lifecycleOwner, cameraSelector, analysis)
        }
    }, ContextCompat.getMainExecutor(context))

    return this
}

public fun switchLens(lens: Int): Int {
    return if (lens == CameraSelector.LENS_FACING_FRONT) {
        CameraSelector.LENS_FACING_BACK
    } else {
        CameraSelector.LENS_FACING_FRONT
    }
}

private fun createAnalysisUseCase(
    lens: Int,
    setSourceInfo: (SourceInfo) -> Unit,
    onFacesDetected: (List<Face>) -> Unit
): ImageAnalysis {
    // Create an instance of the face detector processor
    val imageProcessor = FaceDetectorProcessor()

    // Build the image analysis use case
    val builder = ImageAnalysis.Builder()
    val analysisUseCase = builder.build()

    // Track whether source info has been updated
    var sourceInfoUpdated = false

    // Set the analyzer for processing image frames
    analysisUseCase.setAnalyzer(
        TaskExecutors.MAIN_THREAD
    ) { imageProxy: ImageProxy ->
        imageProxy.let { proxy ->
            // Set the source info if it has not been updated yet
            if (!sourceInfoUpdated) {
                setSourceInfo(obtainSourceInfo(lens, proxy))
                sourceInfoUpdated = true
            }

            // Process the image proxy using the face detector processor
            imageProcessor.processImageProxy(proxy, onFacesDetected)
        }
    }

    return analysisUseCase
}

private fun obtainSourceInfo(lens: Int, imageProxy: ImageProxy): SourceInfo {
    // Determine if the image is flipped based on the lens facing
    val isImageFlipped = lens == CameraSelector.LENS_FACING_FRONT

    // Get the rotation degrees of the image
    val rotationDegrees = imageProxy.imageInfo.rotationDegrees

    // Create a SourceInfo object based on the rotation and lens facing
    return if (rotationDegrees == 0 || rotationDegrees == 180) {
        SourceInfo(
            height = imageProxy.height, width = imageProxy.width, isImageFlipped = isImageFlipped
        )
    } else {
        SourceInfo(
            height = imageProxy.width, width = imageProxy.height, isImageFlipped = isImageFlipped
        )
    }
}

public fun calculateScale(
    constraints: Constraints,
    sourceInfo: SourceInfo,
    scaleType: PreviewScaleType
): Float {
    // Calculate the height and width ratios
    val heightRatio = constraints.maxHeight.toFloat() / sourceInfo.height
    val widthRatio = constraints.maxWidth.toFloat() / sourceInfo.width

    return when (scaleType) {
        // Calculate the scale based on the scale type
        PreviewScaleType.FIT_CENTER -> heightRatio.coerceAtMost(widthRatio)
        PreviewScaleType.CENTER_CROP -> heightRatio.coerceAtLeast(widthRatio)
    }
}

public enum class PreviewScaleType {
    FIT_CENTER,
    CENTER_CROP
}