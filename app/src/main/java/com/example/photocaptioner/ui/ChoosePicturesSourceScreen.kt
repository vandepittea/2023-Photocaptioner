package com.example.photocaptioner.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.photocaptioner.R
import com.example.photocaptioner.ui.theme.PhotoCaptionerTheme

@Composable
fun ChoosePicturesSourceScreen(
    onChooseCamera: () -> Unit,
    onChooseGallery: () -> Unit,
    onChooseMaps: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(16.dp)
    ) {
        Column {
            TopBar(title = R.string.choose_picture_source)
            ImageOptions(
                onChooseCamera = onChooseCamera,
                onChooseGallery = onChooseGallery,
                onChooseMaps = onChooseMaps
            )
        }
    }
}

@Preview
@Composable
fun ChoosePicturesSourceScreenPreview() {
    PhotoCaptionerTheme {
        ChoosePicturesSourceScreen(
            onChooseCamera = {},
            onChooseGallery = {},
            onChooseMaps = {}
        )
    }
}