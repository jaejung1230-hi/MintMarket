package com.example.testapp.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.*
import com.example.testapp.R
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_itemlist.*
import kotlinx.android.synthetic.main.fragment_itemlist.view.*
import org.w3c.dom.Comment
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URI

/**
 * A simple [Fragment] subclass.
 */
class ItemlistFragment : Fragment() {
    //var list = view?.findViewById(R.id.bottom_navigation) as BottomNavigationView
    private var firebasedb : FirebaseDatabase? = null
    private var databaseRef: DatabaseReference? = null
    private var storageRef : StorageReference? = null
    private var itemArrayList = ArrayList<StuffInfo>()
    private var datas = ArrayList<ShowFirebaseDataOnList>()
    //private var itemRepository = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_itemlist, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        databaseRef= FirebaseDatabase.getInstance().reference
        storageRef = FirebaseStorage.getInstance().getReference("images")

        initStorage()
        //val items = (requireContext() as Main2Activity).itemRepository

        view?.let{
            it.message_rv.adapter = ItemAdapter(datas)
            it.message_rv.layoutManager = LinearLayoutManager(requireContext())
        }
        NavigationUI.setupWithNavController(
            bottom_navigation1,
            requireActivity().findNavController(R.id.nav_host_fragment)
        )
    }

    private fun initStorage() {

        firebasedb = FirebaseDatabase.getInstance()


        firebasedb!!.reference.child("info").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {

                datas.clear()
                for(data in p0.children ){
                        val msg = data.getValue(ShowFirebaseDataOnList::class.java)

                        msg?.let { datas.add(it) }

                }

            }

            override fun onCancelled(p0: DatabaseError) {
                Log.d("check", "failed to get database data")
            }
        })
        //Log.d("check", datas.getValue("price"))
        val fileName = "1587020415.jpg"
        storageRef!!.child(fileName).downloadUrl.addOnSuccessListener(OnSuccessListener {url ->

        })
    }
}