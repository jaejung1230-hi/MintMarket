package com.example.testapp.chatting

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat.*
import java.util.ArrayList


class ChatActivity : AppCompatActivity() {
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseReference = firebaseDatabase.reference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        val chatRef = databaseReference.child("chat").child("test")

        var datas = ArrayList<ChatDTO>()
            chatRef.addListenerForSingleValueEvent(object: ValueEventListener {

                    override fun onCancelled(p0: DatabaseError) {
                        Log.d("check", "failed to get database data")
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        Log.d("check", "secsess to get database data")
                        datas.clear()
                        for(data in p0.children ){
                            val msg = data.getValue(ChatDTO::class.java)
                            msg?.let { datas.add(it)}
                        }
                    }
                })
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
                ChatDTO("testUser", message)
            chatRef.push().setValue(chat)
            message_edit.setText("")
        }
    }
}