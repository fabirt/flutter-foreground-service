package com.fabirt.flutter_foreground_service

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.Person

class FlutterAlarmReceiver : BroadcastReceiver() {
    private val channelId: String = "Alarm notification channel"

    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationHelper = NotificationHelper(context!!)
        val importance = NotificationManager.IMPORTANCE_HIGH
        notificationHelper.createNotificationChannel(channelId, importance)
        val alarmIntent = Intent(context, AlarmActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        val pendingIntent = PendingIntent.getActivity(context, 0, alarmIntent, 0)
        val notification = buildMessagingStyleNotification(context, pendingIntent)
        NotificationManagerCompat.from(context).notify(ON_GOING_ALARM_NOTIFICATION, notification)
        // context.startActivity(alarmIntent)
    }

    private fun buildMessagingStyleNotification(context: Context, pendingIntent: PendingIntent): Notification {
        val person = Person.Builder().setName("User Name").build()
        val message = NotificationCompat.MessagingStyle.Message("Some message", 1032371897L, person)
        val style = NotificationCompat.MessagingStyle(person).addMessage(message)
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
                .setContentTitle("This is your alarm")
                .setContentText("Please wake up!")
                //.setStyle(style)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher))
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)

        return notificationBuilder.build().apply {
            flags = Notification.FLAG_INSISTENT
        }
    }
}