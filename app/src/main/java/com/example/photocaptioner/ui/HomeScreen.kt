package com.example.photocaptioner.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.photocaptioner.R
import com.example.photocaptioner.ui.theme.PhotoCaptionerTheme

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ButtonWithIcon(
                imageVector = Icons.Filled.AddCircle,
                contentDescription = R.string.camera_icon,
                text = R.string.take_picture,
                onClick = {}
            )

            ButtonWithIcon(
                imageVector = Icons.Filled.AddCircle,
                contentDescription = R.string.album_icon,
                text = R.string.albums,
                onClick = {}
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.recently_edited),
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 32.dp)
            )

            ImageWithDescription(
                image = R.drawable.paris_france,
                description = R.string.paris_france_picture
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview(){
    PhotoCaptionerTheme {
        HomeScreen()
    }
}