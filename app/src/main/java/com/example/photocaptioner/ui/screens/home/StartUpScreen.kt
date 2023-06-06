package com.example.photocaptioner.ui

import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import com.example.photocaptioner.R
import com.example.photocaptioner.ui.screens.navigation.NavigationDestination
import com.example.photocaptioner.ui.theme.PhotoCaptionerTheme

object StartUpDestination : NavigationDestination {
    override val route = "start_up"
    override val titleRes = R.string.app_name
}

@Composable
fun StartUpScreen(
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler(
        onBack = {}
    )
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
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
                .height(300.dp)
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

@Preview(widthDp = 1000)
@Composable
fun StartUpScreenPreviewWithExpandedView(){
    PhotoCaptionerTheme {
        StartUpScreen(onButtonClick = {})
    }
}