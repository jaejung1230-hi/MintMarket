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
import com.example.testapp.ui.ItemlistFragment.Companion.datas
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
    var detailDatas = ArrayList<detailDataList>()
    private var myInt : HashMap<String?, String?>? =null
    var enrollerUid : String?= null
    var maxPrice : String? = null
    private lateinit var databaseReference: DatabaseReference
    var participateNumber : String? = null //입찰 참가자 숫자
    var ispushed = true
    companion object{
        var CurMaxPrice :String? = null
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_list_detail, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


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

        getUserIdwithfixed()

        participate_btn.setOnClickListener {
            if(loginuser == enrollerUid) {
                Toast.makeText(requireContext(), "등록자는 입찰할수 없습니다.", Toast.LENGTH_LONG).show()
            }else{
                Log.d("check", "$loginuser & $enrollerUid")
                CurMaxPrice = (Integer.parseInt(myInt?.getValue("upprice").toString())+ Integer.parseInt(maxPrice!!)).toString()
                val CurParticipantNum = Integer.parseInt(participateNumber!!)+1
                val participateItemResult = detailDataList(CurParticipantNum.toString(), CurMaxPrice, enrollerUid)

                databaseReference.child("enroller").child(myInt?.getValue("title").toString()).setValue(participateItemResult)


            }
        }
    }

    private fun getUserIdwithfixed() {
        firebasedb = FirebaseDatabase.getInstance()
        val itemTitle = myInt?.getValue("title").toString()
        firebasedb!!.reference.child("enroller").child(itemTitle).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                enrollerUid = dataSnapshot.child("enrolleruid").getValue(String::class.java)
                participateNumber = dataSnapshot.child("count").getValue(String::class.java)
                maxPrice = dataSnapshot.child("maxprice").getValue(String::class.java)
                val result = detailDataList(participateNumber, maxPrice, enrollerUid)
                Log.d("check", "result $result")


            }

            override fun onCancelled(p0: DatabaseError) {
                Log.d("check", "failed to get database data")
            }
        })
    }


}