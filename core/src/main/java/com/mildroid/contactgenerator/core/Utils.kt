package com.mildroid.contactgenerator.core

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

fun Any?.log(msg: Any? = "", type: Int = Log.DEBUG) {
    when (type) {
        Log.DEBUG -> Log.d(TAG, "$msg -> $this")
        Log.ERROR -> Log.e(TAG, "$msg -> $this")
    }
}

inline fun <reified T : ViewBinding> AppCompatActivity.viewBinding() =
    ActivityViewBindingDelegate(T::class.java)

