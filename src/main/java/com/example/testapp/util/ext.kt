package com.example.subway.util

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

//화면에 출력되어있는 inputManager(소프트키보드)를 숨김으로 전환
fun Activity.hideKeyboard() {
    /*현재화면 키보드를 가져옵니다. */
    val inputMethodManager =
        this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    /* 현재 사용자가 보고있는 화면을 가져옵니다. */
    this.currentFocus?.let { focusView ->
        /* 화면에 출력되어있는 inputManager(키보드)를 숨김으로 전환*/
        inputMethodManager.hideSoftInputFromWindow(
            focusView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
}
