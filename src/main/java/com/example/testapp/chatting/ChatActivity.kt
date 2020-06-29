package com.example.testapp.chatting

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.R
import com.example.testapp.activity.Main2Activity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat.*
import java.util.ArrayList


class ChatActivity : AppCompatActivity() {
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseReference = firebaseDatabase.reference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        val path = intent.getStringExtra("chattingroom_name")
        val chatRef = databaseReference.child("chat").child(path)

        var datas = ArrayList<ChatDTO>()

        var chatAdapter = ChatAdapter(datas)
        message_list.adapter = chatAdapter
        message_list.layoutManager = LinearLayoutManager(this)

        chatRef.addChildEventListener(object : ChildEventListener{
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    val chatDTO : ChatDTO = snapshot.getValue(
                        ChatDTO::class.java)!!
                    datas.add(chatDTO)
                    chatAdapter.notifyDataSetChanged();
                }

                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val chatDTO : ChatDTO = snapshot.getValue(
                        ChatDTO::class.java)!!
                    datas.add(chatDTO)
                    chatAdapter.notifyDataSetChanged();
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    TODO("Not yet implemented")
                }

            })
        send_message_btn.setOnClickListener {
            val message = message_edit.getText().toString()
            val chat : ChatDTO =
                ChatDTO(Main2Activity.loginuser.displayName!!, message)
            chatRef.push().setValue(chat)
            message_edit.setText("")
        }
    }
}