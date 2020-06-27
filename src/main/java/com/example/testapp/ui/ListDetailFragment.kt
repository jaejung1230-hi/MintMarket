package com.example.testapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.testapp.activity.Main2Activity
import com.example.testapp.R
import com.example.testapp.dataclass.ShowFirebaseDataOnList
import com.example.testapp.dataclass.detailDataList
import com.example.testapp.dataclass.participantsCheck
import com.example.testapp.moneyFormatToWon
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_list_detail.*


class ListDetailFragment : Fragment() {
    var participantsdatas = ArrayList<participantsCheck>()
    val loginuser = Main2Activity.loginuser.uid
    private var firebasedb : FirebaseDatabase? = null
    private var myInt : HashMap<String?, String?>? =null
    var enrollerUid : String?= null
    var maxPrice : String? = null
    private lateinit var databaseReference: DatabaseReference
    var participateNumber : String? = null //입찰 참가자 숫자
    private var CurMaxPrice :String? = null

    var checkInvoleInItem : String? = null
    private var IsAlreadyIn : Boolean = true
    private var curParticipantNum : Int = 0


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

            val price = "제품 가격 : " + moneyFormatToWon(Integer.parseInt(myInt?.getValue("price").toString())) + "원"
            val upPrice =  "(상승단위 : " +  moneyFormatToWon(Integer.parseInt(myInt?.getValue("upprice").toString())) +"원)"
            Picasso.with(context).load(myInt?.getValue("imgRes")).resize(400,200).into(detail_img)
            detail_price_tv.text = price
            detail_info_tv.text ="제품 설명 \n " + myInt?.getValue("detailinfo").toString()
            detail_name_tv.text = "제품명 : " + myInt?.getValue("title").toString()
            category_tv.text = myInt?.getValue("category").toString()
            up_price_tv.text = upPrice
        }

        getEnrollersItemInfo()



        participate_btn.setOnClickListener {
            if(loginuser == enrollerUid) {
                Toast.makeText(requireContext(), "등록자는 입찰할수 없습니다.", Toast.LENGTH_LONG).show()

            }else{
                val url = myInt?.getValue("imgRes").toString()
                val period = myInt?.getValue("period").toString()

                CurMaxPrice = (Integer.parseInt(myInt?.getValue("upprice").toString())+ Integer.parseInt(maxPrice!!)).toString()

                for (users in participantsdatas.iterator()){
                    Log.d("check", "dfsd $users")
                    if(IsAlreadyIn && loginuser!=users.participants){
                        curParticipantNum = Integer.parseInt(participateNumber!!)+1
                        IsAlreadyIn = false
                    }
                }


                val participateItemResult =
                    detailDataList(
                        curParticipantNum.toString(),
                        CurMaxPrice,
                        enrollerUid,
                        url,
                        period
                    )
                val itemTitle = myInt?.getValue("title").toString()

                databaseReference.child("enroller").child(itemTitle).setValue(participateItemResult)
                databaseReference.child("enroller").child(itemTitle).child("participants").child("user").setValue(loginuser)
                databaseReference.child("info").child(enrollerUid!!).child(itemTitle).child("maxPrice").setValue(CurMaxPrice)
                findNavController().navigate(R.id.itemlistFragment)
            }
        }
    }

    private fun getEnrollersItemInfo() {
        firebasedb = FirebaseDatabase.getInstance()
        val itemTitle = myInt?.getValue("title").toString()
        firebasedb!!.reference.child("enroller").child(itemTitle).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {



                enrollerUid = dataSnapshot.child("enrolleruid").getValue(String::class.java)

                participateNumber = dataSnapshot.child("count").getValue(String::class.java)


                participate_number_tv?.text = getString(R.string.num, participateNumber)




                maxPrice = dataSnapshot.child("maxprice").getValue(String::class.java)

                checkInvoleInItem = dataSnapshot.child("participants").child("user").getValue(String::class.java)
                participantsdatas.add(participantsCheck(checkInvoleInItem))
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.d("check", "failed to get database data")
            }
        })



    }


}