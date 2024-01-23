package com.ncms.module.ext

import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.EditText
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

fun View.toVisible() {
    this.visibility = View.VISIBLE
}

fun View.toInvisible() {
    this.visibility = View.INVISIBLE
}

fun View.toGone() {
    this.visibility = View.GONE
}

fun View.setViewVisibility(boolean: Boolean) {
    this.visibility = if (boolean) View.VISIBLE else View.GONE
}

fun View.setViewInVisibility(boolean: Boolean) {
    this.visibility = if (boolean) View.INVISIBLE else View.VISIBLE
}

fun RecyclerView.smoothSnapToPosition(
    position: Int,
    snapMode: Int
) {
    val smoothScroller = object : LinearSmoothScroller(this.context) {
        override fun getVerticalSnapPreference(): Int = snapMode
        override fun getHorizontalSnapPreference(): Int = snapMode
    }
    smoothScroller.targetPosition = position
    layoutManager?.startSmoothScroll(smoothScroller)
}



inline fun View.rotateImageButtonOnLeft() {
    val rotate = RotateAnimation(
        360f, 0f,
        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
        0.5f
    )
    rotate.duration = 1200
    rotate.repeatCount = Animation.ABSOLUTE
    rotate.interpolator = LinearInterpolator()
    rotate.startOffset = 0
    rotate.fillAfter = true
    this.startAnimation(rotate)
}

inline fun View.rotateImageButtonOnRight() {
    val rotate = RotateAnimation(
        0f, 360f,
        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
        0.5f
    )
    rotate.duration = 1200
    rotate.repeatCount = Animation.ABSOLUTE
    rotate.interpolator = LinearInterpolator()
    rotate.startOffset = 0
    rotate.fillAfter = true
    this.startAnimation(rotate)
}

