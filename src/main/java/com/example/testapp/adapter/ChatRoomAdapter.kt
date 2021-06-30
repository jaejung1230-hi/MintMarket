package com.example.testapp.adapter

import android.app.AlertDialog
import android.content.Intent
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.R
import com.example.testapp.activity.Main2Activity
import com.example.testapp.chatting.ChatActivity
import com.example.testapp.chatting.ChatDTO
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.chatmenu_list.view.*
import java.util.ArrayList

class ChatRoomAdapter (val datas: ArrayList<String>): RecyclerView.Adapter<ChatRoomAdapter.ViewHolder>(){
    private lateinit var databaseReference: DatabaseReference
    override fun getItemCount() = datas.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chatmenu_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindMessage(datas[position])
    }

    inner class ViewHolder(val item: View):RecyclerView.ViewHolder(item){
        fun bindMessage(message: String){
            item.chattingRoom_name.text = message
            item.setOnClickListener {
                val intent = Intent(item.context,ChatActivity::class.java)
                intent.putExtra("chattingroom_name",message)
                item.context.startActivity(intent)
            }
            item.setOnLongClickListener {
                val builder = AlertDialog.Builder(item.context, R.style.Theme_AppCompat_Light_Dialog)
                .setTitle("안내")
                .setMessage("채팅방을 삭제할까요?")
                    .setPositiveButton("확인") {dialog,which->
                        databaseReference = FirebaseDatabase.getInstance().reference
                        val chat : ChatDTO = ChatDTO("관리자", "상대방이 채팅을 종료했습니다.")
                        databaseReference.child("chat").child(message).push().setValue(chat)
                        databaseReference.child("user").child(Main2Activity.loginuser.uid).child("chatingroom").child(message).setValue(null)
                    }.setNegativeButton("취소") {dialog,which->
                }

                builder.show()


                return@setOnLongClickListener true
            }
        }

    }

}