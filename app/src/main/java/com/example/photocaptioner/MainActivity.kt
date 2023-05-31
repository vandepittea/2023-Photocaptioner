package com.example.photocaptioner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.photocaptioner.ui.theme.PhotoCaptionerTheme
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import com.example.photocaptioner.ui.PhotoCaptionerApp

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PhotoCaptionerTheme {
                val windowSize = calculateWindowSizeClass(this)
                PhotoCaptionerApp(
                    windowSize = windowSize.widthSizeClass
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PhotoCaptionerAppCompactPreview() {
    PhotoCaptionerTheme {
        PhotoCaptionerApp(
            windowSize = WindowWidthSizeClass.Compact
        )
    }
}

@Preview(showBackground = true, widthDp = 700)
@Composable
fun PhotoCaptionerAppMediumPreview() {
    PhotoCaptionerTheme {
        PhotoCaptionerApp(
            windowSize = WindowWidthSizeClass.Medium
        )
    }
}

@Preview(showBackground = true, widthDp = 1000)
@Composable
fun PhotoCaptionerAppExpandedPreview() {
    PhotoCaptionerTheme {
        PhotoCaptionerApp(
            windowSize = WindowWidthSizeClass.Expanded
        )
    }
}