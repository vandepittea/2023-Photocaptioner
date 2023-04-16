package com.example.photocaptioner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.example.photocaptioner.ui.theme.PhotoCaptionerTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PhotoCaptionerTheme {
                val windowSize = calculateWindowSizeClass(this)
                PhotoCaptionersApp(
                    windowSize = windowSize.widthSizeClass
                )
            }
        }
    }
}