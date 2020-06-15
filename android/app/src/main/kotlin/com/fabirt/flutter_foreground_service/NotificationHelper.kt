package com.fabirt.flutter_foreground_service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

class NotificationHelper(private val context: Context) {
    fun createNotificationChannel(channelId: String, importance: Int = NotificationManager.IMPORTANCE_DEFAULT) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.fg_service_notification_channel_name)
            val descriptionText = context.getString(R.string.fg_service_notification_channel_description)
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}