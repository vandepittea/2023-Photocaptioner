package com.example.photocaptioner.ui.screens.home

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.unit.dp

private fun DrawScope.drawCamera(center: Offset, color1: Color, color2: Color) {
    val cameraSize = size.minDimension / 1.3f

    // Camera body
    drawRoundRect(
        color = color1,
        topLeft = Offset(center.x - cameraSize / 2.5f, center.y - cameraSize / 2.2f),
        size = Size(cameraSize / 1.25f, cameraSize / 1.6f),
        cornerRadius = CornerRadius(cameraSize / 8f),
        style = Fill
    )

    // Camera lens
    drawCircle(
        color = color2,
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

    val color1 = MaterialTheme.colors.surface
    val color2 = MaterialTheme.colors.onSurface

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        val center = Offset(size.width / 2f, size.height / 2f)

        scale(scale, center) {
            drawCamera(center, color1, color2)
        }
    }
}