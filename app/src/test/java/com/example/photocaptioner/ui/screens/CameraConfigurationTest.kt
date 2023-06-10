package com.example.photocaptioner.ui.screens

import androidx.camera.core.*
import androidx.compose.ui.unit.Constraints
import com.example.photocaptioner.model.SourceInfo
import com.example.photocaptioner.ui.camera.PreviewScaleType
import com.example.photocaptioner.ui.camera.calculateScale
import com.example.photocaptioner.ui.camera.obtainSourceInfo
import com.example.photocaptioner.ui.camera.switchLens
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

class CameraUnitTest {
    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testSwitchLens() {
        val frontLens = CameraSelector.LENS_FACING_FRONT
        val backLens = CameraSelector.LENS_FACING_BACK

        val switchedFrontLens = switchLens(frontLens)
        val switchedBackLens = switchLens(backLens)

        assertEquals(CameraSelector.LENS_FACING_BACK, switchedFrontLens)
        assertEquals(CameraSelector.LENS_FACING_FRONT, switchedBackLens)
    }

    @Test
    fun testCalculateScale_FitCenter() {
        val constraints = Constraints(100, 100)
        val sourceInfo = SourceInfo(200, 150, false)
        val scaleType = PreviewScaleType.FIT_CENTER

        val scale = calculateScale(constraints, sourceInfo, scaleType)

        assertEquals(0.5f, scale)
    }

    @Test
    fun testCalculateScale_CenterCrop() {
        val constraints = Constraints(100, 100)
        val sourceInfo = SourceInfo(200, 150, false)
        val scaleType = PreviewScaleType.CENTER_CROP

        val scale = calculateScale(constraints, sourceInfo, scaleType)

        assertEquals(1.4316558E7f, scale, 0.000001f) // with help of internet
    }

    @Test
    fun testObtainSourceInfo() {
        val lens = CameraSelector.LENS_FACING_BACK
        val imageProxy = createMockImageProxy(100, 200, 180)

        val sourceInfo = obtainSourceInfo(lens, imageProxy)

        assertEquals(200, sourceInfo.height)
        assertEquals(100, sourceInfo.width)
        assertEquals(false, sourceInfo.isImageFlipped)
    }

    private fun createMockImageProxy(
        width: Int,
        height: Int,
        rotationDegrees: Int
    ): ImageProxy {
        val mockImageInfo = mock(ImageInfo::class.java)
        Mockito.`when`(mockImageInfo.rotationDegrees).thenReturn(rotationDegrees)

        val mockImageProxy = mock(ImageProxy::class.java)
        Mockito.`when`(mockImageProxy.imageInfo).thenReturn(mockImageInfo)
        Mockito.`when`(mockImageProxy.width).thenReturn(width)
        Mockito.`when`(mockImageProxy.height).thenReturn(height)

        return mockImageProxy
    }
}