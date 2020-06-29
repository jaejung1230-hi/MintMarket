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
import com.example.testapp.dataclass.MyParticipateItemList
import com.example.testapp.dataclass.detailDataList
import com.example.testapp.dataclass.participantsCheck
import com.example.testapp.util.moneyFormatToWon
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
    private var IsAlreadyIn : Boolean = false

    var curParticipantNum : Int = 0

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

            val price = "제품 가격 : " + moneyFormatToWon(
                Integer.parseInt(myInt?.getValue("price").toString())
            ) + "원"
            val upPrice =  "(상승단위 : " + moneyFormatToWon(
                Integer.parseInt(myInt?.getValue("upprice").toString())
            ) +"원)"
            Picasso.with(context).load(myInt?.getValue("imgRes")).resize(400,200).into(detail_img)
            detail_price_tv.text = price
            detail_info_tv.text ="제품 설명 \n " + myInt?.getValue("detailinfo").toString()
            detail_name_tv.text = "제품명 : " + myInt?.getValue("title").toString()
            category_tv.text = myInt?.getValue("category").toString()
            up_price_tv.text = upPrice
        }

        getEnrollersItemInfo()


        participate_btn.setOnClickListener {
            Log.d("check3", "loginuid : $loginuser ,,,, enrollerUid : $enrollerUid")
            if(loginuser == enrollerUid) {
                Toast.makeText(requireContext(), "등록자는 입찰할수 없습니다.", Toast.LENGTH_LONG).show()

            }else{

                val url = myInt?.getValue("imgRes").toString()
                val period = myInt?.getValue("period").toString()
                val itemTitle = myInt?.getValue("title").toString()
                CurMaxPrice = (Integer.parseInt(myInt?.getValue("upprice").toString())+
                        Integer.parseInt(maxPrice!!)).toString()

                for (users in participantsdatas.iterator()){
                    if(users.participants?.contains(loginuser)!!){
                        IsAlreadyIn = true
                    }

                }

                //이미 입찰목록에 있으면 몇명이 입찰중인지 알려주는 count 변수에 값을 증가시키지 않는다.

                curParticipantNum = Integer.parseInt(participateNumber!!)+1


                //입찰버튼 누르면 내 입찰 목록으로 가져오기 위해 만들어주는 트리
                val myParticipateItem =
                    MyParticipateItemList(
                        itemTitle,
                        CurMaxPrice,
                        url,
                        period,
                        loginuser

                    )
                databaseReference.child("ListOfItemEnroller")
                    .child("participants/$loginuser").child(itemTitle).setValue(myParticipateItem)

                //메인 리스트 뷰 최대 값 바꿔주기
                databaseReference.child("info").child(itemTitle).child("maxPrice").setValue(CurMaxPrice)
                databaseReference.child("info").child(itemTitle).child("count").setValue(curParticipantNum.toString())
                databaseReference.child("info").child(itemTitle).child("enrolleruid").setValue(loginuser)

                findNavController().navigate(R.id.itemlistFragment)
            }
        }
    }

    //누가 등록했는지 값을 가져오게한다.
    private fun getEnrollersItemInfo() {
        firebasedb = FirebaseDatabase.getInstance()
        val itemTitle = myInt?.getValue("title").toString()
        firebasedb!!.reference.child("info").child(itemTitle).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.d("check2", "$dataSnapshot")

                    enrollerUid = dataSnapshot.child("loginuid").getValue(String::class.java)
                    participateNumber = dataSnapshot.child("count").getValue(String::class.java)
                    maxPrice = dataSnapshot.child("maxPrice").getValue(String::class.java)
                    participate_number_tv?.text = getString(R.string.num, participateNumber)
                  Log.d("check3", "loginuid1 : $loginuser ,,,, enrollerUid1 : $enrollerUid")
            }
            override fun onCancelled(p0: DatabaseError) {
                Log.d("check", "failed to get database data")
            }
        })
    }



}