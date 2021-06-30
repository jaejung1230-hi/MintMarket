package com.example.testapp.service

import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.startActivity
import com.example.testapp.chatting.ChatDTO
import com.example.testapp.dataclass.ShowFirebaseDataOnList
import com.example.testapp.dataclass.StuffInfo
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
        var datas = ArrayList<StuffInfo>()
        if (id != null) {
            FirebaseDatabase.getInstance().reference
                .child("info")
                .addListenerForSingleValueEvent(object: ValueEventListener{

                    override fun onCancelled(p0: DatabaseError) {
                        Log.d("check", "failed to get database data")
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        Log.d("check", "secsess to get database data")
                        datas.clear()
                        for(data in p0.children ){
                            val msg = data.getValue(StuffInfo::class.java)
                            if (msg != null) {
                                if(msg.loginuid == id){datas.add(msg)}
                            }
                        }
                        compareDate(datas)
                    }
                })
        }

    }

    fun compareDate(datas: ArrayList<StuffInfo>) {
        var tz: TimeZone
        val date = Date()
        val df: DateFormat = SimpleDateFormat("yyyy-MM-dd/HH:mm:ss")
        tz = TimeZone.getTimeZone("Asia/Seoul")
        df.setTimeZone(tz)

        Log.d("check","${df.parse(df.format(date))}")
        for(i in datas){
            val closeTime =df.parse(i.period)
            val now = df.parse(df.format(date))
            if(now.compareTo(closeTime) >= 0){
                databaseReference = FirebaseDatabase.getInstance().reference
                databaseReference.child("info").child(i.title!!).setValue(null)
               if(i.loginuid!! != i.enrolleruid!!){
                   Log.d("check","${i.title}은 종료되어야한다!")
                   val chat1 : ChatDTO = ChatDTO("관리자", "${i.title}로부터 생성")
                   val chat2 : ChatDTO = ChatDTO("관리자", "${i.maxPrice}로 최종낙찰")
                   val chat3 : ChatDTO = ChatDTO("관리자", "일방적 계약파기는 자제")
                   databaseReference.child("chat").child(i.title!!).push().setValue(chat1)
                   databaseReference.child("chat").child(i.title!!).push().setValue(chat2)
                   databaseReference.child("chat").child(i.title!!).push().setValue(chat3)
                   databaseReference.child("user").child(i.loginuid!!).child("chatingroom").child(i.title!!).setValue(i.title!!)
                   databaseReference.child("user").child(i.enrolleruid!!).child("chatingroom").child(i.title!!).setValue(i.title!!)
                   databaseReference.child("info").child(i.title!!).setValue(null)

               } else{
                   val chat1 : ChatDTO = ChatDTO("관리자", "${i.title}로부터 생성")
                   val chat2 : ChatDTO = ChatDTO("관리자", "${i.title}는 판매되지 않았습니다..")
                   databaseReference.child("chat").child(i.title!!).push().setValue(chat1)
                   databaseReference.child("chat").child(i.title!!).push().setValue(chat2)
                   databaseReference.child("user").child(i.loginuid!!).child("chatingroom").child(i.title!!).setValue(i.title!!)
                   databaseReference.child("info").child(i.title!!).setValue(null)

               }

            }
        }
     }
}