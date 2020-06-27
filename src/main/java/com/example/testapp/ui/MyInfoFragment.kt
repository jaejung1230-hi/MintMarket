package com.example.testapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils.loadAnimation
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.testapp.activity.Main2Activity
import com.example.testapp.activity.MainActivity
import com.example.testapp.R

import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_my_info.*

class MyInfoFragment : Fragment() {
    var isOpen = false

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
            my_info_navigation,
            requireActivity().findNavController(R.id.nav_host_fragment)
        )

        val fabOpen = loadAnimation(context, R.anim.fab_open)
        val fabClose = loadAnimation(context, R.anim.fab_close)
        val fabRClockwise = loadAnimation(context, R.anim.rotate_clockwise)
        val fabRAntiClockwise = loadAnimation(context, R.anim.rotate_anticlockwise)

        addBtn.setOnClickListener{
            if(isOpen){
                wannasell.startAnimation(fabClose)
                wannabuy.startAnimation(fabClose)
                gotochat.startAnimation(fabClose)
                addBtn.startAnimation(fabRClockwise)

                isOpen = false
            } else{
                wannasell.startAnimation(fabOpen)
                wannabuy.startAnimation(fabOpen)
                gotochat.startAnimation(fabOpen)
                addBtn.startAnimation(fabRAntiClockwise)

                wannasell.isClickable
                wannabuy.isClickable
                gotochat.isClickable

                isOpen = true

                wannabuy.setOnClickListener{
                    findNavController().navigate(R.id.myListFragment)
                }
                wannasell.setOnClickListener {
                    findNavController().navigate(R.id.myenrollFragment)

                }
                gotochat.setOnClickListener {
                   findNavController().navigate(R.id.chatFragment)
                }
            }
        }

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