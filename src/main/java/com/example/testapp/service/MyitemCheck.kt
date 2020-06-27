package com.example.testapp.service

import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.startActivity
import com.example.testapp.dataclass.ShowFirebaseDataOnList
import com.firebase.ui.auth.AuthUI.getApplicationContext
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class MyitemCheck(val id:String?) {
    private lateinit var databaseReference: DatabaseReference
    private var storage: FirebaseStorage? = null

    fun check(){
        var datas = ArrayList<ShowFirebaseDataOnList>()
        if (id != null) {
            FirebaseDatabase.getInstance().reference
                .child("info")
                .child(id)
                .addListenerForSingleValueEvent(object: ValueEventListener{

                    override fun onCancelled(p0: DatabaseError) {
                        Log.d("check", "failed to get database data")
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        Log.d("check", "secsess to get database data")
                        datas.clear()
                        for(data in p0.children ){
                            val msg = data.getValue(ShowFirebaseDataOnList::class.java)
                            msg?.let { datas.add(it)}
                        }
                        compareDate(datas)
                    }
                })
        }

    }

    fun compareDate(datas: ArrayList<ShowFirebaseDataOnList>) {
        var tz: TimeZone
        val date = Date()
        val df: DateFormat = SimpleDateFormat("yyyy-MM-dd/HH:mm:ss")
        tz = TimeZone.getTimeZone("Asia/Seoul")
        df.setTimeZone(tz)

        df.format(date)

        for(i in datas){
            val closeTime =df.parse(i.period)
            val now = df.parse(df.format(date))
            if(now.compareTo(closeTime) >= 0){
                Log.d("check","${i.title}은 종료되어야한다!")
                databaseReference = FirebaseDatabase.getInstance().reference
                databaseReference.child("info").child(id!!).child(i.title!!).setValue(null)

            }
        }
     }
}