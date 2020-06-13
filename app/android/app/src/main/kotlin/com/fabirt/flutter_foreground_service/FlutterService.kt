package com.fabirt.flutter_foreground_service

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat

const val ON_GOING_NOTIFICATION = 1

class FlutterService : Service() {
    lateinit var mainHandler: Handler

    private val periodicTask = object : Runnable {
        override fun run() {
            MainActivity.showToast(this@FlutterService, "Hi from Android!")
            mainHandler.postDelayed(this, 5000)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        mainHandler = Handler(Looper.getMainLooper())
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(ON_GOING_NOTIFICATION, createNotification())
        mainHandler.post(periodicTask)
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        mainHandler.removeCallbacks(periodicTask)
        super.onDestroy()
    }

    private fun createNotification(): Notification {
        val pendingIntent: PendingIntent =
                Intent(this, MainActivity::class.java).let { notificationIntent ->
                    PendingIntent.getActivity(this, 0, notificationIntent, 0)
                }

        val channelId = getString(R.string.fg_service_notification_channel)

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
                .setContentTitle("Awesome service running")
                .setContentText("Keep calm... this service is awesome")
                .setSmallIcon(R.drawable.ic_service)
                .setContentIntent(pendingIntent)
                .setUsesChronometer(true)
                .setOngoing(true)

        return notificationBuilder.build()

    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = getString(R.string.fg_service_notification_channel)
            val name = getString(R.string.fg_service_notification_channel_name)
            val descriptionText = getString(R.string.fg_service_notification_channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}