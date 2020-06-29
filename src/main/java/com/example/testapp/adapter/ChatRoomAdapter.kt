package com.example.testapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.R
import com.example.testapp.chatting.ChatActivity
import kotlinx.android.synthetic.main.chatmenu_list.view.*
import java.util.ArrayList

class ChatRoomAdapter (val datas: ArrayList<String>): RecyclerView.Adapter<ChatRoomAdapter.ViewHolder>(){
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
        }

    }

}