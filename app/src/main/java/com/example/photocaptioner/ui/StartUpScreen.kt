package com.example.photocaptioner.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.text.style.TextAlign
import com.example.photocaptioner.R
import com.example.photocaptioner.ui.theme.PhotoCaptionerTheme

@Composable
fun StartUpScreen(onButtonClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.h1,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.camera),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )

        Button(R.string.get_started, onButtonClick)
    }
}

@Preview
@Composable
fun StartUpScreenPreview(){
    PhotoCaptionerTheme {
        StartUpScreen(onButtonClick = {})
    }
}