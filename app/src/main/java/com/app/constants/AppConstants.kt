package com.app.constants

import android.graphics.Color
import com.app.models.MenuItemModel
import java.util.Locale


const val DATABASE_NAME = "app-database"


object AppConstants {

    const val IMAGE_DIRECTORY_NAME = "AppDirectory"
    var SELECTED_MENU_ID: MenuItemModel.MenuID = MenuItemModel.MenuID.HOME


    const val DISPLAY_DATE = "EEE, dd MMM yyyy"
    const val DISPLAY_GET = "dd/MM/yyyy"
    const val DATE_FORMAT_ = "yyyy-MM-dd"
    const val TIME_FORMAT_ = "HH:mm:ss"
    const val TIME_FORMAT_SEND = "HH:mm"
    const val TIME_DISPLAY_ = "hh:mm aa"


    object PrefConstants {
        const val APP_PREFS = "APP_PREFS"
        const val APP_LANGUAGE = "APP_LANGUAGE"
        const val DISPLAY_MAPS_TYPE = "DISPLAY_MAPS_TYPE"
        const val IS_FROM_MENU = "IS_FROM_MENU"
        const val IS_CLOUD_ENABLED = "IS_CLOUD_ENABLED"
        const val CALIBRATE_POPUP_SHOWN = "CALIBRATE_POPUP_SHOWN"
        const val IS_MAP_FROM_SIDE_MENU = "IS_MAP_FROM_SIDE_MENU"
        const val WARNINGS_BADGE_COUNT = "WARNINGS_BADGE_COUNT"
        const val APP_MAP_ACCESS_TOKEN = "APP_MAP_ACCESS_TOKEN"
        const val APP_MAP_BASE_URL = "APP_MAP_BASE_URL"
        const val APP_MAP_ICON_BASE_URL = "APP_MAP_ICON_BASE_URL"
        const val APP_MAP_LAYER_DATA = "APP_MAP_LAYER_DATA"
        const val APP_MAP_LAYER_DATA_TITLE = "APP_MAP_LAYER_DATA_TITLE"
    }


    object WebURL {
        const val BASE_URL = "https://www.baseurl.com/app/"

    }

    object NestedProgress {
        val COLOR_INNER = Color.parseColor("#8B0013")
        val COLOR_OUTER = Color.parseColor("#338B0013")

        const val CIRCLE_RADIUS = 360
        const val ANIM_DURATION = 1000
        const val INNER_ANIM_INTERPOLATOR = 6
        const val OUTER_ANIM_INTERPOLATOR = 5
        const val INNER_LOADER_LENGTH = 260F
        const val OUTER_LOADER_LENGTH = 320F
        const val INNER_STROKE_WIDTH = 4F
        const val OUTER_STROKE_WIDTH = 4.5F
        const val MID_POINT = 2F
        const val START_POINT = 0F
        const val DESIRED_WH = 70F
        const val MAX_TOTAL_STROKE = 21F
        const val MIN_STOKE = 0F
        const val MAX_STROKE = 10F
        const val MIN_B_CIRCLES = 0F
        const val MAX_B_CIRCLES = 10F
        const val SPACE_BETWEEN_CIRCLES = 3F

    }



    val localeEnglish = Locale("en", "US")
    val localeArabic = Locale("ar", "AE")
    val ARABIC = "ar-AE"
    val ENGLISH = "en-US"


}