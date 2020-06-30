package com.example.testapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.View.inflate
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.R
import com.example.testapp.adapter.ItemAdapter
import com.example.testapp.dataclass.Category
import com.example.testapp.dataclass.ShowFirebaseDataOnList
import com.example.testapp.util.moneyFormatToWon
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.fragment_itemlist.*
import kotlinx.android.synthetic.main.layout_spinner.*


/**
 * A simple [Fragment] subclass.
 */

class ItemlistFragment : Fragment() {
    private var firebasedb: FirebaseDatabase? = null
    private var databaseRef: DatabaseReference? = null
    private var storageRef: StorageReference? = null

    var datas = ArrayList<ShowFirebaseDataOnList>()

    //private var itemRepository = null
    @SuppressLint("ResourceType")
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        setHasOptionsMenu(true)
        //var rootView = inflater.inflate(R.layout.layout_spinner, container, false)
        //rootView.findViewById<View>(R.id.cate_spinner)

        return inflater.inflate(R.layout.fragment_itemlist, container, false)
        //return rootView
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)




        databaseRef = FirebaseDatabase.getInstance().reference
        storageRef = FirebaseStorage.getInstance().getReference("images")


        initStorage()

        view.let {

            item_list_rv.adapter = ItemAdapter(datas)

            item_list_rv.layoutManager = LinearLayoutManager(requireContext())


            swipe_home_list_fragment.setOnRefreshListener {
                item_list_rv.adapter?.notifyDataSetChanged()

                swipe_home_list_fragment.isRefreshing = false
            }
        }



        NavigationUI.setupWithNavController(
            item_list_navigation,
            requireActivity().findNavController(R.id.nav_host_fragment)
        )
    }

    private fun initStorage() {
        firebasedb = FirebaseDatabase.getInstance()

        firebasedb!!.reference.child("info").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {

                datas.clear()

                //Log.d("check", "itemlistFragment data ${data}")
                for (itemData in p0.children) {
                    Log.d("check", "itemData : ${itemData}")
                    val category: String? = itemData.child("category").getValue(String::class.java)
                    val detailInfo = itemData.child("detailinfo").getValue(String::class.java)
                    val imgRes = itemData.child("imgRes").getValue(String::class.java)
                    val loginuid = itemData.child("loginuid").getValue(String::class.java)
                    val maxPrice = moneyFormatToWon(
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
                    item_list_rv.adapter = ItemAdapter(datas)
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.d("check", "failed to get database data")
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.cate, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //item_list_rv.adapter = ItemAdapter(datas)
        when(item.itemId){

            R.id.cate_item1->{item_list_rv.adapter = ItemAdapter(datas)}
            R.id.cate_item2->{item_list_rv.adapter =
                ItemAdapter(datas.filter { it.category == "생활/가전" } as ArrayList<ShowFirebaseDataOnList>)}
            R.id.cate_item3->{item_list_rv.adapter =
                ItemAdapter(datas.filter { it.category == "가구/인테리어" } as ArrayList<ShowFirebaseDataOnList>)}
            R.id.cate_item4->{item_list_rv.adapter =
                ItemAdapter(datas.filter { it.category == "의류" } as ArrayList<ShowFirebaseDataOnList>)}
            R.id.cate_item5->{item_list_rv.adapter =
                ItemAdapter(datas.filter { it.category == "게임/취미" } as ArrayList<ShowFirebaseDataOnList>)}
            R.id.cate_item6->{item_list_rv.adapter =
                ItemAdapter(datas.filter { it.category == "유아/완구" } as ArrayList<ShowFirebaseDataOnList>)}
            R.id.cate_item7->{item_list_rv.adapter =
                ItemAdapter(datas.filter { it.category == "티켓/음반" } as ArrayList<ShowFirebaseDataOnList>)}
            R.id.cate_item8->{item_list_rv.adapter =
                ItemAdapter(datas.filter { it.category == "기타" } as ArrayList<ShowFirebaseDataOnList>)}
        }
        return true
    }
}



