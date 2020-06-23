package com.example.testapp

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.StrictMode
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_stuff_registration.*


class StuffRegistrationActivity : AppCompatActivity() {
    val REQUEST_CODE = 0
    var IMG_FLAG = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stuff_registration)
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build()
        )
        stuff_image.setOnClickListener {
            intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            startActivityForResult(intent, REQUEST_CODE)
        }
        ///////////////////////////////////////////////////////
        period_spinner.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Period.values().map{it.period})
        category_spinner.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Category.values().map { it.category })

        ///////////////////////////////////////////////////////
        send_info_btn.setOnClickListener {
            var str = stuff_image.getDrawable().toString().split("@")
            if(IMG_FLAG == 0){
                Toast.makeText(this,"상품의 사진을 반드시 입력해 주세요!",Toast.LENGTH_SHORT).show()
            }
            else if (title_edit.getText().toString().isNullOrBlank()){
                Toast.makeText(this,"제목을 입력해 주세요!",Toast.LENGTH_SHORT).show()
            }
            else if(start_cost_edit.getText().toString().isNullOrBlank()){
                Toast.makeText(this,"시작가격을 입력해 주세요!",Toast.LENGTH_SHORT).show()
            }
            else if(jump_cost_edit.getText().toString().isNullOrBlank()){
                Toast.makeText(this,"상승금액을 입력해 주세요!",Toast.LENGTH_SHORT).show()
            }
            else if(detail_info_edit.getText().toString().isNullOrBlank()){
                Toast.makeText(this,"상세내용을 입력해 주세요!",Toast.LENGTH_SHORT).show()
            }
            else {
                val stuffInfo: StuffInfo = StuffInfo(
                    stuff_image.getDrawable() as String,
                    title_edit.getText().toString(),
                    start_cost_edit.getText().toString(),
                    jump_cost_edit.getText().toString(),
                    period_spinner.getSelectedItem().toString(),
                    category_spinner.getSelectedItem().toString(),
                    detail_info_edit.getText().toString(),
                    loginuid = "",
                    maxPrice = ""
                )/*
                Log.d("stuffInfo", stuffInfo.imgRes.toString())
                Log.d("stuffInfo", R.drawable.noimage.toString())
                Log.d("stuffInfo", stuffInfo.title.toString())
                Log.d("stuffInfo", stuffInfo.price.toString())
                Log.d("stuffInfo", stuffInfo.upprice.toString())
                Log.d("stuffInfo", stuffInfo.period.toString())
                Log.d("stuffInfo", stuffInfo.category.toString())
                Log.d("stuffInfo", stuffInfo.detailinfo.toString())
                */
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            val currentImgUri: Uri? = data?.getData()
            try {
                val BitmapImg = MediaStore.Images.Media.getBitmap(contentResolver, currentImgUri)
                stuff_image.setImageBitmap(BitmapImg)
                IMG_FLAG = 1
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.backmaenu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.back -> {
                finish()
                return super.onOptionsItemSelected(item)
            }
            else -> {
                return false
            }
        }
    }
}