package com.app.extention


import android.content.Intent
import android.content.res.Resources
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import com.app.utils.AppUtils
import java.util.*

fun View.toVisible() {
    visibility = View.VISIBLE
}

fun View.toInvisible() {
    visibility = View.INVISIBLE
}

fun View.toGone() {
    visibility = View.GONE
}

fun View.addStatusBarMargin() {
    val statusBarHeight: Int = AppUtils.getStatusBarHeight(this.context)
    this.setPadding(0, statusBarHeight, 0, 0)
}


fun Any.convertToProperHtml(body: String): String {
    val paragraph = "<p>" + body + "</p>"
    val html = "<html><head><title></title></head><body>" + paragraph + "</body></html>"
    return html
}


val Float.toDP: Float
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f)

