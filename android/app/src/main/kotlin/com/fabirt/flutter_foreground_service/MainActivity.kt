package com.fabirt.flutter_foreground_service

import android.widget.Toast
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity: FlutterActivity() {
    private val channel = "com.fabirt.flutter_foreground_service/channel"

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, channel).setMethodCallHandler { call, result ->
            when (call.method) {
                "showToast" -> {
                    val message: String? = call.argument("message")
                    showToast(message)
                }
                else -> result.notImplemented()
            }
        }
    }

    private fun showToast(message: String?) {
        Toast.makeText(this, message ?: "Hola Android", Toast.LENGTH_SHORT).show()
    }
}
