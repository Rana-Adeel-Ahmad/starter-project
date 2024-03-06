package com.app.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.widget.ImageView
import com.app.R
import com.app.extention.isArabic
import com.bumptech.glide.Glide

object AppUtils {
    @SuppressLint("InternalInsetResource", "DiscouragedApi")
    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    private fun hideSystemUI() {
//        WindowCompat.setDecorFitsSystemWindows(window, false)
//        WindowInsetsControllerCompat(window, viewBinding.root).let { controller ->
//            controller.hide(WindowInsetsCompat.Type.systemBars())
//            controller.systemBarsBehavior =
//                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
//        }
    }

    private fun showSystemUI() {
//        WindowCompat.setDecorFitsSystemWindows(window, true)
//        WindowInsetsControllerCompat(
//            window,
//            viewBinding.root
//        ).show(WindowInsetsCompat.Type.systemBars())
    }

    fun loadImageWithGlide(
        context: Context,
        url: String,
        placeHolder: Int,
        imageView: ImageView
    ) {
        try {
            Glide
                .with(context)
                .load(url)
                .centerCrop()
                .placeholder(placeHolder)
                .into(imageView);
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }
    }



    fun getRespectiveLanguageValueIfNotNull(
        context: Context?,
        valueEn: String?,
        valueAr: String?
    ): String {
        if (valueEn == null && valueAr == null) {
            return ""
        }
        return if (isArabic()) {
            if (!TextUtils.isEmpty(valueAr)) {
                valueAr!!
            } else {
                valueEn!!
            }
        } else {
            run {
                return if (!TextUtils.isEmpty(valueEn)) {
                    valueEn!!
                } else {
                    valueAr!!
                }
            }
        }
    }

    fun openExternalBrowser(url: String, activity: Activity) {
        var url = url
        url.replace("http://", "https://")
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "https://$url"
        }
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        activity.startActivity(browserIntent)
    }
}
