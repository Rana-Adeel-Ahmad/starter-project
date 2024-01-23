package com.ncms.module.constants

import android.app.Activity
import com.google.gson.Gson
import java.util.*

object NcmConstants {
    val nearByDetailsDistance = 200000
    val chartNullValue = 0.9895978f
    var IS_EARTHQUAKE_SELECTION_ENABLED: Boolean = true
    var isNotificationReceived = false
    val App_DEFAULT_DELAY: Long = 500

    // 1000 milliseconds == 1 second
    const val SHORT_INTERVAL: Long = 20 * 1000
    const val NORMAL_INTERVAL: Long = 60 * 1000


    const val WEATHER_INTERVAL: Long = 60 * 1000 * 2 // 2 minutes
    const val SEA_INTERVAL: Long = 60 * 1000 * 5 // 5 minutes
    const val WARNING_INTERVAL: Long = 60 * 1000 * 1 // 1 minutes
    const val EVENTS_INTERVAL: Long = 60 * 1000 * 2  // 2 minutesWar
    const val FULL_MAPS_INTERVAL: Long = 60 * 1000 * 2  // 2 minutes
    const val UPDATE_RADAR_SECTION_INTERVAL: Long = 30 * 1000  // 30 seconds

    const val DEFAULT_CITY_ID: Long = 24813
    const val DEFAULT_STATION_ID: Long = 26
    const val GPS_ENABLED_CHANGE_ACTION = "android.location.PROVIDERS_CHANGED"
    val countryCodeWorld = "countryCodeWorld"
    val METHOD_UAE = "ecmwf-uae"
    val METHOD_GLOBAL = "ecmwf-global"

    val EMIRATE_ID_PATTERN: String = "\\d{3}-\\d{4}-\\d{7}-\\d{1}"
    const val LANGUAGE_ID: String = "1"
    const val TOKEN = "AuthorizationToken"

    const val LANGUAGE = "language"
    const val APP_THEME_GRAY = "app_theme"
    const val TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss"
    const val TIMESTAMP_FORMAT_2 = "yyyyMMddHHmm"
    const val TIMESTAMP_FORMAT_TILE = "yyyyMMddHH"
    const val TIMESTAMP_FORMAT_TILE_2 = "yyyyMMddHHmm"
    const val TIMESTAMP_FORMAT_SAT = "yyyyMMdd/yyyyMMdd_HHmm"
    const val DATE_FORMAT_1 = "yyyyMMdd"
    const val DATE_DISPLAY_6 = "dd/MM/yyyy HH:mm"
    const val DATE_DISPLAY_5 = "dd/MM/yyyy - HH:mm"
    const val DATE_DISPLAY_4 = "dd/MM | HH:mm"
    const val DATE_DISPLAY_4_AR = "MM/dd | HH:mm"
    const val DATE_DISPLAY_3 = "dd-MM-yyyy  HH:mm"
    const val DATE_DISPLAY_SIMPLE = "dd-MM-yyyy"
    const val DATE_DISPLAY_HH_MM = "HH:mm"
    const val DATE_HH_MM = "HH:mm"
    const val DATE_DISPLAY_SIMPLE_ = "yyyy-MM-dd"
    const val DATE_DISPLAY_2 = "MMM dd, yyyy"
    const val DATE_DISPLAY_ = "dd MMM, yyyy"
    const val DATE_DISPLAY_yyyy_MM_dd = "yyyy-MM-dd"
    const val DATE_DISPLAY_yyyy_MM_dd__hh_mm_ss = "yyyy-MM-dd HH:mm:ss"
    const val DATE_DISPLAY_yyyy_MM_dd_t_hh_mm_ss = "yyyy-MM-ddTHH:mm:ss"
    const val DATE_DISPLAY_hh_mm_aa = "HH:mm"
    const val TIME_FORMAT_24 = "HH:mm"
    const val TIME_FORMAT_24_without_separator = "HHmm"
    const val TIME_DATE = "dd"
    const val TIME_MONTH = "MM"
    const val SHORT_MONTH_NAME = "MMM"
    const val TIME_MINUTES = "mm"
    const val TIME_HOURS = "HH"
    const val UTC_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ"
    const val DATE_TIME_FORMAT_MAP = "yyyy-MM-dd'T'HH:mm:ss"
    const val DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"
    const val DATE_TIME_FORMAT_WEB_CAM = "dd/MM/yyyy - HH:mm"
    const val DATE_DISPLAY_DAY_MONTH_OLD = "dd/MM"
    const val DATE_DISPLAY_DAY_MONTH = "EEEE dd/MM"
    const val DATE_DISPLAY_DAY_MONTH_AR_ = "EEEE MM/dd"
    const val DATE_DISPLAY_DAY_MONTH_AR = "MM/dd"
    const val DATE_DISPLAY_DAY_MONTH_YEAR = "dd/MM/yyyy"
    const val DATE_FORMAT_DAY_DATE_TIME = "dd/MM HH:mm"
    const val DATE_FORMAT_DAY = "EE"
    const val DATE_FORMAT_DAY_FULL = "EEEE"
    const val PRAYER_TIME_STORE_FORMATE = "yyyy-MM-dd HH:mm:ss"
    const val ALMANAC_DATE_FORMAT = "dd MMMM"


    const val FAVOURITES_LIMIT: Int = 10

    const val INTENT_OPEN_INDEX = "INTENT_OPEN_INDEX"
    const val DEFAULT_CITY_ID_TO_LOAD = "DEFAULT_CITY_ID_TO_LOAD"
    const val BUILD = "2.0"
    const val threeDURL = "https://www.rain.ae/radar3d"
    const val twoDURL = "https://www.rain.ae/radars/uae"

    val localeEnglish = Locale("en", "US")
    val localeArabic = Locale("ar", "AE")

    var NWP_SELECTED_INDEX = 0

    object LoaderStyle {
        const val BallPulseIndicator = "BallPulseIndicator"
        const val BallGridPulseIndicator = "BallGridPulseIndicator"
        const val BallClipRotateIndicator = "BallClipRotateIndicator"
        const val BallClipRotatePulseIndicator = "BallClipRotatePulseIndicator"
        const val SquareSpinIndicator = "SquareSpinIndicator"
        const val BallClipRotateMultipleIndicator = "BallClipRotateMultipleIndicator"
        const val BallPulseRiseIndicator = "BallClipRotateMultipleIndicator"
        const val BallRotateIndicator = "BallRotateIndicator"
        const val CubeTransitionIndicator = "CubeTransitionIndicator"
        const val BallZigZagIndicator = "BallZigZagIndicators"
        const val BallZigZagDeflectIndicator = "BallZigZagDeflectIndicator"
        const val BallTrianglePathIndicator = "BallTrianglePathIndicator"
        const val BallScaleIndicator = "BallScaleIndicator"
        const val LineScaleIndicator = "LineScaleIndicator"
        const val LineScalePartyIndicator = "LineScalePartyIndicator"
        const val BallScaleMultipleIndicator = "BallScaleMultipleIndicator"
        const val BallPulseSyncIndicator = "BallPulseSyncIndicator"
        const val BallBeatIndicator = "BallBeatIndicator"
        const val LineScalePulseOutIndicator = "LineScalePulseOutIndicator"
        const val LineScalePulseOutRapidIndicator = "LineScalePulseOutRapidIndicator"
        const val BallScaleRippleIndicator = "BallScaleRippleIndicators"
        const val BallScaleRippleMultipleIndicator = "BallScaleRippleMultipleIndicator"
        const val BallSpinFadeLoaderIndicator = "BallSpinFadeLoaderIndicator"
        const val LineSpinFadeLoaderIndicator = "LineSpinFadeLoaderIndicator"
        const val TriangleSkewSpinIndicator = "TriangleSkewSpinIndicator"
        const val PacmanIndicator = "PacmanIndicator"
        const val BallGridBeatIndicator = "BallGridBeatIndicator"
        const val SemiCircleSpinIndicator = "SemiCircleSpinIndicator"
    }

    object PrefConstants {
        const val APP_PREFS = "APP_PREFS"
        const val APP_LANGUAGE = "APP_LANGUAGE"
        const val IS_FROM_SETTINGS = "IS_FROM_SETTINGS"
        const val APP_THEME = "APP_THEME"
        const val APP_THEME_PREFER = "APP_THEME_PREFER"
        const val APP_THEME_AUTO = 0
        const val APP_THEME_DARK = 1
        const val APP_THEME_LIGHT = 2
        const val APP_FIRST_RUN = "APP_FIRST_RUN"
        const val APP_IS_SHOW_TUTORIAL = "APP_IS_SHOW_TUTORIAL"

        const val APP_TEMP_UNIT = "APP_TEMP_UNIT"
        const val APP_TIME_UNIT = "APP_TIME_UNIT"
        const val APP_WIND_UNIT = "APP_WIND_UNIT"
        const val APP_FOG_DUST_SAND = "APP_FOG_DUST_SAND"
        const val APP_WIND_ROUGH_SEA = "APP_WIND_ROUGH_SEA"
        const val APP_IS_MM_PRECIPITATION = "APP_IS_MM_PRECIPITATION"
        const val APP_IS_MM_WAVE_HEIGHT = "APP_IS_MM_WAVE_HEIGHT"
        const val APP_THUNDER_RAIN_SB_CLOUDS = "APP_WIND_RAIN_SB_CLOUDS"
        const val MAP_LAYERS_DATA = "map_layers_data"
        const val WIDGET_DATA = "WIDGET_DATA"
        const val APP_MAP_ACCESS_TOKEN = "APP_MAP_ACCESS_TOKEN"
        const val APP_MAP_BASE_URL = "APP_MAP_BASE_URL"
        const val APP_MAP_ICON_BASE_URL = "APP_MAP_ICON_BASE_URL"
        const val APP_MAP_LAYER_DATA = "APP_MAP_LAYER_DATA"
        const val APP_MAP_LAYER_DATA_TITLE = "APP_MAP_LAYER_DATA_TITLE"
        const val APP_FORECAST_DATA = "APP_FORECAST_DATA"
        const val APP_PRAYERS_DATA = "APP_PRAYERS_DATA_OLD"
        const val APP_PRAYERS_DATA_OLD = "APP_PRAYERS_DATA_OLD"
        const val CURR_COUNTRY_DATA = "CURR_COUNTRY_DATA"
        const val APP_FORECAST_DATA_DATE = "APP_FORECAST_DATA_DATE"
        const val APP_RELOAD_CITIES = "APP_RELOAD_CITIES"
        const val APP_FORECAST_DATA_DATE_HOURLY_SAVE_TIME =
            "APP_FORECAST_DATA_DATE_HOURLY_SAVE_TIME"
        const val APP_FORECAST_CITY_HOURLY_DATA = "APP_FORECAST_CITY_HOURLY_DATA"
        const val CITY_TYPE = "CITY_TYPE"
        const val CITY_POSITION = "CITY_POSITION"
        const val APP_VIP_EDITION = "APP_VIP_EDITION"
        const val IS_CURRENT_CITY = "IS_CURRENT_CITY"
        const val IS_INIT_LANGUAGE = "IS_INIT_LANGUAGE"
        const val IS_ENABLE_FROM_SETTINGS = "IS_ENABLE_FROM_SETTINGS"
        const val NOTIFICATION_RECEIVED = "NOTIFICATION_RECEIVED"
        const val SHOW_CURRENT_LOCATION = "SHOW_CURRENT_LOCATION"
        const val PREF_BACKGROUND_REFRESH = "BACKGROUND_REFRESH"
        const val CURRENT_LOCATION_INDEX = "CURRENT_LOCATION_INDEX"
        const val TEMP_FAVOURITE_CITY = "TEMP_FAVOURITE_CITY"
        const val STATIONS_LOADED_DATA = "STATIONS_LOADED_DATA"
        const val IS_FROM_SPLASH_WHEN_LOCATION_IS_OFF = "IS_FROM_SPLASH_WHEN_LOCATION_IS_OFF"
        const val PDF_TYPE = "PDF_TYPE"
        const val URL = "URL"
        const val PDF_TYPE_ONLY_VIEW = 99
        const val PDF_TYPE_UAE = 100
        const val PDF_TYPE_MARINE = 200
        const val DISPLAY_MAPS_TYPE = "DISPLAY_MAPS_TYPE"
        const val DISPLAY_MAP_LAYER_ID = "DISPLAY_MAP_LAYER_ID"
        const val IS_FROM_MENU = "IS_FROM_MENU"
        const val IS_CLOUD_ENABLED = "IS_CLOUD_ENABLED"
        const val CALIBRATE_POPUP_SHOWN = "CALIBRATE_POPUP_SHOWN"
        const val IS_MAP_FROM_SIDE_MENU = "IS_MAP_FROM_SIDE_MENU"
        const val WARNINGS_BADGE_COUNT = "WARNINGS_BADGE_COUNT"
        const val UPDATED_DB_VERSION = "UPDATED_DB_VERSION"
        const val MAP_ZOOM = "MAP_ZOOM"
    }


    object EnglishFonts {
        const val LIGHT = "fonts/SF-Pro-Text-Light.otf"
        const val MEDIUM = "fonts/SF-Pro-Text-Medium.otf"
        const val NORMAL = "fonts/SF-Pro-Text-Regular.otf"
        const val BOLD = "fonts/SF-Pro-Text-Bold.otf"
        const val SEMI_BOLD = "fonts/SF-Pro-Text-Semibold.otf"
    }


    object ArabicFonts {
        const val LIGHT = "fonts/SF-Arabic.ttf"
        const val MEDIUM = "fonts/SF-Arabic.ttf"
        const val NORMAL = "fonts/SF-Arabic.ttf"
        const val BOLD = "fonts/SF-Arabic.ttf"
        const val SEMI_BOLD = "fonts/SF-Arabic.ttf"
    }

    object SocialLinks {
        const val TWITTER = "https://twitter.com/NCMUAE"
        const val YOUTUBE = "https://www.youtube.com/c/UAEWeatherChannel"
        const val FACEBOOK = "https://www.facebook.com/NCMUAE"
        const val INSTAGRAM = "https://www.instagram.com/officialuaeweather/"
    }

    object CountryCodes {
        const val uae = "AE"
        const val station = "stationType"
    }

    object DBValue {
        const val ICAO = "icao"
        const val station = "stationType"
    }

    object FCMTopics {
        object ArabicTopics {
            const val fogDustSand = "WARNING_FG_DS_AR"
            const val windRoughSea = "WARNING_WD_RS_AR"
            const val thunderRainSBClouds1 = "WARNING_TR_CC_AR"
            const val other = "WARNING_OTHER_AR"
        }

        object EnglishTopics {
            const val fogDustSand = "WARNING_FG_DS_EN"
            const val windRoughSea = "WARNING_WD_RS_EN"
            const val thunderRainSBClouds1 = "WARNING_TR_CC_EN"
            const val other = "WARNING_OTHER_EN"
        }
    }

    object HijriMonthNames {
        val englishNamesList = arrayOf(
            "Muh.",
            "Saf.",
            "Rab. I",
            "Rab. II",
            "Jum. I",
            "Jum. II",
            "Raj.",
            "Sha.",
            "Ram.",
            "Shaw.",
            "Dhuʻl-Q.",
            "Dhuʻl-H."
        )
        val arabicNamesList = arrayOf(
            "محرم",
            "صفر",
            "ربيع الأول",
            "ربيع الآخر",
            "جمادى الأولى",
            "جمادى الآخرة",
            "رجب",
            "شعبان",
            "رمضان",
            "شوال",
            "ذو القعدة",
            "ذو الحجة"
        )
    }

    object WindDirection {
        const val NORTH = "N"
        const val NORTH_EAST = "NE"
        const val EAST = "E"
        const val SOUTH_EAST = "SE"
        const val SOUTH = "S"
        const val SOUTH_WEST = "SW"
        const val WEST = "W"
        const val NORTH_WEST = "NW"

    }

    object Backgrounds {
        const val weatherBackground = "weatherBackground"
        const val rainBackground = "rainBackground"

    }

    object QueryBy {
        const val icao = "icao"
        const val geonameid = "geonameid"
        const val latlng = "latlng"
    }

    object AddRemoveQuery {
        const val addLocation = "add?location_id="
        const val removeLocation = "remove?location_id="
    }

    object Timezones {
        const val UAE = "Asia/Dubai"
        const val UTC = "utc"
    }


    object MainActivityTabID {
        public const val ID_WEATHER = 1
        public const val ID_SEA = 2
        public const val ID_WARNINGS = 3
        public const val ID_EVENTS = 4
        public const val ID_ROADS = 5
    }

    object MenuIDs {
        public const val MENU_SELECTED_ID: String = "MENU_SELECTED_ID"
        public const val MENU_REQUEST_CODE: Int = 1000
        public const val MENU_WEATHER_TODAY: Int = 99
        public const val MENU_WEATHER_RADARS: Int = 100
        public const val MENU_AWS_WEATHER_STATIONS: Int = 101
        public const val MENU_SATELLITE_IMAGES: Int = 102
        public const val MENU_MARINE_STATIONS: Int = 103
        public const val MENU_EARTHQUAKES: Int = 104
        public const val MENU_AIR_QUALITY_STATIONS: Int = 105
        public const val MENU_WARNINGS: Int = 106
        public const val MENU_DAYS_BULLETIN: Int = 107
        public const val MENU_NWP: Int = 108
        public const val MENU_NWP_MODELS_FORECAST: Int = 109
        public const val MENU_SEA_CONDITION: Int = 110
        public const val MENU_MAIN_ROADS: Int = 111
        public const val MENU_APP_SETTINGS: Int = 112
        public const val MENU_CONTACT_US: Int = 113
        public const val MENU_COPYRIGHT_AND_TERMS: Int = 114
        public const val MENU_PRIVACY_POLICY: Int = 115
        public const val MENU_MARINE_BULLETIN: Int = 116
        public const val MENU_TUTORIAL: Int = 117
        public const val MENU_MARINE_BULLETIN_PDF: Int = 118
        public const val MENU_WEATHER_MAPS: Int = 119

    }

    object SettingsIDs {
        public const val LANGUAGE: Int = 100
        public const val AUTO_DETECT_LOCATION: Int = 101
        public const val COLOR_MODE: Int = 102
        public const val BACKGROUND_REFRESH: Int = 103
        public const val TEMPERATURE: Int = 104
        public const val WIND: Int = 105
        public const val PRECIPITATION: Int = 106
        public const val WAVE_HEIGHT: Int = 107
        public const val FOG_DUST_AND_SAND: Int = 108
        public const val WIND_ROUGH_SEA: Int = 109
        public const val THUNDAR_RAIN_SB_CLOUDS: Int = 110
        public const val DEFAULT_SCREENS: Int = 111

    }

    object MainActivityBottomIDs {
        public const val ID_WORLD = 1
        public const val ID_STATIONS = 2
        public const val ID_MAPS = 3
        public const val ID_SETTINGS = 4
    }

    object DataBaseActionType {
        public const val ADD = "add"
        public const val DELETE = "remove"
        public const val UPDATE = "update"
    }

    object WebURL {
        const val BASE_URL = "https://apps.ncm.ae/mobileapps/api/"
        const val STATIONS = "aws/stations"
        const val AWS_OBSERVATIONS_MAPS = "aws/observations/24h"
        const val EARTHQUAKES = "earthquakes/stations/"
        const val EARTHQUAKES_BULLETINS = "earthquakes/bulletins"
        const val MAP_LAYERS = "map-layers"
        const val WARNINGS_GEO = "warnings/geo"
        const val WARNINGS_GEO_WARNINGS = "warnings/geo-warnings"
        const val PRAYER_TIMES = "prayertimes"
        const val ALMANAC = "almanac"
        const val SAT_VIDEOS = "sat-videos"
        const val BULLETINS = "bulletins"
        const val WEATHER_UPDATE = "weather-updates"
        const val FORECAST_BY_CITY = "forecast/by-cities"
        const val FORECAST_BY_SEA = "forecast/marine"
        const val FORECAST_BY_TIDES = "forecast/tides"
        const val SUN_MOON_INFO = "forecast/sun-moon-info"
        const val MARINE_STATIONS = "marine/stations"
        const val MARINE_BUOYS = "marine/buoys"
        const val MARINE_TIDES = "marine/tides"
        const val WEBCAM = "webcam/"
        const val AIRPORT_STATIONS = "airport/stations"
        const val EARTHQUAKES_EVENTS = "earthquakes/events"
        const val MARINE_FORECAST_LOCATIONS_LIST = "marine/forecast/locations"
        const val MARINE_FORECAST_BY_LOCATION_ID = "marine/forecast/"
        const val RADAR1H = "radar1h"
        const val GET_DB_UPDATES = "nwp-cities/uae/updates/"

    }

    public fun getAPIAppsBaseUrlNo(): String {
        return "https://apps.ncm.ae/"
    }

    object ContactUsKeys {
        const val TEL = "+971 2 222 7777"
        const val FAX = "+971 2 666 1575"
        const val LAT = 24.338353
        const val LNG = 54.641101
        const val GOOGLE_MAP_URL = "https://goo.gl/maps/1qTqwNHFJ8Q8t4po8"
    }

    object WebKeys {
        const val EMS = "ems"
        const val SMS = "sms"
        const val SHMS = "shms"
    }

    object LayerType {
        public const val RADAR = "radar"
        public const val NWP = "nwp"
        public const val SATELLITE = "satellite"
        public const val AWS = "aws"
    }

}