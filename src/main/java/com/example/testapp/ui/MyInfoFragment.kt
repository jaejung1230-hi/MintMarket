package com.example.testapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.testapp.Main2Activity
import com.example.testapp.MainActivity
import com.example.testapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_my_info.*
import kotlinx.android.synthetic.main.my_list_fragment.*

class MyInfoFragment : Fragment() {


    private lateinit var auth: FirebaseAuth
    var loginuser = Main2Activity.loginuser.displayName
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_my_info, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        NavigationUI.setupWithNavController(
            bottom_navigation3,
            requireActivity().findNavController(R.id.nav_host_fragment)
        )


        userId.text = loginuser.toString()

        logout.setOnClickListener{

           FirebaseAuth.getInstance().signOut()



            //activity?.finish()
            //findNavController().navigate(R.id.itemlistFragment)
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)

        }
    }

}