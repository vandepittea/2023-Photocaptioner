package com.example.photocaptioner.ui.screens.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.core.content.ContextCompat
import com.example.photocaptioner.R
import com.example.photocaptioner.ui.screens.Button
import com.example.photocaptioner.ui.screens.navigation.NavigationDestination
import com.example.photocaptioner.ui.theme.PhotoCaptionerTheme

object StartUpDestination : NavigationDestination {
    override val route = "start_up"
    override val titleRes = R.string.app_name
    override val routeWithArgs = route
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
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
        val context = LocalContext.current
        val launcherMultiplePermissions = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
        }
        Text(
            text = stringResource(R.string.app_name),
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.h1,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )

        AnimatedLogo()

        Button(
            text = R.string.get_started,
            onClick = {
                val permissions = arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.INTERNET,
                    Manifest.permission.POST_NOTIFICATIONS
                )

                if (!permissions.all {
                    ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
                }) {
                    launcherMultiplePermissions.launch(permissions)
                }

                onButtonClick()
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview
@Composable
fun StartUpScreenPreview(){
    PhotoCaptionerTheme {
        StartUpScreen(onButtonClick = {})
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview(widthDp = 1000)
@Composable
fun StartUpScreenPreviewWithExpandedView(){
    PhotoCaptionerTheme {
        StartUpScreen(onButtonClick = {})
    }
}