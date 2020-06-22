package com.example.testapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.testapp.ui.ItemlistFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main2.*


class Main2Activity : AppCompatActivity() {

    companion object{
        lateinit var loginuser : FirebaseUser
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        Log.d("check", "main2activity")
        loginuser = intent.getParcelableExtra<FirebaseUser>("user")

        val navController = findNavController((R.id.nav_host_fragment))

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            Log.d("NAVI","${destination.label}")
        }
        Toast.makeText(this,"${loginuser.displayName}님 환영합니다!",Toast.LENGTH_SHORT).show()
    }

}
