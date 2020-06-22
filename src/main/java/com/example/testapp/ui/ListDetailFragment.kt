package com.example.testapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.testapp.Main2Activity
import com.example.testapp.R
import com.example.testapp.ShowFirebaseDataOnList
import com.example.testapp.detailDataList
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_list_detail.*
import kotlinx.android.synthetic.main.item_list.*
import kotlinx.android.synthetic.main.item_list.view.*
import java.io.Serializable


class ListDetailFragment : Fragment() {
    val loginuser = Main2Activity.loginuser.uid
    private var firebasedb : FirebaseDatabase? = null
    private var databaseRef: DatabaseReference? = null
    var datas = ArrayList<detailDataList>()
    private var myInt : HashMap<String?, String?>? =null
    var enrollerUid : String= ""
    var maxPrice = AddFragment.maxPrice
    private lateinit var databaseReference: DatabaseReference
    var participateNumber : String? = null //입찰 참가자 숫자

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_list_detail, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        firebasedb = FirebaseDatabase.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference

        //val item = activity?.intent?.getStringExtra("items")
        val bundle = this.arguments
        if (bundle != null) {
            myInt  = bundle.getSerializable("items") as HashMap<String?, String?>

            Picasso.with(context).load(myInt?.getValue("imgRes")).resize(400,200).into(detail_img)
            detail_price_tv.text = "제품 가격 : " + myInt?.getValue("price").toString() + "원"
            detail_info_tv.text ="제품 설명 \n " + myInt?.getValue("detailinfo").toString()
            detail_name_tv.text = "제품명 : " + myInt?.getValue("title").toString()
            category_tv.text = myInt?.getValue("category").toString()
            up_price_tv.text = "(상승단위 : " + myInt?.getValue("upprice").toString() +"원)"
        }

        getUserId()
        Log.d("check", "$loginuser & $enrollerUid")
        participate_btn.setOnClickListener {
            if(loginuser == enrollerUid) {
                Toast.makeText(requireContext(), "등록자는 입찰할수 없습니다.", Toast.LENGTH_LONG).show()
            }else{
                val finalMaxPrice = Integer.parseInt(myInt?.getValue("upprice").toString())+Integer.parseInt(maxPrice!!)
                val finalParticipantNum = Integer.parseInt(participateNumber!!)+1
                val result = detailDataList(finalParticipantNum.toString(), loginuser,finalMaxPrice.toString())

                databaseReference.child("enroller").child(myInt?.getValue("title").toString()).setValue(result)


            }
        }
    }

    private fun getUserId(){
        firebasedb!!.reference.child("enroller").child(myInt?.getValue("title").toString()).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                for(data in p0.children ){
                    val msg = data.getValue(detailDataList::class.java)
                    msg?.let { datas.add(it) }
                    for(data in datas){
                        enrollerUid = data.uid.toString()
                        participateNumber = data.count.toString()
                        //Log.d("check", "enrollerUid : ${enrollerUid}")
                    }
                }

            }

            override fun onCancelled(p0: DatabaseError) {
                Log.d("check", "failed to get database data")
            }
        })

    }

}