package com.example.photocaptioner.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.photocaptioner.data.Datasource
import com.example.photocaptioner.ui.theme.PhotoCaptionerTheme

@Composable
fun AlbumsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Text(
            text = "My Albums",
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp)
        ) {
            items(Datasource.albumList) { album ->
                ImageWithDescription(
                    image = album.image,
                    description = album.name
                )
            }
        }
    }
}

@Preview
@Composable
fun AlbumsScreenPreview(){
    PhotoCaptionerTheme {
        AlbumsScreen()
    }
}