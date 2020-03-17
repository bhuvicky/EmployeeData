package com.example.employeedata.extensions

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import example.com.employeedata.R


fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun showViews(vararg views: View) {
    // TODO: Can use forEach() instead of map ??
    views.map { it.show() }
}

fun goneViews(vararg views: View) {
    views.forEach { it.gone() }
}

fun <ViewT : View> AppCompatActivity.bindView(@IdRes idRes: Int): Lazy<ViewT> {
    return lazy(LazyThreadSafetyMode.NONE) {
        findViewById<ViewT>(idRes)
    }
}

inline fun Context.checkInternetAndExecute(function: () -> Unit) {
    /*if (MobileApplication.isNetConnected(this)) {
        function()
    } else {
        Toast.makeText(this, getString(R.string.msg_no_internet), Toast.LENGTH_SHORT).show()
    }*/
}