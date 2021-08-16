package com.mildroid.contactgenerator.core

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import kotlin.math.log10

/**
 * The main logger function.
 */
fun Any?.log(msg: Any? = "", type: Int = Log.DEBUG) {
    when (type) {
        Log.DEBUG -> Log.d(TAG, "$msg -> $this")
        Log.ERROR -> Log.e(TAG, "$msg -> $this")
    }
}

/**
 * Checks has given permissions.
 * @return true if has.
 */
fun Context.hasPermissions(vararg permissions: String) = permissions.all {
    ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
}