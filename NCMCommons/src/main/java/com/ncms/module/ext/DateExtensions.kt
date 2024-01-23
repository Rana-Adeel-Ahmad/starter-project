package com.ncms.module.ext

import com.ncms.module.utils.datetimeutils.DateTimeUtils
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


fun Date.getCurrentTimeAccordingToTimezone(timeZone: String, includeCurrentHour: Boolean, addDays: Int = 0): String {
    var format = if(includeCurrentHour) "yyyy-MM-dd'T'HH':00:00'" else "yyyy-MM-dd'T00:00:00'"
    val date = DateTimeUtils.getCurrentDateTimeFromTimeZoneWithAddDays(timeZone, format, addDays)
    return date
}

fun Date.getPreviousUTCDateStringFromCurrentTimezone(dateTime: String, dateInputFormat: String, dateOutputFormat: String, timezone: String, addDays: Int): String {

    var utcformat = dateInputFormat
    val originalFormat: DateFormat = SimpleDateFormat(dateOutputFormat, Locale.ENGLISH)
    originalFormat.timeZone = TimeZone.getTimeZone(timezone)
    val utcFormat: DateFormat = SimpleDateFormat(utcformat, Locale.ENGLISH)
    utcFormat.timeZone = TimeZone.getTimeZone(timezone)
    var date = originalFormat.parse(dateTime)
    val cal = Calendar.getInstance()
    cal.timeZone = originalFormat.timeZone
    cal.time = date // convert your date to Calendar object
    cal.set(Calendar.HOUR_OF_DAY, 0)
    cal.set(Calendar.MINUTE, 0)
    cal.set(Calendar.SECOND, 0)
    cal.add(Calendar.DATE, addDays)
    date = cal.time // again get back your date object
    var dateTimeUTC = utcFormat.format(date)
    return dateTimeUTC
}

fun Date.getCurrentTimeAccordingToTimezone(formate: String): String {

    val df = SimpleDateFormat(formate, Locale.ENGLISH)
    df.timeZone = TimeZone.getTimeZone("UTC")
    return df.format(this)
}

fun Date.getCurrentTimeAccordingToTimezone(formate: String, timezone: String): String {
    val df = SimpleDateFormat(formate, Locale.ENGLISH)
    df.timeZone = TimeZone.getTimeZone(timezone)
    return df.format(this)
}

fun Date.getCurrentTimeAccordingToTimezoneWithCurrentLocale(formate: String, timezone: String): String {
    val df = SimpleDateFormat(formate)
    df.timeZone = TimeZone.getTimeZone(timezone)
    return df.format(this)
}

fun Date.getCurrentDateTime(format: String): String {
    val df = SimpleDateFormat(format, Locale.ENGLISH)
    return df.format(this)
}



