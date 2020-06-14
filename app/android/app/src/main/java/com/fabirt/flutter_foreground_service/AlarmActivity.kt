package com.fabirt.flutter_foreground_service

import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class AlarmActivity : AppCompatActivity() {

    private lateinit var alert: Uri
    private lateinit var player: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)
        alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
        player = MediaPlayer.create(this, alert)
        player.start()
    }

    override fun onDestroy() {
        player.stop()
        super.onDestroy()
    }
}
