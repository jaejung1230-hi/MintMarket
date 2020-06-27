package com.example.testapp.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build


class RebootRecever : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val startin = Intent(context, RestartService::class.java)
            context.startForegroundService(startin)
        } else {
            val startin = Intent(context, RealService::class.java)
            context.startService(startin)
        }
    }
}