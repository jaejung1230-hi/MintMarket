package com.example.testapp.service

import android.R
import android.app.*
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.testapp.activity.Main2Activity
import java.text.SimpleDateFormat
import java.util.*


class RealService : Service(){
    val mpreference by lazy { (application as UserPreference).mpreferences }
    private var mainThread: Thread? = null
    companion object {
        var serviceIntent: Intent? = null
    }
    override fun onCreate() {
        super.onCreate()
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        serviceIntent = intent
        showToast(application, "Start Service")
        mainThread = Thread(Runnable {
            val sdf = SimpleDateFormat("aa hh:mm")
            var run = true
            while (run) {
                try {
                    Thread.sleep(1000*5.toLong()) // 1 minute
                    val date = Date()
                    val id =  mpreference.getString("save_loginuser","없음")
                    showToast(getApplication(),id)
                    sendNotification(sdf.format(date))
                    MyitemCheck(id).check()
                } catch (e: InterruptedException) {
                    run = false
                    e.printStackTrace()
                }
            }
        })
        mainThread!!.start()
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()

        serviceIntent = null
        setAlarmTimer()

        Thread.currentThread().interrupt()

        if (mainThread != null) {
            mainThread!!.interrupt()
            mainThread = null
        }
    }

    ////////////////////////////////////////////////////////////////////////

    fun showToast(application: Application, msg: String?) {
        val h = Handler(application.mainLooper)
        h.post(Runnable { Toast.makeText(application, msg, Toast.LENGTH_LONG).show() })
    }

    protected fun setAlarmTimer() {
        val c = Calendar.getInstance()
        c.timeInMillis = System.currentTimeMillis()
        c.add(Calendar.SECOND, 1)
        val intent = Intent(this, AlarmRecever::class.java)
        val sender = PendingIntent.getBroadcast(this, 0, intent, 0)
        val mAlarmManager =
            getSystemService(Context.ALARM_SERVICE) as AlarmManager
        mAlarmManager[AlarmManager.RTC_WAKEUP, c.timeInMillis] = sender
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun sendNotification(messageBody: String) {
        val intent = Intent(this, Main2Activity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0 /* Request code */,
            intent,
            PendingIntent.FLAG_ONE_SHOT
        )
        val channelId =
            "fcm_default_channel" //getString(R.string.default_notification_channel_id)
        val defaultSoundUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder: Notification.Builder = Notification.Builder(this, channelId)
            .setSmallIcon(R.mipmap.sym_def_app_icon) //drawable.splash)
            .setContentTitle("Service test")
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setPriority(Notification.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }
}