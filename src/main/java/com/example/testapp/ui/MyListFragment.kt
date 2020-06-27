package com.example.testapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.activity.Main2Activity
import com.example.testapp.R
import com.example.testapp.adapter.MyAddListAdapter
import com.example.testapp.dataclass.ShowFirebaseDataOnList
import com.example.testapp.moneyFormatToWon
import com.google.firebase.database.*
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.my_list_fragment.*

class MyListFragment : Fragment() {
    var datas = ArrayList<ShowFirebaseDataOnList>()
    private var firebasedb : FirebaseDatabase? = null
    private var databaseRef: DatabaseReference? = null
    private var storageRef : StorageReference? = null
    var enrolleruid : String? = null
    val loginuser = Main2Activity.loginuser.uid
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.my_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        databaseRef= FirebaseDatabase.getInstance().reference

        initStorage()
        Log.d("check1", "loginuser ${loginuser} enrolleruid : ${enrolleruid}")

        view.let {
            my_list_rv.adapter = MyAddListAdapter(datas)
            my_list_rv.layoutManager = LinearLayoutManager(requireContext())

            swipe_my_list_fragment.setOnRefreshListener {
                my_list_rv.adapter?.notifyDataSetChanged()

                swipe_my_list_fragment.isRefreshing = false
            }
        }



    }
    private fun initStorage(){
        firebasedb = FirebaseDatabase.getInstance()

        firebasedb!!.reference.child("info").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {

                datas.clear()
                for(data in p0.children ){
                    for(itemData in data.children){
                        enrolleruid = itemData.child("loginuid").getValue(String::class.java)
                        if(enrolleruid == loginuser) {
                            val category: String? =itemData.child("category").getValue(String::class.java)
                            val detailInfo =itemData.child("detailinfo").getValue(String::class.java)
                            val imgRes = itemData.child("imgRes").getValue(String::class.java)

                            //Log.d("check1", "enrolleruid : ${enrolleruid}")
                            val maxPrice = moneyFormatToWon(Integer.parseInt(itemData.child("maxPrice").getValue(String::class.java)!!))

                            val period = itemData.child("period").getValue(String::class.java)
                            val price = itemData.child("price").getValue(String::class.java)
                            val title = itemData.child("title").getValue(String::class.java)
                            val upprice = itemData.child("upprice").getValue(String::class.java)
                            datas.add(
                                ShowFirebaseDataOnList(
                                    imgRes!!, title!!, price!!, upprice!!, period!!,
                                    enrolleruid!!, maxPrice!!, detailInfo!!, category!!
                                )
                            )
                        }
                        //val msg = itemData.getValue(ShowFirebaseDataOnList::class.java)
                        //msg?.let { datas.add(it) }

                    }
                }
                //Log.d("check", "${message_rv.adapter}")
                //item_list_rv.adapter?.notifyDataSetChanged()
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.d("check", "failed to get database data")
            }
        })

    }
}