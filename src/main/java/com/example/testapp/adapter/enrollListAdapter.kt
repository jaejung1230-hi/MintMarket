package com.example.testapp.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.R
import com.example.testapp.dataclass.ItemListDao
import com.example.testapp.dataclass.ShowFirebaseDataOnList
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list.view.*

class EnrollListAdapter(val items: ArrayList<ItemListDao>) : RecyclerView.Adapter<EnrollListAdapter.ViewHolder>() {
    val data = HashMap<String?, String?>()
    override fun getItemCount() = items.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)

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


        }
    }
}
