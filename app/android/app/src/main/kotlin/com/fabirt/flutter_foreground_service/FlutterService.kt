package com.fabirt.flutter_foreground_service

import android.app.*
import android.content.Intent
import android.os.*
import androidx.core.app.NotificationCompat


class FlutterService : Service() {
    private val channelId: String = "Service notification channel"
    private lateinit var mainHandler: Handler

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
        val notificationHelper = NotificationHelper(this)
        notificationHelper.createNotificationChannel(channelId)
        mainHandler = Handler(Looper.getMainLooper())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(ON_GOING_SERVICE_NOTIFICATION, createNotification())
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

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
                .setContentTitle("Awesome service running")
                .setContentText("Keep calm... this service is awesome")
                .setSmallIcon(R.drawable.ic_service)
                .setContentIntent(pendingIntent)
                .setUsesChronometer(true)
                .setOngoing(true)

        return notificationBuilder.build()
    }
}