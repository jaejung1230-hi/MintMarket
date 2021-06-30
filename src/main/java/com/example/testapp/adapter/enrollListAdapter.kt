package com.example.testapp.adapter

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.R
import com.example.testapp.activity.Main2Activity
import com.example.testapp.chatting.ChatDTO
import com.example.testapp.dataclass.ItemListDao
import com.example.testapp.dataclass.ShowFirebaseDataOnList
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list.view.*

class EnrollListAdapter(val items: ArrayList<ItemListDao>) : RecyclerView.Adapter<EnrollListAdapter.ViewHolder>() {
    val data = HashMap<String?, String?>()
    override fun getItemCount() = items.size
    private lateinit var databaseReference: DatabaseReference
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rootView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)

        return ViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items[position])
    }

    inner class ViewHolder(val item: View) : RecyclerView.ViewHolder(item) {

        fun bindItem(items: ItemListDao) {
            item.name_tv.text = items.title
            item.preiod_tv.text = items.itemperiod
            item.maxprice_tv.text = "최고가: " + items.maxprice + "원"
            Picasso.with(item.context).load(items.imgRes).resize(100, 100).into(item.img_res)

            item.setOnLongClickListener {
                val builder =
                    AlertDialog.Builder(item.context, R.style.Theme_AppCompat_Light_Dialog)
                        .setTitle("안내")
                        .setMessage("입찰했던 물건을 목록에서 삭제할까요?")
                        .setPositiveButton("확인") { dialog, which ->
                            databaseReference = FirebaseDatabase.getInstance().reference
                            databaseReference.child("ListOfItemEnroller").child("participants")
                                .child(Main2Activity.loginuser.uid)
                                .child(items.title!!).setValue(null)
                        }.setNegativeButton("취소") { dialog, which ->
                        }

                builder.show()


                return@setOnLongClickListener true

            }
        }
    }
}