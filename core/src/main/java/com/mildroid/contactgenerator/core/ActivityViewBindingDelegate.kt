package com.mildroid.contactgenerator.core

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class ActivityViewBindingDelegate<T : ViewBinding>(private val bindingClass: Class<T>) :
    ReadOnlyProperty<AppCompatActivity, T> {

    //    initiate variable for binding view
    private var binding: T? = null

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: AppCompatActivity, property: KProperty<*>): T {
        binding?.let { return it }

//        inflate view class
        val inflateMethod =
            bindingClass.getMethod("inflate", LayoutInflater::class.java)

//        bind layout
        val invokeLayout =
            inflateMethod.invoke(null, thisRef.layoutInflater) as T

//        set content view
        thisRef.setContentView(invokeLayout.root)

        return invokeLayout.also { this.binding = it }
    }
}