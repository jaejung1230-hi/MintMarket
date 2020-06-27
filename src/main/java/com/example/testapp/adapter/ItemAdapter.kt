package com.example.testapp.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.R
import com.example.testapp.dataclass.ShowFirebaseDataOnList
import com.squareup.picasso.Picasso
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
                data["period"] = items.period



                val bundle = Bundle()
                bundle.putSerializable("items", data)

                Navigation.findNavController(item).navigate(R.id.action_itemlistFragment_to_listDetailFragment, bundle)
            }

        }
    }

}