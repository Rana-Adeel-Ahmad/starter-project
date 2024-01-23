package com.ncms.module.ext



import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
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
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.ncms.module.constants.NcmConstants
import com.ncms.module.preferences.NCMPreferencesHelper
import com.ncms.module.utils.NCMUtility
import java.util.*
import kotlin.math.roundToInt


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
    return NcmConstants.localeEnglish
}

fun Any.getArabicLocale(): Locale {
    return NcmConstants.localeArabic
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
    return NCMPreferencesHelper.getAppLanguage()
}

fun Any.getTheme(): Int {
    return NCMPreferencesHelper.getTheme()
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

fun Any.printJson(key: String) {
    val json = Gson().toJson(this)
    Log.d(key, json)
}

inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
}

inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}

inline fun <reified T : Parcelable> Bundle.parcelableArrayList(key: String): ArrayList<T>? = when {
    SDK_INT >= 33 -> getParcelableArrayList(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableArrayList(key)
}

inline fun <reified T : Parcelable> Intent.parcelableArrayList(key: String): ArrayList<T>? = when {
    SDK_INT >= 33 -> getParcelableArrayListExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableArrayListExtra(key)
}

inline fun Fragment.isMapZoomDisable(): Boolean {
    return !NCMUtility.isMapZoom(this)
}

fun Double?.setFractions(noOfFractions: Int): String {
    if (this == null) {
        return ""
    }
    return if (noOfFractions <= 0) {
        this.roundToInt().toString()
    } else {
        NCMUtility.getNumberValue(this, noOfFractions).toString()
    }
}

fun Int.isSuccessResponseCode(): Boolean {
    return this == 200
}