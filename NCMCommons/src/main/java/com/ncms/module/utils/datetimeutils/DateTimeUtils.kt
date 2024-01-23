package com.ncms.module.utils.datetimeutils



import com.ncms.module.ext.isArabic
import android.content.Context
import android.text.format.DateUtils
import android.util.Log
import com.ncms.module.R
import com.ncms.module.constants.NcmConstants
import com.ncms.module.constants.NcmConstants.DATE_DISPLAY_yyyy_MM_dd__hh_mm_ss
import com.ncms.module.constants.NcmConstants.DATE_TIME_FORMAT
import com.ncms.module.constants.NcmConstants.DATE_TIME_FORMAT_MAP
import com.ncms.module.constants.NcmConstants.UTC_TIME_FORMAT
import com.ncms.module.ext.getCurrentDateTime
import com.ncms.module.utils.NCMUtility
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.abs


/**
 * DateTimeUtils
 * This class contains a bunch of function that can manipulate
 * Date object or Date String to achieve certain operations
 * e.g : Time difference, Time Ago, Date formatting
 *
 * @author thunder413
 * @version 1.5
 */
object DateTimeUtils {
    /**
     * LOG TAG
     */
    private const val LOG_TAG = "DateTimeUtils"

    /**
     * Debug mode
     */
    private var debug = false

    /**
     * Time zone
     */
    private var timeZone = "UTC"

    /**
     * Enable / Disable
     *
     * @param state Debug state
     */
    fun setDebug(state: Boolean) {
        debug = state
    }

    /**
     * Set TimeZone
     *
     * @param zone TimeZone
     */
    fun setTimeZone(zone: String) {
        timeZone = zone
    }

    /**
     * Get Date or DateTime formatting pattern
     *
     * @param dateString Date String
     * @return Format Pattern
     */
    public fun getDatePattern(dateString: String?): String? {
        return if (isDateTime(dateString)) {
            if (dateString!!.contains("/")) DateTimeFormat.DATE_TIME_PATTERN_2 else DateTimeFormat.DATE_TIME_PATTERN_1
        } else {
            if (dateString!!.contains("/")) DateTimeFormat.DATE_PATTERN_2 else DateTimeFormat.DATE_PATTERN_1
        }
    }
    /**
     * Convert a Java Date object to String
     *
     * @param date   Date Object
     * @param locale Locale
     * @return Date Object string representation
     */
    /**
     * Convert a Java Date object to String
     *
     * @param date Date Object
     * @return Date Object string representation
     */
    @JvmOverloads
    fun formatDate(date: Date?, locale: Locale? = Locale.getDefault()): String {
        if (date == null && debug) {
            Log.e(LOG_TAG, "formatDate >> Supplied date is null")
        }
        val iso8601Format = SimpleDateFormat(DateTimeFormat.DATE_TIME_PATTERN_1, locale)
        iso8601Format.timeZone = TimeZone.getTimeZone(timeZone)
        if (debug) {

        }
        return iso8601Format.format(date)
    }

    /**
     * Convert a date string to Java Date Object
     *
     * @param dateString Date String
     * @param locale     Locale
     * @return Java Date Object
     */
    fun formatDate(dateString: String?, locale: Locale?): Date? {
        val iso8601Format = SimpleDateFormat(getDatePattern(dateString), locale)
        iso8601Format.timeZone = TimeZone.getTimeZone(timeZone)
        var date: Date? = null
        if (dateString != null) {
            try {
                date = iso8601Format.parse(dateString.trim { it <= ' ' })
            } catch (e: ParseException) {
                if (debug) {
                    Log.e(
                        LOG_TAG,
                        "formatDate >> Fail to parse supplied date string >> $dateString"
                    )
                    e.printStackTrace()
                }
            }
        }
        return date
    }

    /**
     * Convert a date string to Java Date Object
     *
     * @param date Date String
     * @return Java Date Object
     */
    fun formatDate(date: String?): Date? {
        return formatDate(date, Locale.getDefault())
    }
    /**
     * Convert a timeStamp into a date object
     *
     * @param timeStamp TimeStamp
     * @param units     Witch unit is whether seconds or milliseconds
     * @return Date object
     * @see DateTimeUnits.SECONDS
     *
     * @see DateTimeUnits.MILLISECONDS
     */
    /**
     * Convert a timeStamp into a date considering given timeStamp in milliseconds
     *
     * @param timeStamp TimeStamp
     * @return Date object
     * @see DateTimeUnits.MILLISECONDS
     */
    @JvmOverloads
    fun formatDate(timeStamp: Long, units: DateTimeUnits = DateTimeUnits.MILLISECONDS): Date {
        return if (units == DateTimeUnits.SECONDS) Date(timeStamp * 1000L) else Date(
            timeStamp
        )
    }
    /**
     * Format date using a given pattern
     * and apply supplied locale
     *
     * @param date    Date Object
     * @param pattern Pattern
     * @param locale  Locale
     * @return Formatted date
     */
    /**
     * Format date using a given pattern
     * apply default locale
     *
     * @param date    Date Object
     * @param pattern Pattern
     * @return Formatted date
     */
    @JvmOverloads
    fun formatWithPattern(
        date: Date?,
        pattern: String?,
        locale: Locale? = Locale.getDefault()
    ): String {
        if (date == null && debug) {
            Log.e(LOG_TAG, "FormatWithPattern >> Supplied date is null")
        }
        val iso8601Format = SimpleDateFormat(pattern, locale)
        iso8601Format.timeZone = TimeZone.getTimeZone(timeZone)
        return iso8601Format.format(date)
    }
    /**
     * Format date using a given pattern
     * and apply supplied locale
     *
     * @param date    Date String
     * @param pattern Pattern
     * @param locale  Locale
     * @return Formatted date
     */
    /**
     * Format date using a given pattern
     * apply default locale
     *
     * @param date    Date String
     * @param pattern Pattern
     * @return Formatted date
     */
    @JvmOverloads
    fun formatWithPattern(
        date: String?,
        pattern: String?,
        locale: Locale? = Locale.getDefault()
    ): String {
        return formatWithPattern(formatDate(date), pattern, locale)
    }

    /**
     * Build a pattern for given style
     *
     * @param style DateTimeStyle
     * @return Pattern
     */
    private fun getPatternForStyle(style: DateTimeStyle): String {
        val pattern: String
        pattern = if (style == DateTimeStyle.LONG) {
            "MMMM dd, yyyy"
        } else if (style == DateTimeStyle.MEDIUM) {
            "MMM dd, yyyy"
        } else if (style == DateTimeStyle.SHORT) {
            "MM/dd/yy"
        } else {
            "EEEE, MMMM dd, yyyy"
        }
        return pattern
    }
    /**
     * Get localized date string
     *
     * @param date Date string
     * @return Formatted localized date string
     */
    /**
     * Get localized date string (Using default locale)
     *
     * @param date Date string
     * @return Formatted localized date string
     */
    @JvmOverloads
    fun formatWithStyle(
        date: Date?,
        style: DateTimeStyle,
        locale: Locale? = Locale.getDefault()
    ): String {
        if (date == null && debug) {
            Log.e(LOG_TAG, "FormatWithPattern >> Supplied date is null")
        }
        return formatWithPattern(date, getPatternForStyle(style), locale)
    }
    /**
     * Get localized date string (Using default locale)
     *
     * @param date Date string
     * @return Formatted localized date string
     */
    /**
     * Get localized date string (Using default locale)
     *
     * @param date Date string
     * @return Formatted localized date string
     */
    @JvmOverloads
    fun formatWithStyle(
        date: String?,
        style: DateTimeStyle,
        locale: Locale? = Locale.getDefault()
    ): String {
        return formatWithStyle(formatDate(date), style, locale)
    }
    /**
     * Extract time from date without seconds
     *
     * @param date Date object
     * @return Time String
     * @see DateTimeFormat.TIME_PATTERN_1
     */
    /**
     * Extract time from date without seconds
     *
     * @param date Date object
     * @return Time string
     */
    @JvmOverloads
    fun formatTime(date: Date?, forceShowHours: Boolean = false): String {
        val iso8601Format = SimpleDateFormat(DateTimeFormat.TIME_PATTERN_1, Locale.getDefault())
        iso8601Format.timeZone = TimeZone.getTimeZone(timeZone)
        val time = iso8601Format.format(date)
        val hhmmss = time.split(":".toRegex()).toTypedArray()
        val hours = hhmmss[0].toInt()
        val minutes = hhmmss[1].toInt()
        val seconds = hhmmss[2].toInt()
        return ((if (hours == 0 && !forceShowHours) "" else if (hours < 10) "0$hours:" else "$hours:") +
                (if (minutes == 0) "00" else if (minutes < 10) "0$minutes" else minutes.toString()) + ":"
                + if (seconds == 0) "00" else if (seconds < 10) "0$seconds" else seconds.toString())
    }
    /**
     * Extract time from date without seconds
     *
     * @param date Date object
     * @return Time string
     */
    /**
     * Extract time from date without seconds
     *
     * @param date Date object
     * @return Time string
     */
    @JvmOverloads
    fun formatTime(date: String?, forceShowHours: Boolean = false): String {
        return formatTime(formatDate(date), forceShowHours)
    }

    /**
     * Convert millis to human readable time
     *
     * @param millis TimeStamp
     * @return Time String
     */
    fun millisToTime(millis: Long): String {
        val seconds = (TimeUnit.MILLISECONDS.toSeconds(millis)
                - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)))
        val minutes = (TimeUnit.MILLISECONDS.toMinutes(millis)
                - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)))
        val hours = TimeUnit.MILLISECONDS.toHours(millis)
        return ((if (hours == 0L) "" else if (hours < 10) "0$hours:" else "$hours:") +
                (if (minutes == 0L) "00" else if (minutes < 10) "0$minutes" else minutes.toString()) + ":"
                + if (seconds == 0L) "00" else if (seconds < 10) "0$seconds" else seconds.toString())
    }

    /**
     * Convert millis to human readable time
     *
     * @param time Time string
     * @return Time String
     */
    fun timeToMillis(time: String): Long {
        val hhmmss = time.split(":".toRegex()).toTypedArray()
        var hours = 0
        val minutes: Int
        val seconds: Int
        if (hhmmss.size == 3) {
            hours = hhmmss[0].toInt()
            minutes = hhmmss[1].toInt()
            seconds = hhmmss[2].toInt()
        } else {
            minutes = hhmmss[0].toInt()
            seconds = hhmmss[1].toInt()
        }
        return ((hours * 60 + minutes * 60 + seconds) * 1000).toLong()
    }

    /**
     * Tell whether or not a given string represent a date time string or a simple date
     *
     * @param dateString Date String
     * @return True if given string is a date time False otherwise
     */
    fun isDateTime(dateString: String?): Boolean {
        return dateString != null && dateString.trim { it <= ' ' }.split(" ".toRegex())
            .toTypedArray().size > 1
    }

    /**
     * Tell whether or not a given date is yesterday
     *
     * @param date Date Object
     * @return True if the date is yesterday False otherwise
     */
    fun isYesterday(date: Date?): Boolean {
        // Check if yesterday
        val c1 = Calendar.getInstance() // today
        c1.add(Calendar.DAY_OF_YEAR, -1) // yesterday
        val c2 = Calendar.getInstance()
        c2.time = date //
        return (c1[Calendar.YEAR] == c2[Calendar.YEAR]
                && c1[Calendar.DAY_OF_YEAR] == c2[Calendar.DAY_OF_YEAR])
    }

    /**
     * Tell whether or not a given date is yesterday
     *
     * @param dateString Date String
     * @return True if the date is yesterday False otherwise
     */
    fun isYesterday(dateString: String?): Boolean {
        return isYesterday(formatDate(dateString))
    }

    /**
     * Tell whether or not a given date is today date
     *
     * @param date Date object
     * @return True if date is today False otherwise
     */
    fun isToday(date: Date?): Boolean {
        return DateUtils.isToday(date!!.time)
    }

    /**
     * Tell whether or not a given date is today date
     *
     * @param dateString Date string
     * @return True if date is today False otherwise
     */
    fun isToday(dateString: String?): Boolean {
        return isToday(formatDate(dateString))
    }

    /**
     * Get Previous month from a given date
     *
     * @param date Date start
     * @return Date of the previous month
     */
    fun getPreviousMonthDate(date: Date?): Date {
        val c = Calendar.getInstance()
        c.time = date //
        c.add(Calendar.MONTH, -1)
        return c.time
    }

    /**
     * Get Previous month from a given date
     *
     * @param date Date start
     * @return Date of the previous month
     */
    fun getPreviousMonthDate(date: String?): Date {
        return getPreviousMonthDate(formatDate(date))
    }

    /**
     * Get Next month from a given date
     *
     * @param date Date start
     * @return Date of the previous month
     */
    fun getNextMonthDate(date: Date?): Date {
        val c = Calendar.getInstance()
        c.time = date //
        c.add(Calendar.MONTH, 1)
        return c.time
    }

    /**
     * Get Previous month from a given date
     *
     * @param date String Date start
     * @return Date of the previous month
     */
    fun getNextMonthDate(date: String?): Date {
        return getNextMonthDate(formatDate(date))
    }

    /**
     * Get Previous week date
     *
     * @param date         Date Object
     * @param dayOfTheWeek Day Of the week
     * @return Date
     */
    fun getPreviousWeekDate(date: Date?, dayOfTheWeek: Int): Date {
        val c = Calendar.getInstance()
        c.time = date
        c.firstDayOfWeek = dayOfTheWeek
        c[Calendar.DAY_OF_WEEK] = dayOfTheWeek
        c.add(Calendar.DATE, -7)
        return c.time
    }

    /**
     * Get Previous week date
     *
     * @param date         Date String
     * @param dayOfTheWeek Day Of the week
     * @return Date
     */
    fun getPreviousWeekDate(date: String?, dayOfTheWeek: Int): Date {
        return getPreviousWeekDate(formatDate(date), dayOfTheWeek)
    }

    /**
     * Get Next week date
     *
     * @param date         Date Object
     * @param dayOfTheWeek Day Of the week
     * @return Date
     */
    fun getNextWeekDate(date: Date?, dayOfTheWeek: Int): Date {
        val c = Calendar.getInstance()
        c.time = date
        c.firstDayOfWeek = dayOfTheWeek
        c[Calendar.DAY_OF_WEEK] = dayOfTheWeek
        c.add(Calendar.DATE, 7)
        return c.time
    }

    /**
     * Get Next week date
     *
     * @param date Date Object
     * @return Date
     */
    fun getNextWeekDate(date: Date?): Date {
        return getNextWeekDate(date, Calendar.MONDAY)
    }

    /**
     * Get Next week date
     *
     * @param date Date Object
     * @return Date
     */
    fun getNextWeekDate(date: String?): Date {
        return getNextWeekDate(formatDate(date))
    }

    /**
     * Get Next week date
     *
     * @param date         Date Object
     * @param dayOfTheWeek Day Of the week
     * @return Date
     */
    fun getNextWeekDate(date: String?, dayOfTheWeek: Int): Date {
        return getNextWeekDate(formatDate(date), dayOfTheWeek)
    }

    /**
     * Get difference between two dates
     *
     * @param nowDate  Current date
     * @param oldDate  Date to compare
     * @param dateDiff Difference Unit
     * @return Difference
     */
    fun getDateDiff(nowDate: Date?, oldDate: Date?, dateDiff: DateTimeUnits?): Int {
        val diffInMs = nowDate!!.time - oldDate!!.time
//        val days = TimeUnit.MILLISECONDS.toDays(diffInMs).toInt()
//        val hours = (TimeUnit.MILLISECONDS.toHours(diffInMs) - TimeUnit.DAYS.toHours(
//            days.toLong()
//        )).toInt()
//        val minutes = (TimeUnit.MILLISECONDS.toMinutes(diffInMs) - TimeUnit.HOURS.toMinutes(
//            TimeUnit.MILLISECONDS.toHours(diffInMs)
//        )).toInt()
//        val seconds = TimeUnit.MILLISECONDS.toSeconds(diffInMs).toInt()

        val days = TimeUnit.MILLISECONDS.toDays(diffInMs).toInt()
        val hours = TimeUnit.MILLISECONDS.toHours(diffInMs).toInt()
        val minutes = TimeUnit.MILLISECONDS.toMinutes(diffInMs).toInt()
        val seconds = TimeUnit.MILLISECONDS.toSeconds(diffInMs).toInt()
        return when (dateDiff) {
            DateTimeUnits.DAYS -> days
            DateTimeUnits.SECONDS -> seconds
            DateTimeUnits.MINUTES -> minutes
            DateTimeUnits.HOURS -> hours
            DateTimeUnits.MILLISECONDS -> diffInMs.toInt()
            else -> diffInMs.toInt()
        }
    }

    /**
     * Get difference between two dates
     *
     * @param nowDate  Current date
     * @param oldDate  Date to compare
     * @param dateDiff Difference Unit
     * @return Difference
     */
    fun getDateDiff(nowDate: String?, oldDate: Date?, dateDiff: DateTimeUnits?): Int {
        return getDateDiff(formatDate(nowDate), oldDate, dateDiff)
    }

    /**
     * Get difference between two dates
     *
     * @param nowDate  Current date
     * @param oldDate  Date to compare
     * @param dateDiff Difference Unit
     * @return Difference
     */
    fun getDateDiff(nowDate: Date?, oldDate: String?, dateDiff: DateTimeUnits?): Int {
        return getDateDiff(nowDate, formatDate(oldDate), dateDiff)
    }

    /**
     * Get difference between two dates
     *
     * @param nowDate  Current date
     * @param oldDate  Date to compare
     * @param dateDiff Difference Unit
     * @return Difference
     */
    fun getDateDiff(nowDate: String?, oldDate: String?, dateDiff: DateTimeUnits?): Int {
        return getDateDiff(nowDate, formatDate(oldDate), dateDiff)
    }

    /**
     * Get time ago of given date
     *
     * @param context Context
     * @param date    Date object
     * @param style   DateTimeStyle
     * @return Time ago string
     */
    fun getTimeAgo(context: Context, date: Date?, style: DateTimeStyle): String {
        val seconds = getDateDiff(Date(), date, DateTimeUnits.SECONDS).toDouble()
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        val phrase: String
        val s: String
        if (seconds <= 0) {
            phrase = context.getString(R.string.time_ago_now)
        } else if (seconds < 45) {
            s =
                if (style == DateTimeStyle.AGO_FULL_STRING) context.getString(R.string.time_ago_full_seconds) else context.getString(
                    R.string.time_ago_seconds
                )
            phrase = String.format(s, Math.round(seconds))
        } else if (seconds < 90) {
            s =
                if (style == DateTimeStyle.AGO_FULL_STRING) context.getString(R.string.time_ago_full_minute) else context.getString(
                    R.string.time_ago_minute
                )
            phrase = String.format(s, 1)
        } else if (minutes < 45) {
            s =
                if (style == DateTimeStyle.AGO_FULL_STRING) context.getString(R.string.time_ago_full_minutes) else context.getString(
                    R.string.time_ago_minutes
                )
            phrase = String.format(s, Math.round(minutes))
        } else if (minutes < 90) {
            s =
                if (style == DateTimeStyle.AGO_FULL_STRING) context.getString(R.string.time_ago_full_hour) else context.getString(
                    R.string.time_ago_hour
                )
            phrase = String.format(s, 1)
        } else if (hours < 24) {
            s =
                if (style == DateTimeStyle.AGO_FULL_STRING) context.getString(R.string.time_ago_full_hours) else context.getString(
                    R.string.time_ago_hours
                )
            phrase = String.format(s, Math.round(hours))
        } else if (hours < 42) {
            phrase = if (isYesterday(date)) {
                context.getString(R.string.time_ago_yesterday_at, formatTime(date))
            } else {
                formatWithStyle(
                    date,
                    if (style == DateTimeStyle.AGO_FULL_STRING) DateTimeStyle.FULL else DateTimeStyle.SHORT
                )
            }
        } else if (days < 30) {
            s =
                if (style == DateTimeStyle.AGO_FULL_STRING) context.getString(R.string.time_ago_full_days) else context.getString(
                    R.string.time_ago_days
                )
            phrase = String.format(s, Math.round(days))
        } else if (days < 45) {
            s =
                if (style == DateTimeStyle.AGO_FULL_STRING) context.getString(R.string.time_ago_full_month) else context.getString(
                    R.string.time_ago_month
                )
            phrase = String.format(s, 1)
        } else if (days < 365) {
            s =
                if (style == DateTimeStyle.AGO_FULL_STRING) context.getString(R.string.time_ago_full_months) else context.getString(
                    R.string.time_ago_months
                )
            phrase = String.format(s, Math.round(days / 30))
        } else {
            phrase = formatWithStyle(
                date,
                if (style == DateTimeStyle.AGO_FULL_STRING) DateTimeStyle.FULL else DateTimeStyle.SHORT
            )
        }
        return phrase
    }

    /**
     * Get time ago of given date
     *
     * @param context    Context
     * @param dateString Representing a date time string
     * @return Time ago string
     */
    fun getTimeAgo(context: Context, dateString: String?): String {
        return getTimeAgo(context, formatDate(dateString), DateTimeStyle.AGO_FULL_STRING)
    }

    /**
     * Get time ago of given date
     *
     * @param context Context
     * @param date    Representing a date time string
     * @return Time ago string
     */
    fun getTimeAgo(context: Context, date: Date?): String {
        return getTimeAgo(context, date, DateTimeStyle.AGO_FULL_STRING)
    }

    fun convertDateStringXFormatToYFormat(
        dateStr_: String?,
        xFormat: String?,
        yFormat: String?,
        timeZone: String
    ): String? {
        try {
            if (dateStr_ == null) return ""
            var dateStr = dateStr_
            if (xFormat!!.endsWith("z", true) && (!dateStr!!.endsWith("z", true))) {
                dateStr = dateStr + "Z"
            }
            val originalFormat: DateFormat =
                SimpleDateFormat(xFormat, Locale("en"))
            val targetFormat: DateFormat =
                SimpleDateFormat(yFormat, Locale("en"))
            if (timeZone.equals("", ignoreCase = true)) {
                targetFormat.timeZone = TimeZone.getDefault()
                originalFormat.timeZone = TimeZone.getDefault()
            } else {
                targetFormat.timeZone = TimeZone.getTimeZone(timeZone)
                originalFormat.timeZone = TimeZone.getTimeZone(timeZone)
            }

            val date = originalFormat.parse(dateStr)
            dateStr = targetFormat.format(date)
            return dateStr
        } catch (e: ParseException) {
            e.printStackTrace()
            return ""

        }
    }

    fun convertDateStringXFormatToYFormatArabic(
        dateStr_: String?,
        xFormat: String?,
        yFormat: String?,
        timeZone: String
    ): String? {
        try {
            if (dateStr_ == null) return ""
            var dateStr = dateStr_
            if (xFormat!!.endsWith("z", true) && (!dateStr!!.endsWith("z", true))) {
                dateStr = dateStr + "Z"
            }
            val originalFormat: DateFormat =
                SimpleDateFormat(xFormat, Locale("ar"))
            val targetFormat: DateFormat =
                SimpleDateFormat(yFormat, Locale("ar"))
            if (timeZone.equals("", ignoreCase = true)) {
                targetFormat.timeZone = TimeZone.getDefault()
                originalFormat.timeZone = TimeZone.getDefault()
            } else {
                targetFormat.timeZone = TimeZone.getTimeZone(timeZone)
                originalFormat.timeZone = TimeZone.getTimeZone(timeZone)
            }

            val date = originalFormat.parse(dateStr)
            dateStr = targetFormat.format(date)
            return dateStr
        } catch (e: ParseException) {
            e.printStackTrace()
            return ""
        }
    }

    fun getCurrentDateTimeAsString(): String? {
        val sdf =
            SimpleDateFormat(UTC_TIME_FORMAT, Locale.US)
        val time = sdf.format(Date())
        return time
    }


    fun getCurrentDateTimeAsStringOfUAE(formate: String, daysToAdd: Int = 0): String? {
        val sdf =
            SimpleDateFormat(formate, Locale.US)
        sdf.timeZone = TimeZone.getTimeZone(NcmConstants.Timezones.UAE)
        val time = sdf.format(Date())
        val c = Calendar.getInstance()
        try {
            c.time = sdf.parse(time)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        c.add(Calendar.DATE, daysToAdd)
        val ttime = sdf.format(c.time)
        return ttime
    }

    fun getCurrentDateTimeFromTimeZoneWithAddDays(
        timezone: String,
        formate: String,
        daysToAdd: Int = 0
    ): String {
        val sdf =
            SimpleDateFormat(formate, Locale.US)
        sdf.timeZone = TimeZone.getTimeZone(timezone)
        val time = sdf.format(Date())
        val c = Calendar.getInstance()
        try {
            c.time = sdf.parse(time)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        c.add(Calendar.DATE, daysToAdd)
        val ttime = sdf.format(c.time)
        return ttime
    }

    fun getCurrentDateTimeAsString(formate: String): String? {
        val sdf = SimpleDateFormat(formate, Locale.US)
        val time = sdf.format(Date())
        return time
    }

    fun getTimeDifference(time1: String, time2: String, formate: String): Int {
        val sdf = SimpleDateFormat(formate, Locale.ENGLISH)
        try {
            val date1: Date = sdf.parse(time1.replace("Z", "").replace("z", ""))
            val date2: Date = sdf.parse(time2.replace("Z", "").replace("z", ""))
            return date1.compareTo(date2)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return 0
    }


    fun isDateBefore(time1: String, time2: String, format: String): Boolean {
        val sdf = SimpleDateFormat(format, Locale.ENGLISH)
        try {
            val date1: Date = sdf.parse(time1)
            val date2: Date = sdf.parse(time2)
            return date1.before(date2)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return false
    }

    fun isDateAfter(time1: String, time2: String, format: String): Boolean {
        val sdf = SimpleDateFormat(format, Locale.ENGLISH)
        try {
            val date1: Date = sdf.parse(time1)
            val date2: Date = sdf.parse(time2)
            return date1.after(date2)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return false
    }

    fun isSameDate(time1: String, time2: String, format: String): Boolean {
        val sdf = SimpleDateFormat(format, Locale.ENGLISH)
        try {
            val date1: Date = sdf.parse(time1)!!
            val date2: Date = sdf.parse(time2)!!
            return date1.equals(date2)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return false
    }


    fun getTimeDifferenceInHoursWithoutNegative(
        time1: String,
        time2: String,
        formate: String,
        dateDiff: DateTimeUnits?
    ): Long {
        val sdf = SimpleDateFormat(formate, Locale.ENGLISH)
        try {
            val date1: Date = sdf.parse(time1)!!
            val date2: Date = sdf.parse(time2)!!
            var diff = date1.time - date2.time
            if (date1.time < date2.time) {
                diff = date2.time - date1.time
            }
            val seconds = diff / 1000
            val minutes = seconds / 60
            val hours = minutes / 60
            val days = hours / 24
            when (dateDiff) {
                DateTimeUnits.DAYS -> {
                    return days
                }

                DateTimeUnits.HOURS -> {
                    return hours
                }

                DateTimeUnits.MINUTES -> {
                    return minutes
                }

                DateTimeUnits.SECONDS -> {
                    return seconds
                }

                else -> {
                    return diff
                }
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return 0
    }

    fun getTimeDifferenceInHours(
        time1: String,
        time2: String,
        formate: String,
        dateDiff: DateTimeUnits?
    ): Long {
        val sdf = SimpleDateFormat(formate, Locale.ENGLISH)
        try {
            val date1: Date = sdf.parse(time1)!!
            val date2: Date = sdf.parse(time2)!!
            var diff = date1.time - date2.time
            val seconds = diff / 1000
            val minutes = seconds / 60
            val hours = minutes / 60
            val days = hours / 24
            when (dateDiff) {
                DateTimeUnits.DAYS -> {
                    return days
                }

                DateTimeUnits.HOURS -> {
                    return hours
                }

                DateTimeUnits.MINUTES -> {
                    return minutes
                }

                DateTimeUnits.SECONDS -> {
                    return seconds
                }

                else -> {
                    return diff
                }
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return 0
    }

    fun getDayDate(string: String, returnFormate: String): String {
        val dateSplit: List<String> = string.split("+")
        val dateStr = dateSplit[0].replace("GMT", "").replace("T", " ")
        val finalDateStr = convertDateStringXFormatToYFormat(
            dateStr,
            NcmConstants.DATE_TIME_FORMAT,
            returnFormate,
            ""
        )
        return finalDateStr!!
    }

    fun getDayDateArabic(string: String, returnFormate: String): String {
        val dateSplit: List<String> = string.split("+")
        val dateStr = dateSplit[0].replace("GMT", "").replace("T", " ")
        val finalDateStr = convertDateStringXFormatToYFormatArabic(
            dateStr,
            NcmConstants.DATE_TIME_FORMAT,
            returnFormate,
            ""
        )
        return finalDateStr!!
    }


    fun getDateFromTimeZone(zone: ZonedDateTime?, b: Boolean, context: Context?): String {
        val reqFormat =
            if (b) NcmConstants.DATE_DISPLAY_hh_mm_aa else if (context != null && NCMUtility.isArabic()
            ) NcmConstants.DATE_DISPLAY_DAY_MONTH_AR else NcmConstants.DATE_DISPLAY_DAY_MONTH_OLD
        val formatter = DateTimeFormatter.ofPattern(DATE_DISPLAY_yyyy_MM_dd__hh_mm_ss)
        val timeFinal = zone!!.format(formatter)
        val time = convertDateStringXFormatToYFormat(
            timeFinal,
            DATE_DISPLAY_yyyy_MM_dd__hh_mm_ss,
            reqFormat,
            ""
        )
        return time!!
    }


    fun getSunMoonCurrentPosition(startDate: String, endDate: String): Float {

        try {
            if (startDate.isEmpty() && endDate.isEmpty()) return 0.0f
            val currentDate = getCurrentDateTime(DATE_TIME_FORMAT)
            when {
                isDateBefore(currentDate, startDate, DATE_TIME_FORMAT) -> {
                    return 0.0f
                }

                isDateAfter(currentDate, endDate, DATE_TIME_FORMAT) -> {
                    return 1.0f
                }

                else -> {
                    val diff: Int = getDateDiff(endDate, startDate, DateTimeUnits.SECONDS)
                    val diff2: Int = getDateDiff(startDate, currentDate, DateTimeUnits.SECONDS)
                    var actualDiff = diff2.toFloat() / diff.toFloat()
                    val value = NCMUtility.round(actualDiff, 1)
                    actualDiff = abs(value)
                    return actualDiff
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            return 0.0f
        }
    }


    fun convertMapPlayDateTime(
        dateStr: String?,
        xFormat: String?,
        yFormat: String?,
        isAddHours: Boolean = true
    ): String? {
        try {
            var dateStr = dateStr
            val originalFormat: DateFormat =
                SimpleDateFormat(xFormat, Locale.US)
            val targetFormat: DateFormat =
                SimpleDateFormat(yFormat, Locale.US)
            targetFormat.timeZone = TimeZone.getTimeZone(timeZone)
            originalFormat.timeZone = TimeZone.getTimeZone(timeZone)
            val hourPlus = if (isAddHours) 4 else 0
            val date = originalFormat.parse(dateStr)
            val utcDateTime = originalFormat.format(date)
            val sdfOrignal = SimpleDateFormat(xFormat, Locale.US)
            val sdf = SimpleDateFormat(yFormat, Locale.US)
            val c = Calendar.getInstance()
            c.time = sdfOrignal.parse(utcDateTime)!!
            c.add(Calendar.HOUR, +hourPlus)
            val resultDate = Date(c.timeInMillis)
            dateStr = sdf.format(resultDate)
            return if (NCMUtility.isUAETimeUnit())
                dateStr
            else
                utcDateTime

            return dateStr
        } catch (e: ParseException) {
            e.printStackTrace()
            return ""
        }
    }

    fun convertTideDateToTime(
        dateStr: String?,
        xFormat: String?,
        yFormat: String?
    ): String? {
        try {
            var dateStr = dateStr
            val originalFormat: DateFormat =
                SimpleDateFormat(xFormat, Locale.US)
            val targetFormat: DateFormat =
                SimpleDateFormat(yFormat, Locale.US)
            targetFormat.timeZone = TimeZone.getTimeZone(timeZone)
            originalFormat.timeZone = TimeZone.getTimeZone(timeZone)
            val date = originalFormat.parse(dateStr)
            val utcDateTime = originalFormat.format(date)
            val sdfOrignal = SimpleDateFormat(xFormat, Locale.US)
            val sdf = SimpleDateFormat(yFormat, Locale.US)
            val c = Calendar.getInstance()
            c.time = sdfOrignal.parse(utcDateTime)!!
            val resultDate = Date(c.timeInMillis)
            dateStr = sdf.format(resultDate)
            return if (NCMUtility.isUAETimeUnit())
                dateStr
            else
                utcDateTime

            return dateStr
        } catch (e: ParseException) {
            e.printStackTrace()
            return ""
        }
    }

    fun getForeCastDateTime(defaultTime: String, foreCastTime: String): String {
        try {
            if (foreCastTime.startsWith("PT")) {
                var timeToAdd = foreCastTime.replace("PT", "")
                timeToAdd = timeToAdd.replace("H", "")
                timeToAdd = timeToAdd.replace("D", "")

                var hours: Int = timeToAdd.toInt()

                if (foreCastTime.contains("D", true)) {
                    hours *= 24
                }
                val date = defaultTime.replace("Z", "")
                val sdf = SimpleDateFormat(DATE_TIME_FORMAT_MAP, Locale.ENGLISH)
                val c = Calendar.getInstance()
                c.time = sdf.parse(date)!!
                c.add(Calendar.HOUR, +hours)
                val resultDate = Date(c.timeInMillis)
                return sdf.format(resultDate)
            } else {
                return ""
            }
        } catch (ex: Exception) {
            return defaultTime
        }
    }

    fun getDateWithOneDayPlus(dateStr: String, dateTimeFormat: String): String {
        return try {
            val sdf = SimpleDateFormat(dateTimeFormat, Locale.ENGLISH)
            val c = Calendar.getInstance()
            c.time = sdf.parse(dateStr)!!
            c.add(Calendar.HOUR, +24)
            val resultDate = Date(c.timeInMillis)
            sdf.format(resultDate)
        } catch (ex: Exception) {
            dateStr
        }
    }

    fun getCurrentDateTimeAccordingToTimeZone(timezone: String?, format: String): String {
        val sdf = SimpleDateFormat(format, Locale.ENGLISH)
        sdf.timeZone = TimeZone.getTimeZone(timezone)
        val calendar = Calendar.getInstance()
        val currentDateTime = Date(calendar.timeInMillis)
        return sdf.format(currentDateTime)
    }

    fun getOneDayBeforeDate(timezone: String?, format: String): String {
        val sdf = SimpleDateFormat(format, Locale.ENGLISH)
        sdf.timeZone = TimeZone.getTimeZone(timezone)
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -1)
        val currentDateTime = Date(calendar.timeInMillis)
        return sdf.format(currentDateTime)
    }

    fun convertDateTimeAccordingToTimeZone(
        timezone: String?,
        dateTime: String,
        format: String
    ): String {
        val formatter = DateTimeFormatter.ofPattern(format)
        val dateTimeFromServer = dateTime.replace("Z", "")
        return LocalDateTime.parse(dateTimeFromServer, formatter)
            .atOffset(ZoneOffset.UTC)
            .atZoneSameInstant(ZoneId.of(timezone))
            .format(formatter)
    }

    fun getDayOfMonthSuffix(dd: String): String? {
        var n = 0
        try {
            n = dd.replace(" ", "").toInt()
        } catch (ex: Exception) {
        }
        if (n == 0) {
            return ""
        }
        return if (n >= 11 && n <= 13) {
            "th"
        } else when (n % 10) {
            1 -> "st"
            2 -> "nd"
            3 -> "rd"
            else -> "th"
        }
    }

    fun getLocalTimeFromUTC(timeUtc: String, format: String, timezone: String?): String {
        var time = timeUtc
        try {
            var zone = timezone
            try {
//                var rawOffset = TimeZone.getTimeZone(timezone).rawOffset.toLong()
//                var zoneValue = TimeUnit.HOURS.convert(rawOffset, TimeUnit.MILLISECONDS)
//                zone = "GMT${if(zoneValue < 0) "" else "+"}$zoneValue"
            } catch (ex: Exception) {

            }
            if (!zone.isNullOrEmpty()) {
                val utcFormat: DateFormat = SimpleDateFormat(format, Locale.ENGLISH)
                utcFormat.timeZone = TimeZone.getTimeZone("UTC")
                var utcDate = utcFormat.parse(time)
                val localFormat: DateFormat = SimpleDateFormat(format, Locale.ENGLISH)
                localFormat.timeZone = TimeZone.getTimeZone(zone)
                time = localFormat.format(utcDate)
            }
        } catch (ex: Exception) {
        }
        return time
    }

    fun getUTCTimeFromLocal(timeLocal: String, format: String, timezone: String?): String {
        var time = timeLocal
        try {
            var zone = timeZone
            try {
                var rawOffset = TimeZone.getTimeZone(timezone).rawOffset.toLong()
                var zoneValue = TimeUnit.HOURS.convert(rawOffset, TimeUnit.MILLISECONDS)
                zone = "GMT${if (zoneValue < 0) "" else "+"}$zoneValue"
            } catch (ex: Exception) {

            }
            if (!zone.isNullOrEmpty()) {
                val utcFormat: DateFormat = SimpleDateFormat(format, Locale.ENGLISH)
                utcFormat.timeZone = TimeZone.getTimeZone(zone)
                var utcDate = utcFormat.parse(time)
                val localFormat: DateFormat = SimpleDateFormat(format, Locale.ENGLISH)
                localFormat.timeZone = TimeZone.getTimeZone("UTC")
                time = localFormat.format(utcDate)
            }
        } catch (ex: Exception) {
        }
        return time
    }

    fun getCurrentActualIndex(isNWP: Boolean, list: ArrayList<String>): Int {
        var formatToCompare = NcmConstants.TIMESTAMP_FORMAT_TILE_2
        var currentTime = getCurrentDateTimeAccordingToTimeZone("UTC", formatToCompare)
        for (index in list.indices) {
            val date = list[index].replace("Z", "")
            val dateToCompare =
                convertDateStringXFormatToYFormat(
                    date,
                    DATE_TIME_FORMAT_MAP,
                    formatToCompare,
                    ""
                )
            dateToCompare?.let {
                if (isTimeAfter(dateToCompare, currentTime, formatToCompare)) {
                    return index
                }
            }
        }
        /*
           val foundIndex = list.indexOfFirst {
                val date = it.replace("Z", "")
                val dateToCompare =
                    convertDateStringXFormatToYFormat(
                        date,
                        DATE_TIME_FORMAT_MAP,
                        formatToCompare,
                        ""
                    )
                dateToCompare.equals(currentTime, false)
            }
            if (foundIndex > -1) {
                printLog(
                    "TIME_CHECK",
                    "Return from 1st method , Current Time : $currentTime and match time : " + list[foundIndex]
                )
                return foundIndex

        }*/



        return 0
    }

    private fun isTimeAfter(dateTime1: String, dateTime2: String, pattern: String): Boolean {
        return try {
            val formatter = DateTimeFormatter.ofPattern(pattern)
            val dt1 = LocalDateTime.parse(dateTime1, formatter)
            val dt2 = LocalDateTime.parse(dateTime2, formatter)

            val time1 = dt1.toLocalTime()
            val time2 = dt2.toLocalTime()

            return time1.isAfter(time2)
        } catch (e: Exception) {
            false
        }
    }

    fun getTimeAgo(context: Context, seconds: Double, style: DateTimeStyle): String {
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        var phrase: String = ""
        val s: String
        if (seconds <= 0) {
            phrase = context.getString(R.string.time_ago_now)
        } else if (seconds < 45) {
            s =
                if (style == DateTimeStyle.AGO_FULL_STRING) context.getString(R.string.time_ago_full_seconds) else context.getString(
                    R.string.time_ago_seconds
                )
            phrase = String.format(s, Math.round(seconds))
        } else if (seconds < 90) {
            s =
                if (style == DateTimeStyle.AGO_FULL_STRING) context.getString(R.string.time_ago_full_minute) else context.getString(
                    R.string.time_ago_minute
                )
            phrase = String.format(s, 1)
        } else if (minutes < 45) {
            s =
                if (style == DateTimeStyle.AGO_FULL_STRING) context.getString(R.string.time_ago_full_minutes) else context.getString(
                    R.string.time_ago_minutes
                )
            phrase = String.format(s, Math.round(minutes))
        } else if (minutes < 90) {
            s =
                if (style == DateTimeStyle.AGO_FULL_STRING) context.getString(R.string.time_ago_full_hour) else context.getString(
                    R.string.time_ago_hour
                )
            phrase = String.format(s, 1)
        } else if (hours < 24) {
            s =
                if (style == DateTimeStyle.AGO_FULL_STRING) context.getString(R.string.time_ago_full_hours) else context.getString(
                    R.string.time_ago_hours
                )
            phrase = String.format(s, Math.round(hours))
        }
        return phrase
    }

    fun getTimeAgo(context: Context, seconds: Long): String {
        val day = TimeUnit.SECONDS.toDays(seconds).toInt()
        val hours = TimeUnit.SECONDS.toHours(seconds) - day * 24
        val minute = TimeUnit.SECONDS.toMinutes(seconds) -
                TimeUnit.SECONDS.toHours(seconds) * 60
        val second = TimeUnit.SECONDS.toSeconds(seconds) -
                TimeUnit.SECONDS.toMinutes(seconds) * 60
        val string =
            "$hours ${context.getString(R.string.hours)} ${context.getString(R.string.and)} $minute ${
                context.getString(R.string.minutes)
            } "
        return string
    }

    fun getTimeAgoWithHMN(context: Context, seconds: Long): String {
        val day = TimeUnit.SECONDS.toDays(seconds).toInt()
        val hours = TimeUnit.SECONDS.toHours(seconds) - day * 24
        val minute = TimeUnit.SECONDS.toMinutes(seconds) -
                TimeUnit.SECONDS.toHours(seconds) * 60
        val second = TimeUnit.SECONDS.toSeconds(seconds) -
                TimeUnit.SECONDS.toMinutes(seconds) * 60
        val string =
            "$hours ${context.getString(R.string.h)} $minute ${context.getString(R.string.mn)}"
        return string
    }


    fun set1HourBefore(
        dateStr: String?,
        dateFormat: String?
    ): String {
        try {
            var dateStr = dateStr
            val originalFormat: DateFormat =
                SimpleDateFormat(dateFormat, Locale.US)
            val targetFormat: DateFormat =
                SimpleDateFormat(dateFormat, Locale.US)
            targetFormat.timeZone = TimeZone.getTimeZone(timeZone)
            originalFormat.timeZone = TimeZone.getTimeZone(timeZone)
            val date = originalFormat.parse(dateStr)
            val utcDateTime = originalFormat.format(date)
            val sdfOrignal = SimpleDateFormat(dateFormat, Locale.US)
            val sdf = SimpleDateFormat(dateFormat, Locale.US)
            val c = Calendar.getInstance()
            c.time = sdfOrignal.parse(utcDateTime)!!
            c.add(Calendar.MINUTE, -45)
            val resultDate = Date(c.timeInMillis)
            dateStr = sdf.format(resultDate)
            return dateStr
        } catch (e: ParseException) {
            e.printStackTrace()
            return ""
        }
    }


    fun getDateTimeStamp(
        dateStr: String?,
        dateFormat: String?
    ): Long {
        return try {
            val simpleDateFormat = SimpleDateFormat(dateFormat)
            val date = simpleDateFormat.parse(dateStr)
            date.time
        } catch (e: ParseException) {
            e.printStackTrace()
            0
        }
    }


//    fun getLocalTimeIfIsUAE(utcTime: String?, format: String): String? {
//        if(utcTime == null || !NCMSUtils.isUAETimeUnit()){
//            return utcTime
//        } else {
//            return utcTime
//        }
//    }
}