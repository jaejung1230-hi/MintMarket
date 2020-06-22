package com.example.testapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.testapp.Main2Activity
import com.example.testapp.R
import kotlinx.android.synthetic.main.fragment_itemlist.*
import kotlinx.android.synthetic.main.fragment_my_info.*
import kotlinx.android.synthetic.main.my_list_fragment.*

class MyListFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.my_list_fragment, container, false)




    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        NavigationUI.setupWithNavController(
            bottom_navigation2,
            requireActivity().findNavController(R.id.nav_host_fragment)
        )
    }

}