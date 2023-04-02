package com.example.photocaptioner.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.photocaptioner.ui.theme.PhotoCaptionerTheme
import com.example.photocaptioner.data.Datasource
import com.example.photocaptioner.model.Album

@Composable
fun AlbumDetailScreen(album: Album) {
    val colors = MaterialTheme.colors
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = stringResource(id = album.name),
            style = MaterialTheme.typography.subtitle1,
            color = colors.onBackground,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Text(
            text = stringResource(id = album.description),
            style = MaterialTheme.typography.body1,
            color = colors.onBackground,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Last changed on ${album.lastChanged}",
            style = MaterialTheme.typography.body2,
            color = colors.onBackground,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        AlternatingColumn(items = album.photos)
    }
}

@Preview
@Composable
fun AlbumDetailScreenPreview(){
    PhotoCaptionerTheme {
        AlbumDetailScreen(Datasource.albumList[0])
    }
}