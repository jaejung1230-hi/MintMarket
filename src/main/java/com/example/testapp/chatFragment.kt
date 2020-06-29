package com.example.testapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.activity.Main2Activity
import com.example.testapp.ui.ItemlistFragment
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_chat.*


class chatFragment : Fragment() {
    private var firebasedb : FirebaseDatabase? = null
    private var databaseRef: DatabaseReference? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        databaseRef= FirebaseDatabase.getInstance().reference
        firebasedb = FirebaseDatabase.getInstance()
        var datas = ArrayList<String>()
        firebasedb!!.reference.child("user").child(Main2Activity.loginuser.uid).child("chatingroom")
            .addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                datas.clear()
                for(itemData in snapshot.children){
                    itemData.getValue(String::class.java)?.let { datas.add(it) }
                }
                chatroom_list.adapter = ChatRoomAdapter(datas)
                chatroom_list.layoutManager = LinearLayoutManager(requireContext())
            }
        })


    }
}
