package com.example.photocaptioner.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.provider.DocumentsContract
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.photocaptioner.R

fun showDownloadCompleteNotification(context: Context) {
    createNotificationChannel(context)

    val intent = Intent(Intent.ACTION_GET_CONTENT)
    intent.type = "*/*"
    val folderUri = Uri.parse("content://com.android.externalstorage.documents/document/primary%3ADownload")
    intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, folderUri)

    val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

    val customLogoBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.camera)

    val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.album1_picture1)
        .setLargeIcon(customLogoBitmap)
        .setContentTitle("Download Complete")
        .setContentText("Album download is complete")
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    with(NotificationManagerCompat.from(context)) {
        notify(NOTIFICATION_ID, notificationBuilder.build())
    }
}

private fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "Download Notifications"
        val descriptionText = "Shows notifications for download progress"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}