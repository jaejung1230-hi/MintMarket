package com.example.testapp.chatting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.R
import kotlinx.android.synthetic.main.item_chat.view.*
import java.util.ArrayList

class ChatAdapter (val messages: ArrayList<ChatDTO>): RecyclerView.Adapter<ChatAdapter.ViewHolder>(){
    override fun getItemCount() = messages.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindMessage(messages[position])
    }

    inner class ViewHolder(val item: View):RecyclerView.ViewHolder(item){
        fun add(chatDTO : ChatDTO){
            item.user_name.text = chatDTO.userName
            item.message_contents.text = chatDTO.messageContent
        }
        fun bindMessage(message: ChatDTO){
            item.user_name.text = message.userName
            item.message_contents.text = message.messageContent
        }

    }

}