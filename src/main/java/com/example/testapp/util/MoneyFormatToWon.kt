package com.example.testapp.util

import java.text.DecimalFormat

fun moneyFormatToWon(money : Int) : String?{
    val decimalFormat : DecimalFormat = DecimalFormat("#,##0")

    return decimalFormat.format(money)
}
