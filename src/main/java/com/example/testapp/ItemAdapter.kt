package com.example.testapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list.view.*

class ItemAdapter(val items: ArrayList<ShowFirebaseDataOnList>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>(){

    override fun getItemCount() = items.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)

        return ViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items[position])
    }


    inner class ViewHolder(val item: View) : RecyclerView.ViewHolder(item) {
        fun bindItem(items: ShowFirebaseDataOnList){
            item.name_tv.text = items.title
            item.price_tv.text = items.price.toString()
            item.upprice_tv.text ="상승가: " + items.upprice.toString()
           // item.preiod_tv.text = items.period
            //item.preiod_tv.text = items.category
            //item.img_res.setImageResource(items.imgRes)
            //Picasso.with(item.context).load(items.imgRes).into(item.img_res)

/*
            item.setOnClickListener{
                val bundle = Bundle().apply { putInt("idx", position) }

                Navigation.findNavController(item).navigate(R.id.action_itemlistFragment_to_listdetailFragment, bundle)
            }
*/
        }
    }
}