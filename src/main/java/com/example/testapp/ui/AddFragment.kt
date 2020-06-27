package com.example.testapp.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore.Images.Media
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.loader.content.CursorLoader
import androidx.navigation.fragment.findNavController
import com.example.testapp.*
import com.example.testapp.activity.Main2Activity
import com.example.testapp.dataclass.Category
import com.example.testapp.dataclass.Period
import com.example.testapp.dataclass.StuffInfo
import com.example.testapp.dataclass.detailDataList
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.item_list.*
import java.io.File
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
open class AddFragment : Fragment() {
    private val OPEN_GALLERY = 1
    private val REQUEST_CAMERA = 1

    private var currentImageUri: Uri? = null
    private lateinit var databaseReference: DatabaseReference
    private var storage: FirebaseStorage? = null
    private var imagePath : String? = null
    companion object{var maxPrice : String? = null}
    val loginuser = Main2Activity.loginuser.uid
    var Datemsg : String?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root =  inflater.inflate(R.layout.fragment_add, container, false)
        setHasOptionsMenu(true)
        return root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        storage = FirebaseStorage.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference

        period_spinner.adapter = ArrayAdapter<String>(requireContext(),android.R.layout.simple_list_item_1, Period.values().map{it.period})
        categorys_spinner.adapter = ArrayAdapter<String>(requireContext(),android.R.layout.simple_list_item_1, Category.values().map{it.category})

        upload_image.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, OPEN_GALLERY)
        }
        send_info_btn.setOnClickListener {
            val itemPrice : String = start_cost_edit?.text.toString()
            val itemName :String = title_edit?.text.toString()
            val itemUpPrice: String = jump_cost_edit?.text.toString()
            val itemDetailInfo:String = detailinfo_edit?.text.toString()
            if(itemPrice.isBlank() || itemName.isBlank() || itemUpPrice.isBlank() || itemDetailInfo.isBlank()){
                Toast.makeText(requireContext(), "내용을 입력하세요", Toast.LENGTH_LONG).show()
            }else {
                upload(imagePath)
                findNavController().navigate(R.id.itemlistFragment)
            }

        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun upload (uri : String?) {
        //날짜 계산
        var tz: TimeZone
        val cal = Calendar.getInstance()
        cal.time = Date()
        val df : DateFormat = SimpleDateFormat("yyyy-MM-dd/HH:mm:ss")
        tz = TimeZone.getTimeZone("Asia/Seoul")
        df.setTimeZone(tz)
        when (period_spinner.selectedItem) {
            "1일" -> {
                cal.add(Calendar.DATE, 1)
            }
            "2일" -> {
                cal.add(Calendar.DATE, 2)
            }
            "3일" -> {
                cal.add(Calendar.DATE, 3)
            }
        }
        val storageRef : StorageReference? = storage?.getReferenceFromUrl("gs://test-3578b.appspot.com")


        val itemPrice : String = start_cost_edit?.text.toString()
        val itemName :String = title_edit?.text.toString()
        val itemUpPrice: String = jump_cost_edit?.text.toString()
        val itemPeriod:String = df.format(cal.time).toString()
        val itemCategory:String = categorys_spinner?.selectedItem.toString()
        val itemDetailInfo:String = detailinfo_edit?.text.toString()
        maxPrice = itemPrice

        val file: Uri = Uri.fromFile(File(uri))
        Log.d("check", "url ${file.lastPathSegment}")
        val riversRef = storageRef?.child("images/" + "${file.lastPathSegment}")
        val uploadTask = riversRef?.putFile(file)
///storage/emulated/0/DCIM/Camera/IMG_20200627_033321.jpg
        uploadTask?.continueWith {
            if (!it.isSuccessful) {
                Log.d("check", "upload failed")
            }
            riversRef.downloadUrl
        }?.addOnCompleteListener {
            if (it.isSuccessful) {
                val tempURL = it.result!!.addOnSuccessListener { task ->
                    val url = task.toString().substring(0, task.toString().indexOf("&token"))

                    val result = StuffInfo(
                        url, itemName, itemPrice, itemUpPrice,
                        itemPeriod, itemCategory, itemDetailInfo, loginuser, maxPrice!!
                    )
                    val tmp = detailDataList(
                        "0",
                        maxPrice!!,
                        loginuser,
                        url,
                        itemPeriod
                    )

                    //val enroll = detailDataList(loginuser)
                    databaseReference.child("info").child("$loginuser/$itemName").setValue(result)
                    databaseReference.child("enroller").child(itemName).setValue(tmp)

                }

            }
        }

    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            if (requestCode == OPEN_GALLERY) {
                currentImageUri = data?.data
                try {
                    imagePath = data?.data?.let { getPath(it) }
                   val BitmapImg =
                       Media.getBitmap(activity?.contentResolver, currentImageUri)
                    upload_image.setImageBitmap(BitmapImg)
                    val file = File(data?.data?.let { getPath(it) })
                    if(!file.exists())
                        file.mkdirs()
                    img_res.setImageURI(Uri.fromFile(file))


                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
    fun getPath(uri : Uri) : String {
        val proj:Array<String>  = arrayOf(Media.DATA)
        val cursorLoader  = CursorLoader(requireContext(),uri,proj,null,null,null)

        val cursor  = cursorLoader.loadInBackground()!!
        val index : Int = cursor.getColumnIndexOrThrow(Media.DATA)

        cursor.moveToFirst()

        return cursor.getString(index)
    }





    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.backmaenu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.back -> {
                val fragmentManager: FragmentManager = requireActivity().supportFragmentManager


                findNavController().navigate(R.id.itemlistFragment)
                //fragmentManager.popBackStackImmediate()
                return super.onOptionsItemSelected(item)
            }
            else -> {
                return false
            }
        }
    }


/*    override fun onRequestPermissionsResult( requestCode: Int,        permissions: Array<out String>,        grantResults: IntArray    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == REQUEST_CAMERA){
            if(grantResults.get(0) == PackageManager.PERMISSION_GRANTED)

        }
    }*/
}
