package com.example.testapp.dataclass


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import kotlin.math.max


class ShowFirebaseDataOnList{
    var imgRes : String? = null
    var title : String? = null
    var price : String? = null
    var upprice : String? = null
    var period: String? = null
    var loginuid : String? = null
    var maxPrice : String? = null
    var detailinfo : String? = null
    var category :String? = null
    constructor(){}

    constructor(imgRes : String, title : String, price:String,
                upprice:String, period: String, loginuid : String, maxPrice : String, detailinfo :String, category : String){
        this.imgRes = imgRes
        this.title = title
        this.price = price
        this.upprice = upprice
        this.period = period
        this.loginuid = loginuid
        this.maxPrice = maxPrice
        this.detailinfo = detailinfo
        this.category = category
    }
}
