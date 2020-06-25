package com.example.testapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide.get
import com.example.testapp.ui.ItemlistFragment
import com.example.testapp.ui.ListDetailFragment
import com.google.android.gms.actions.ItemListIntents
import com.squareup.picasso.Picasso
import io.grpc.okhttp.internal.Platform.get
import kotlinx.android.synthetic.main.fragment_add.view.*
import kotlinx.android.synthetic.main.item_list.view.*

class ItemAdapter(val items: ArrayList<ShowFirebaseDataOnList>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>(){
    val data = HashMap<String?, String?>()
    override fun getItemCount() = items.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)

        return ViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bindItem(items[position])

    }

    fun setData(newItems: ArrayList<ShowFirebaseDataOnList>){
        val diffCallback = DiffCallback(items, newItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }


    inner class ViewHolder(val item: View) : RecyclerView.ViewHolder(item) {

        fun bindItem(items: ShowFirebaseDataOnList){
            item.name_tv.text = items.title
            item.preiod_tv.text = items.period
            item.maxprice_tv.text = "최고가: " + items.maxPrice + "원"
            Picasso.with(item.context).load(items.imgRes).resize(100,100).into(item.img_res)



            item.setOnClickListener{
                data["imgRes"] = items.imgRes
                data["title"] = items.title
                data["price"] = items.price
                data["upprice"] = items.upprice
                data["maxprice"] = items.maxPrice
                data["detailinfo"] = items.detailinfo
                data["category"] = items.category
                data["loginuid"] = items.loginuid



                val bundle = Bundle()
                bundle.putSerializable("items", data)

                Navigation.findNavController(item).navigate(R.id.action_itemlistFragment_to_listDetailFragment, bundle)
            }

        }
    }

}