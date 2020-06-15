package com.example.testapp


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

class ShowFirebaseDataOnList{
    var imagePath : String? = null
    var title : String? = null
    var price : String? = null
    var upprice : String? = null
    constructor(){}

    constructor(imagePath : String, title : String, price:String,upprice:String){
        this.imagePath = imagePath
        this.title = title
        this.price = price
        this.upprice = upprice
    }
}