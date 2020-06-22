package com.example.testapp

import java.io.Serializable
import kotlin.math.max

class detailDataList{
    var count : String? = null
    var uid : String? = null
    var maxprice : String? = null
    constructor(){}

    constructor(count: String, uid: String, maxprice: String){
        this.count = count
        this.uid = uid
        this.maxprice = maxprice
    }
}
