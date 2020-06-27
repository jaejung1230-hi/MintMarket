package com.example.testapp.service

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager

@Suppress("DEPRECATION")
open class UserPreference : Application(){
    val mpreferences : SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(this)
    }
}