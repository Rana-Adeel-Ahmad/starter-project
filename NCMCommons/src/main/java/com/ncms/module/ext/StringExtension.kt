package com.ncms.module.ext


import com.ncms.module.utils.datetimeutils.DateTimeUtils
import android.content.Context
import com.ncms.module.R
import com.ncms.module.constants.NcmConstants
import com.ncms.module.constants.NcmConstants.DATE_TIME_FORMAT
import com.ncms.module.constants.NcmConstants.TIME_MINUTES
import com.ncms.module.constants.NcmConstants.UTC_TIME_FORMAT
import com.ncms.module.utils.NCMUtility
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


fun String.getCountryName(): String {
    val locale = Locale("", this)
    return locale.getDisplayCountry(Locale.ENGLISH)
}

fun Any.getCurrentHour(): Int {
    return try {
        val nowCal = Calendar.getInstance()
        nowCal[Calendar.HOUR_OF_DAY]
    } catch (ex: Exception) {
        0
    }
}


fun Any.getCurrentDateTime(format: String, timezone: String): String {
    val df = SimpleDateFormat(format, Locale.ENGLISH)
    df.timeZone = TimeZone.getTimeZone(timezone)
    val date = Date()
    return df.format(date)
}

fun Any.from24To12Time(context: Context, string: String): String {
    try {
        var finalDateStr = DateTimeUtils.convertDateStringXFormatToYFormat(
            string,
            NcmConstants.TIME_FORMAT_24,
            NcmConstants.DATE_DISPLAY_hh_mm_aa,
            ""
        )

        val amStr = context.getString(R.string.am)
        val pmStr = context.getString(R.string.pm)
        finalDateStr = finalDateStr!!.replace("AM", amStr, true)
        finalDateStr = finalDateStr.replace("PM", pmStr, true)
        return finalDateStr
    }catch (ex: Exception){

    }
    return string
}

fun Any.getDateStringToTime(string: String, format: String): String {
    try {
        val dateSplit: List<String> = string.split("+")
        var dateStr = dateSplit[0].replace("GMT", "").replace("T", " ")
        var finalDateStr = DateTimeUtils.convertDateStringXFormatToYFormat(
            dateStr,
            format,
            NcmConstants.DATE_DISPLAY_HH_MM,
            ""
        )
        return finalDateStr!!
    } catch (ex: Exception) {
        return string
    }

}

fun Any.getDateStringToTime(
        string: String,
        orignalFormat: String,
        requiredFormat: String,
): String {
    try {
        var finalDateStr = DateTimeUtils.convertDateStringXFormatToYFormat(
            string,
            orignalFormat,
            requiredFormat,
            ""
        )
        return finalDateStr!!
    } catch (ex: Exception) {
        return string
    }
}

fun Any.getSunMoonDateToUTCFormate(string: String): String {
    val dateSplit: List<String> = string.split("+")
    val dateStr = dateSplit[0].replace("GMT", "").replace("T", " ")
    val finalDateStr = DateTimeUtils.convertDateStringXFormatToYFormat(
        dateStr,
        DATE_TIME_FORMAT,
        UTC_TIME_FORMAT,
        ""
    )
    return finalDateStr!!
}

fun Any.getSunMoonDateToMinutesFormate(string: String, format: String): Int {
    val dateSplit: List<String> = string.split("+")
    val dateStr = dateSplit[0].replace("GMT", "").replace("T", " ")
    val finalDateStr = DateTimeUtils.convertDateStringXFormatToYFormat(
        dateStr,
        format,
        TIME_MINUTES,
        ""
    )
    try {
        return finalDateStr!!.toInt()
    } catch (ex: Exception) {
        return -1
    }
}

fun Any.getCurrentDateTime(format: String): String {
    val df = SimpleDateFormat(format, Locale.ENGLISH)
    val date = Date()
    return df.format(date)
}

public fun String.setFractions(noOfFractions: Int): String {
    var value: Double = 0.0
    try {
        value = this.toDouble()
    } catch (ex: Exception){
        return this
    }
    return if(noOfFractions <= 0){
        value.roundToInt().toString()
    } else {
        NCMUtility.getNumberValue(value, noOfFractions).toString()
    }
}



