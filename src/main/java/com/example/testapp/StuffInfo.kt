package com.example.testapp

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StuffInfo(val imgRes: String, val title: String, val price: String,
                     val upprice: String, val period: String, val category: String, val detailinfo: String, val uid : String, val maxPrice : String) : Parcelable