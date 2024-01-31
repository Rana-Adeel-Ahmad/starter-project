package com.app.extention



import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable

import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.core.widget.ImageViewCompat
import com.app.constants.AppConstants
import com.app.preferences.PreferencesHelper
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import java.util.*
import kotlin.math.roundToInt
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity


fun ViewGroup.inflate(resId: Int): View {
    return LayoutInflater.from(context).inflate(resId, this, false)
}

/**
 *
 * @param attr
 * @return
 */

fun Context.getThemeColor(attr: Int): Int {
    val typedValue: TypedValue = TypedValue()
    this.theme.resolveAttribute(attr, typedValue, true)
    return typedValue.data
}

/**
 *
 * @param attr
 * @return
 */
fun Context.getThemedDrawable(attr: Int): Drawable {
    val typedValue = TypedValue()
    theme.resolveAttribute(attr, typedValue, true)
    return ContextCompat.getDrawable(this, typedValue.data)!!
}


/**
 * TODO
 *
 * @param dp
 * @return
 */
fun View.dpToPx(dp: Double): Double {
    val scale = context.resources.displayMetrics.density
    return dp * scale + 0.5f
}

/**
 * @param sp
 * @return
 */
fun View.spToPx(sp: Float): Int {
    val px =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.resources.displayMetrics)
            .toInt()
    return px
}

/**
 * @param T
 * @param alt
 * @return
 */
fun <T> T?.ifNotNullOr(alt: T): T {
    if (this == null)
        return alt
    else
        return this
}

fun <T> T?.ifNotNullOr(alt: () -> T): T {
    if (this == null)
        return alt()
    else
        return this
}

fun <T> List<T>.plusElementFront(element: T): List<T> {
    val newList = this.toMutableList()
    newList.add(0, element)
    return newList.toList()
}

inline fun <A, B, R> ifNotNull(a: A?, b: B?, code: (A, B) -> R) {
    if (a != null && b != null)
        code(a, b)
}

fun Context.getDefaultPreferences(): SharedPreferences {
    return this.getSharedPreferences("SHARED_PREFERENCES_NAME", Context.MODE_PRIVATE)
}

fun Any.printLog(key: String, message: String) {
        Log.d(key, message)

}

fun Any.getEnglishLocale(): Locale {
    return AppConstants.localeEnglish
}

fun Any.getArabicLocale(): Locale {
    return AppConstants.localeArabic
}

fun Any.getAppLocale(): Locale {
    return if (isArabic()) getArabicLocale() else getEnglishLocale()
}

fun TextView.setDrawableColor(@ColorRes color: Int) {
    compoundDrawables.filterNotNull().forEach {
        it.colorFilter = PorterDuffColorFilter(getColor(context, color), PorterDuff.Mode.SRC_IN)
    }
}

fun ImageView.setImageResourceColor(@ColorRes color: Int) {
    try {
        ImageViewCompat.setImageTintList(
            this,
            ColorStateList.valueOf(getColor(context, color))
        )
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Any.isArabic(): Boolean {
    return PreferencesHelper.getAppLanguage()
}

fun Any.getTheme(): Int {
    return PreferencesHelper.getTheme()
}

inline fun TabLayout.addTabs(vararg strings: String?) {
    for (s in strings) {
        this.addTab(this.newTab().setText(s))
    }
}

inline fun TabLayout.addTabListener(noinline onSelectedTabPosition: (position: Int) -> Unit) {
    this.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab) {
            val pos = tab.position
            onSelectedTabPosition.invoke(pos)
        }

        override fun onTabUnselected(tab: TabLayout.Tab) {

        }

        override fun onTabReselected(tab: TabLayout.Tab) {}
    })
}

inline fun TabLayout.setUpTabLayout(
    strings: ArrayList<String>,
    noinline onSelectedTabPosition: (position: Int) -> Unit
) {
    for (s in strings) {
        this.addTab(this.newTab().setText(s))
    }
    this.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab) {
            val pos = tab.position
            onSelectedTabPosition.invoke(pos)
        }

        override fun onTabUnselected(tab: TabLayout.Tab) {

        }

        override fun onTabReselected(tab: TabLayout.Tab) {}
    })
}

inline fun TabLayout.setUpTabLayout(
    vararg strings: String?,
    noinline onSelectedTabPosition: (position: Int) -> Unit
) {
    var titles = ArrayList<String>()
    for (s in strings) {
        titles.add(s.toString())
    }
    setUpTabLayout(titles, onSelectedTabPosition)
}





fun Int.isSuccessResponseCode(): Boolean {
    return this == 200
}

//fun ImageView.loadImageWithGlide(
//    url: String, placeHolder: Int = AppUtils.getPlaceHolder()
//) {
//    try {
//        Glide
//            .with(this.context)
//            .load(url)
//            .centerCrop()
//            .placeholder(placeHolder)
//            .into(this);
//    } catch (ex: java.lang.Exception) {
//        try {
//            this.setImageDrawable(
//                ContextCompat.getDrawable(
//                    this.context,
//                    AppUtils.getErrorPlaceHolder()
//                )
//            )
//        } catch (exx: java.lang.Exception) {
//        }
//    }
//}

fun Double?.setFractions(noOfFractions: Int): String {
    if (this == null) {
        return ""
    }
    return if (noOfFractions <= 0) {
        this.roundToInt().toString()
    } else {
        "AppUtils.getNumberValue(this, noOfFractions).toString()"
    }
}

fun Any.printJson(key: String) {
    val json = Gson().toJson(this)
    Log.d(key, json)
}

fun Activity.setStatusBarColor(
     @ColorRes statusBarColor: Int,
    isDark: Boolean = false
) {
    val color = getColor(this, statusBarColor)
    val window: Window = this.window
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = color
    if (isDark) {
        val decor = this.window.decorView
        decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}

inline fun AppCompatActivity.setTransparentStatusBar() {
    this.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    this.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    this.window.statusBarColor = Color.TRANSPARENT
    // this lines ensure only the status-bar to become transparent without affecting the nav-bar
    window.decorView.systemUiVisibility =
        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
}
//
//inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
//    SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
//    else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
//}
//
//inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
//    SDK_INT >= 33 -> getParcelable(key, T::class.java)
//    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
//}
//
//inline fun <reified T : Parcelable> Bundle.parcelableArrayList(key: String): ArrayList<T>? = when {
//    SDK_INT >= 33 -> getParcelableArrayList(key, T::class.java)
//    else -> @Suppress("DEPRECATION") getParcelableArrayList(key)
//}
//
//inline fun <reified T : Parcelable> Intent.parcelableArrayList(key: String): ArrayList<T>? = when {
//    SDK_INT >= 33 -> getParcelableArrayListExtra(key, T::class.java)
//    else -> @Suppress("DEPRECATION") getParcelableArrayListExtra(key)
//}

