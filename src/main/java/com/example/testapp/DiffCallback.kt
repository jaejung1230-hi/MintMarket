package com.example.testapp

import androidx.recyclerview.widget.DiffUtil
import com.google.firebase.database.annotations.Nullable

class DiffCallback(private val oldList: ArrayList<ShowFirebaseDataOnList>,
private val newList: ArrayList<ShowFirebaseDataOnList>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].title == newList.get(newItemPosition).title
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        val maxPrice =  oldList[oldPosition]
        val maxPrice1 = newList[newPosition]

        return maxPrice == maxPrice1
    }

    @Nullable
    override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? {
        return super.getChangePayload(oldPosition, newPosition)
    }
}