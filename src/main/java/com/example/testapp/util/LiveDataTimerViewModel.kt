package com.example.testapp.util

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class LiveDataTimerViewModel : ViewModel(){
    companion object{
        private const val DELAY_SECOND =1000.toLong()
        private const val PERIOD_SECOND =1000.toLong()
        const val TAG = "LIVEDATA"
    }

    private var nowTime = MutableLiveData<Date>()

    fun getNowTime(): MutableLiveData<Date>{
            return nowTime
    }

    init{
        val timer = Timer()
        timer.scheduleAtFixedRate(object: TimerTask() {
            override fun run() {
                var tz: TimeZone
                val date = Date()
                val df : DateFormat = SimpleDateFormat("yyyy-MM-dd/HH:mm:ss")
                tz = TimeZone.getTimeZone("Asia/Seoul")
                df.setTimeZone(tz)
                val nowStr: String = df.format(date)
                val now = df.parse(nowStr)
                Log.d("check","run ${now}")
                nowTime.postValue(now)
            }
        }, DELAY_SECOND, PERIOD_SECOND)
    }
}