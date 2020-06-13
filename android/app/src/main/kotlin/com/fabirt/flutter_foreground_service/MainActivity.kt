package com.fabirt.flutter_foreground_service

import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity : FlutterActivity() {
    private val channel = "com.fabirt.flutter_foreground_service/channel"
    private lateinit var callbackChannel: MethodChannel

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        callbackChannel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, channel).apply {
            setMethodCallHandler { call, result ->
                when (call.method) {
                    "showToast" -> {
                        val message: String? = call.argument("message")
                        showToast(message)
                        result.success(null)
                    }
                    "startToastService" -> {
                        startToastService()
                        result.success(null)
                    }
                    "stopToastService" -> {
                        stopToastService()
                        result.success(null)
                    }
                    else -> result.notImplemented()
                }
            }
        }
    }

    private fun showToast(message: String?) {
        Toast.makeText(this, message ?: "Hola Android", Toast.LENGTH_SHORT).show()
    }

    private fun startToastService() {
        Intent(this, FlutterService::class.java).also {
            ContextCompat.startForegroundService(this, it)
        }
    }

    private fun stopToastService() {
        Intent(this, FlutterService::class.java).also {
            stopService(it)
        }
    }
}
