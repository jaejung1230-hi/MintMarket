package com.example.testapp.util

fun calculateTime(time: Long):Array<Long?>{
    val array: Array<Long?> = arrayOfNulls<Long>(4)
    val day = time/(60*60*24)
    array[0] = day
    val left1 = time%(60*60*24)
    val hour = left1/(60*60)
    array[1] = hour
    val left2= left1%(60*60)
    val minutes = left2/60
    array[2] = minutes
    val seconds = left2%60
    array[3] = seconds

    return array
}