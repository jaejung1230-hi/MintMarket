package com.example.testapp.ui

import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.res.ColorStateListInflaterCompat.inflate
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.adapter.ItemAdapter
import com.example.testapp.R
import com.example.testapp.dataclass.Category
import com.example.testapp.dataclass.ItemListDao
import com.example.testapp.dataclass.ShowFirebaseDataOnList
import com.example.testapp.dataclass.StuffInfo
import com.example.testapp.util.moneyFormatToWon
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.fragment_itemlist.*
import java.util.zip.Inflater

/**
 * A simple [Fragment] subclass.
 */
class ItemlistFragment : Fragment() {
    private var firebasedb : FirebaseDatabase? = null
    private var databaseRef: DatabaseReference? = null
    private var storageRef : StorageReference? = null

    var datas = ArrayList<ShowFirebaseDataOnList>()

    //private var itemRepository = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_itemlist, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        databaseRef = FirebaseDatabase.getInstance().reference
        storageRef = FirebaseStorage.getInstance().getReference("images")

        initStorage()

        view.let {

            item_list_rv.adapter = ItemAdapter(datas)

            item_list_rv.layoutManager = LinearLayoutManager(requireContext())
            //아래로 댕기면 새로고침
            swipe_home_list_fragment.setOnRefreshListener {
                item_list_rv.adapter?.notifyDataSetChanged()

                swipe_home_list_fragment.isRefreshing = false
            }

//품목 필터링
            cate_spinner.adapter = ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1, Category.values().map { it.category })
            cate_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val cate = Category.values()[position]
                    item_list_rv.adapter = ItemAdapter(datas)
                    Log.d("cate", "$cate")
                    if(cate.name == "ALL"){
                        item_list_rv.adapter = ItemAdapter(datas)

                    }else{
                        item_list_rv.adapter = ItemAdapter(datas.filter { it.category == cate.category} as ArrayList<ShowFirebaseDataOnList>)
                    }
                    item_list_rv.adapter?.notifyDataSetChanged()
                }
            }

            //item_list_rv.adapter?.notifyDataSetChanged()
        }



        NavigationUI.setupWithNavController(
            item_list_navigation,
            requireActivity().findNavController(R.id.nav_host_fragment)
        )


    }


    //파이어베이스에서 값 가져와서 화면에 뿌려주기
    private fun initStorage(){
        firebasedb = FirebaseDatabase.getInstance()

        firebasedb!!.reference.child("info").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {

                datas.clear()

                //Log.d("check", "itemlistFragment data ${data}")
                for(itemData in p0.children){
                    val category : String? = itemData.child("category").getValue(String::class.java)
                    val detailInfo = itemData.child("detailinfo").getValue(String::class.java)
                    val imgRes = itemData.child("imgRes").getValue(String::class.java)
                    val loginuid = itemData.child("loginuid").getValue(String::class.java)
                    val maxPrice  = moneyFormatToWon(
                        Integer.parseInt(
                            itemData.child("maxPrice").getValue(String::class.java).toString()
                        )
                    )
                    val period = itemData.child("period").getValue(String::class.java)
                    val price = itemData.child("price").getValue(String::class.java)
                    val title = itemData.child("title").getValue(String::class.java)
                    val upprice = itemData.child("upprice").getValue(String::class.java)

                    datas.add(
                        ShowFirebaseDataOnList(
                            imgRes!!, title!!,
                            price!!,
                            upprice!!,
                            period!!,
                            loginuid!!, maxPrice!!, detailInfo!!, category!!
                        )
                    )

                }

            }

            override fun onCancelled(p0: DatabaseError) {
                Log.d("check", "failed to get database data")
            }

        })

    }
}