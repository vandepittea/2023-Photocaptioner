package com.example.photocaptioner

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.photocaptioner.ui.PhotoCaptionerApp
import com.example.photocaptioner.ui.theme.PhotoCaptionerTheme
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (permissionGranted(Manifest.permission.CAMERA) && permissionGranted(Manifest.permission.READ_MEDIA_IMAGES)) {
            initView()
        } else if (!permissionGranted(Manifest.permission.CAMERA)){
            requestPermission(Manifest.permission.CAMERA)
        } else if (!permissionGranted(Manifest.permission.READ_MEDIA_IMAGES)) {
            requestPermission(Manifest.permission.READ_MEDIA_IMAGES)
        }
    }

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    private fun initView() {
        setContent {
            PhotoCaptionerTheme {
                val windowSize = calculateWindowSizeClass(this)
                PhotoCaptionerApp(windowSize = windowSize.widthSizeClass)
            }
        }
    }

    private fun permissionGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission(permission: String) {
        ActivityCompat.requestPermissions(this, arrayOf(permission), PERMISSION_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initView()
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 0
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