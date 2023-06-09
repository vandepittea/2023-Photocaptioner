package com.example.photocaptioner.ui

import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.core.content.ContextCompat
import com.example.photocaptioner.R
import com.example.photocaptioner.ui.screens.home.AnimatedLogo
import com.example.photocaptioner.ui.screens.navigation.NavigationDestination
import com.example.photocaptioner.ui.theme.PhotoCaptionerTheme

object StartUpDestination : NavigationDestination {
    override val route = "start_up"
    override val titleRes = R.string.app_name
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
        ) { permissionsMap ->
            val areGranted = permissionsMap.values.reduce { acc, next -> acc && next }
            if (areGranted) {
                Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.h1,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        )

        AnimatedLogo()

        Button(
            text = R.string.get_started,
            onClick = {
                val permissions = arrayOf(
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.READ_MEDIA_IMAGES,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
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