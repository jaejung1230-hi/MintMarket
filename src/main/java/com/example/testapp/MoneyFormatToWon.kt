package com.example.testapp

import java.text.DecimalFormat

fun moneyFormatToWon(money : Int) : String?{
    val decimalFormat : DecimalFormat = DecimalFormat("#,##0")

    return decimalFormat.format(money)
}
