package com.example.photocaptioner.ui.screens.home

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.*

private fun DrawScope.drawCamera(center: Offset) {
    val cameraSize = size.minDimension / 1.3f

    // Camera body
    drawRoundRect(
        color = Color(0xFFD9D9D9),
        topLeft = Offset(center.x - cameraSize / 2.5f, center.y - cameraSize / 2.2f),
        size = Size(cameraSize / 1.25f, cameraSize / 1.6f),
        cornerRadius = CornerRadius(cameraSize / 8f),
        style = Fill
    )

    // Camera lens
    drawCircle(
        color = Color(0xFF000000),
        radius = cameraSize / 6f,
        center = Offset(center.x, center.y - cameraSize / 5f)
    )
}

@Composable
fun AnimatedLogo() {
    val transition = rememberInfiniteTransition()

    // Animation parameters
    val scale by transition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        val center = Offset(size.width / 2f, size.height / 2f)

        scale(scale, center) {
            drawCamera(center)
        }
    }
}