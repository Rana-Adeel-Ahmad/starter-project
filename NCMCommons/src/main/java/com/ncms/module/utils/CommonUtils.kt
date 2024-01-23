package com.ncms.module.utils

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import android.util.Patterns
import com.ncms.module.constants.NcmConstants
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Muzzamil
 */
object CommonUtils {
    @SuppressLint("all")
    fun getDeviceId(context: Context): String {
        return Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        )
    }

    val timestamp: String
        get() = SimpleDateFormat(NcmConstants.TIMESTAMP_FORMAT, Locale.US)
            .format(Date())

    val timestamp2: String
        get() = SimpleDateFormat(NcmConstants.TIMESTAMP_FORMAT_2, Locale.US)
            .format(Date())




}