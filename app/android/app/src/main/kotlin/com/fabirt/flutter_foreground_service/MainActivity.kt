package com.fabirt.flutter_foreground_service

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel

const val CHANNEL_NAME = "com.fabirt.flutter_foreground_service/channel"

class MainActivity : FlutterActivity(), MethodChannel.MethodCallHandler {
    private lateinit var callbackChannel: MethodChannel

    companion object {
        fun showToast(context: Context, message: String?) {
            Toast.makeText(context, message ?: "Hello Android", Toast.LENGTH_SHORT).show()
        }
    }

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        callbackChannel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL_NAME).apply {
            setMethodCallHandler(this@MainActivity)
        }
    }

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when (call.method) {
            "showToast" -> {
                val message: String? = call.argument("message")
                showToast(this, message)
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

    private fun startToastService() {
        val intent = Intent(this, FlutterService::class.java)
        ContextCompat.startForegroundService(this, intent)
    }

    private fun stopToastService() {
        Intent(this, FlutterService::class.java).also {
            stopService(it)
        }
    }
}