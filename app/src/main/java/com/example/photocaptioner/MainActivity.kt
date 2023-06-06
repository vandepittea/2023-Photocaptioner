package com.example.photocaptioner

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.photocaptioner.ui.PhotoCaptionerApp
import com.example.photocaptioner.ui.theme.PhotoCaptionerTheme
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : ComponentActivity() {
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PhotoCaptionerTheme {
                val windowSize = calculateWindowSizeClass(this)
                PhotoCaptionerApp(windowSize = windowSize.widthSizeClass)
            }
        }
    }


    @Preview(showBackground = true)
    @Composable
    fun PhotoCaptionerAppCompactPreview() {
        PhotoCaptionerTheme {
            PhotoCaptionerApp(windowSize = WindowWidthSizeClass.Compact)
        }
    }

    @Preview(showBackground = true, widthDp = 700)
    @Composable
    fun PhotoCaptionerAppMediumPreview() {
        PhotoCaptionerTheme {
            PhotoCaptionerApp(windowSize = WindowWidthSizeClass.Medium)
        }
    }

    @Preview(showBackground = true, widthDp = 1000)
    @Composable
    fun PhotoCaptionerAppExpandedPreview() {
        PhotoCaptionerTheme {
            PhotoCaptionerApp(windowSize = WindowWidthSizeClass.Expanded)
        }
    }
}