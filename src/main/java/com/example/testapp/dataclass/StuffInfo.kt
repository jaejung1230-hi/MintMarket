package com.example.testapp.dataclass

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.android.parcel.Parcelize

@Parcelize
class StuffInfo() : Parcelable{
    var count : String? = null
    var enrolleruid : String? = null
    var imgRes: String? = null
    var title: String? = null
    var price: String? = null
    var upprice: String? = null
    var period: String? = null
    var category: String? = null
    var detailinfo: String? = null
    var loginuid : String? = null
    var maxPrice : String? = null

    constructor(count : String, enrolleruid : String, imgRes: String, title: String, price: String,
                upprice: String, period: String, category: String, detailinfo: String, loginuid : String, maxPrice : String) : this() {
        this.count = count
        this.enrolleruid = enrolleruid
        this.imgRes = imgRes
        this.title = title
        this.price = price
        this.upprice = upprice
        this.period = period
        this.category = category
        this.detailinfo = detailinfo
        this.loginuid = loginuid
        this.maxPrice = maxPrice
    }
}
