package com.example.testapp


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

class ShowFirebaseDataOnList{
    var imgRes : String? = null
    var title : String? = null
    var price : String? = null
    var upprice : String? = null
    var period: String? = null
    constructor(){}

    constructor(imgRes : String, title : String, price:String, upprice:String, period: String){
        this.imgRes = imgRes
        this.title = title
        this.price = price
        this.upprice = upprice
        this.period = period
    }
}