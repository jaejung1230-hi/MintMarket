package com.example.testapp

import androidx.lifecycle.LiveData
import com.google.firebase.database.*

open class FirebaseQueryLiveData : LiveData<DataSnapshot>{

    constructor(path : String){
        query = FirebaseDatabase.getInstance().getReference(path)
    }
    constructor(ref : DatabaseReference){
        query = ref
    }
    constructor(query: Query){
        this.query = query
    }
    private val listener = MyValueEventListener()

    inner class MyValueEventListener : ValueEventListener{
        override fun onCancelled(p0: DatabaseError) {

        }

        override fun onDataChange(p0: DataSnapshot) {
            value = p0
        }

    }

    private val query: Query

    override fun onActive() {
        query.addValueEventListener(listener)
    }


}