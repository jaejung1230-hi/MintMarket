package com.example.testapp.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.testapp.R
import com.example.testapp.service.RealService
import com.example.testapp.service.UserPreference
import com.google.firebase.auth.FirebaseUser


class Main2Activity : AppCompatActivity() {
    companion object{
        lateinit var loginuser : FirebaseUser
    }
    val mpreference by lazy { (application as UserPreference).mpreferences }
    private var serviceIntent: Intent? = null

    val a = 10
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        loginuser = intent.getParcelableExtra<FirebaseUser>("user")!!
        mpreference.edit().putString("save_loginuser", loginuser.uid).apply()
        if (RealService.serviceIntent==null) {
            serviceIntent = Intent(this, RealService::class.java)
            startService(serviceIntent)
        } else {
            serviceIntent = RealService.serviceIntent //getInstance().getApplication()
            Toast.makeText(getApplicationContext(), "already", Toast.LENGTH_LONG).show()
        }


        val navController = findNavController((R.id.nav_host_fragment))
        val bundle = Bundle()
        bundle.putString("user",loginuser.uid)


        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            Log.d("NAVI","${destination.label}")
        }
        Toast.makeText(this,"${loginuser.displayName}님 환영합니다!",Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (serviceIntent!=null) {
            stopService(serviceIntent)
            serviceIntent = null
        }
    }

}
