package com.example.testapp.service

import android.R
import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.testapp.activity.Main2Activity
import com.example.testapp.activity.MainActivity



class RestartService : Service(){
    override fun onCreate() {
        super.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val builder: Notification.Builder = Notification.Builder(this, "default")
        builder.setSmallIcon(R.mipmap.sym_def_app_icon)
        builder.setContentTitle(null)
        builder.setContentText(null)
        val notificationIntent = Intent(this, Main2Activity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
        builder.setContentIntent(pendingIntent)
        val manager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(
                NotificationChannel(
                    "default",
                    "기본 채널",
                    NotificationManager.IMPORTANCE_NONE
                )
            )
        }
        val notification: Notification = builder.build()
        startForeground(9, notification)

        /////////////////////////////////////////////////////////////////////
        val startin = Intent(this, RealService::class.java)
        startService(startin)
        stopForeground(true)
        stopSelf()
        return START_NOT_STICKY
    }

}