package com.ncms.module.utils

import android.content.Context
import android.os.Build
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.mapbox.android.telemetry.TelemetryUtils
import com.mapbox.mapboxsdk.BuildConfig
import com.mapbox.mapboxsdk.http.HttpIdentifier
import com.ncms.module.constants.NcmConstants
import com.ncms.module.constants.NcmConstants.PrefConstants.MAP_LAYERS_DATA
import com.ncms.module.ext.isArabic
import com.ncms.module.ext.printLog
import com.ncms.module.ext.setFractions
import com.ncms.module.models.maps.layers.MapLayersResponse
import com.ncms.module.models.mapsettings.MapSettingsResponse
import com.ncms.module.preferences.NCMPreferencesHelper
import com.ncms.module.preferences.NCMPreferencesHelper.isCelsiusTemperature
import com.ncms.module.preferences.NCMPreferencesHelper.isKMHWind
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.roundToInt


/**
 * @author Muzzamil
 */

object NCMUtility {

    private const val MAP_STYLE = "MAP_STYLE"
    private const val AWS_MAP_STYLE = "AWS_MAP_STYLE"
    private const val MAP_LAY_SELECTED = "MAP_LAY_SELECTED"

    private const val API_KEY = "data-api-key"
    private const val FORECAST_API_KEY = "forecast-api-key"
    private const val AGENT = "User-Agent"

    public const val MAP_BLUE_STYLE = "/styles/ncm-topo-blue/style.json"
    public const val MAP_GREEN_STYLE = "/styles/ncm-topo-green/style.json"
    public const val MAP_LIGHT_STYLE = "/styles/ncm-bright-terrain/style.json"
    public const val MAP_DARK_STYLE = "/styles/ncm-dark/style.json"
    public const val MAP_HYBRID_STYLE = "/styles/ncm-satellite-hybrid/style.json"
    public const val MAP_LIGHT_STYLE_2 = "/styles/ncm-bathymetry/style.json"
    public const val STATIC_MAP_TOKEN =
        "/Y3EeKtxDm5HYYRoCZP0IYbnyLaBNu7XynQ8hIUoYIRhs3y1Gvc6XpBvFsAqfy5Qzon7UiclCWdQXItKF9MOXvMMea2qJEbiKyId9HB7JAt7WzUc8rny66T4R6Im9fvd9"

    public const val LAYER_ID_RADAR_MERGE_UAE = "radar-Merge-UAE"
    public const val LAYER_ID_RADAR_MERGE_SAT = "radar-Merge-Sat"
    public const val LAYER_ID_RADAR_MERGE_GCC = "radar-Merge-GCC"

    public const val LAYER_ID_RADAR_MERGE_SAT_15 = "satellite-msg-15mn-int"
    public const val LAYER_ID_RADAR_MERGE_SAT_15_COLOR = "satellite-15mn-color"

    public const val LAYER_ID_WAVE_HEIGHT = "cosmo-arab-wave-height"

    public const val MAPS_DISPLAY_RADARS = LAYER_ID_RADAR_MERGE_SAT
    public const val MAPS_DISPLAY_RADARS_2 = LAYER_ID_RADAR_MERGE_UAE
    public const val MAPS_DISPLAY_RADARS_3 = LAYER_ID_RADAR_MERGE_GCC

    public const val SELECTED_NWP_LAYER_ID = LAYER_ID_WAVE_HEIGHT
    public var SELECTED_RADAR_LAYER_ID = LAYER_ID_RADAR_MERGE_UAE


    public const val AWS_TEMP = "AWS_TEMP"
    public const val AWS_SOLAR = "AWS_SOLAR"
    public const val AWS_WIND = "AWS_WIND"
    public const val AWS_HUMIDITY = "AWS_HUMIDITY"
    public const val AWS_AIR_PRESSURE = "AWS_AIR_PRESSURE"
    public const val AWS_VISIBILITY = "AWS_VISIBILITY"
    public const val AWS_DEW_POINT = "AWS_DEW_POINT"

    public const val RESULT_CODE = 1001

    public const val MAPS_DISPLAY_STATIONS = "MAPS_DISPLAY_STATIONS"
    public const val AIR_QUALITY_ID = "AIR_QUALITY_ID"
    public const val SEISMOLOGY_ID = "SEISMOLOGY_ID"
    public const val AL_BAHAR_ID = "AL_BAHAR_ID"
    public const val MAPS_DISPLAY_WARNINGS = "MAPS_DISPLAY_WARNINGS"
    public const val MAPS_DISPLAY_TILES = "MAPS_DISPLAY_TILES"
    public const val MAPS_DISPLAY_SINGLE_MARKER = "MAPS_DISPLAY_SINGLE_MARKER"
    public const val MAPS_DISPLAY_CLOUD_SATELLITE_TILE = "clouds-sat-layer"

    public const val SEISMOLOGY_URL = "https://earthquakes.ncm.ae"
    public const val AIR_QUALITY_URL = "https://airquality.ncm.ae"
    public const val AL_BAHAR_URL = "https://albahar.ncm.ae"

    public const val COPY_RIGHTS_NCOM_URL = "https://mobile.ncm.ae/#map_radar_uae"
    public const val COPY_RIGHTS_OSMC_URL = "https://www.openstreetmap.org/copyright"
    public const val COPY_RIGHTS_MT_URL = "https://www.maptiler.com/copyright/"
    public const val COPY_RIGHTS_METEO_BLUE = "https://www.meteoblue.com/"
    public const val COPY_RIGHTS_MBRSC = "https://www.mbrsc.ae/"

    public const val MAPS_EXTRA_ZOOMED = 9.0
    public const val MAPS_EXTRA_8 = 8.0
    public const val MAPS_EXTRA_12 = 12.0
    public const val MAPS_DEFAULT_ZOOM = 5.6
    public const val MAPS_DEFAULT_LAT = 24.5178091
    public const val MAPS_DEFAULT_LNG = 53.9149608

    val Sunny = "Sunny"
    val ClearSky = "Clear Sky"
    val ClearDay = "Clear Day"
    val MostlySunny = "Mostly Sunny"
    val DustClouds = "Dust Clouds"
    val FogPartialyCloudy = "Fog Partly Cloudy"//
    val PartialyCloudy = "Partly Cloudy"
    val MostlyCloudy = "Mostly Cloudy"
    val PartialyCloudyWithRain = "Partly Cloudy with Rain"
    val PartialyCloudyWithFewRain = "Partly Cloudy with few Rain"
    val PartialyCloudyWithHeavyRain = "Partly Cloudy with heavy Rain"
    val MostlyCloudyWithRainAndThunder = "Mostly Cloudy with Rain and Thunder"
    val PartialyCloudyWithSnow = "Partly Cloudy with Snow"//
    val Cloudy = "Cloudy"
    val CloudyWithRain = "Cloudy with Rain"
    val CloudyWithSnow = "Cloudy with Snow"
    val CloudyWithHail = "Cloudy with Hail"
    val FreezingCloudyWithRain = "Freezing Cloudy with Rain"
    val CloudyWithRainAndSnow = "Cloudy with Rain and Snow"
    val HazeDay = "Haze Day"
    val Fog = "Fog"
    val Mist = "Mist"
    val HotWeather = "Hot Weather"
    val VeryColdFrost = "Very Cold Frost"
    val Windy = "Windy"
    val WindyWithDust = "Windy with Dust"
    val Dusty = "Dusty"
    val VeryHot = "Very Hot"
    val Hot = "Hot"
    val Dust = "Dust"

    val PartlyCloudyWithRainAndThunder = "Partly Cloudy with Rain and Thunder"
    val HazeNight = "Haze Night"
    val Cold = "Cold"
    val HumidFog = "Humid-Fog"
    val EVENT_MARKER_IMAGE = "EventsMarker"
    val SEA_STATION_IMAGE = "SeaStationImage"
    val EVENT_STATION_IMAGE = "EventStationImage"
    val SEA_TIDE_IMAGE = "SeaTideImage"
    val LATEST_EVENT_MARKER_IMAGE = "LATEST_EVENT_MARKER_IMAGE"

    private const val MOBILE_APP_KEY_SECRET = "RStvMleUiKaMlnnpaaz1MFXE8vHd7Mbs9+JSjcdag8Y="

    private const val MOBILE_APP_SETTING_URL =
        "https://apps.ncm.ae/mobile-apps-settings_DsWFAYDU6E2vyk0JIeRqDbjCI0NU9AppXQ-V_TYpenFY/apps-settings-uaeweather.json"

    fun getAppSettingURL(): String {
        return MOBILE_APP_SETTING_URL
    }


    fun getHeadersAppSettings(): HashMap<String, String> {
        val header = HashMap<String, String>()
        header["mobileappskey"] = getMobileAppApiKey()
        header["User-Agent"] = userAgentString
        return header
    }

    fun getHeadersForeCast(): HashMap<String, String> {
        val header = HashMap<String, String>()
        header["User-Agent"] = userAgentString
        return header
    }

    private val userAgentString = TelemetryUtils.toHumanReadableAscii(
        String.format(
            "%s %s (%s) Android/%s (%s)",
            HttpIdentifier.getIdentifier(),
            BuildConfig.MAPBOX_VERSION_STRING,
            BuildConfig.GIT_REVISION_SHORT,
            Build.VERSION.SDK_INT,
            Build.CPU_ABI
        )
    )

    fun getAPIAppsBaseUrl(): String {
        var url = getAppApiURL()
        return if (url.endsWith("/")) {
            url
        } else {
            "$url/"
        }
    }

    fun getApiNwpBaseUrl(): String {
        var url = getNwpURL()
        return if (url.endsWith("/")) {
            url
        } else {
            "$url/"
        }
        return url
    }

    public fun getMapURL(): String {
        val mapAccessToken =
            if (getMapAccessToken().isNullOrEmpty()) "" else "/" + getMapAccessToken()
        return getMapBaseURL() + mapAccessToken
    }

    public fun getMapAccessToken(): String? {
        return NCMPreferencesHelper.getStringPrefs(NcmConstants.PrefConstants.APP_MAP_ACCESS_TOKEN)
    }

    public fun getMapBaseURL(): String? {
        return NCMPreferencesHelper.getStringPrefs(NcmConstants.PrefConstants.APP_MAP_BASE_URL)
    }


    fun setAppSettingsResponse(mapSettingsResponse: MapSettingsResponse?) {
        try {
            if (mapSettingsResponse == null) return
            NCMPreferencesHelper.setMapTokenFetchedTime()
            setMapsBaseURL(mapSettingsResponse.result.map.mapsBaseUrl)
            setIconBaseUrl(mapSettingsResponse.result.global.iconsBaseUrl)
            setMapAccessToken(mapSettingsResponse.result.map.mapAccessToken)
            setCurrentPlayStoreVersion(mapSettingsResponse.result.global.androidVersion)
            setDataApiKey(mapSettingsResponse.result.dataEndpoint.reqHeaders.dataApiKey)
            setAppApiURL(mapSettingsResponse.result.dataEndpoint.endpoint)
            setNwpURL(mapSettingsResponse.result.nwpEndpoint.endpoint)
            setMapDefaultSelectedLayer(mapSettingsResponse.result.layers.defaultLayer.ref)
            setMapDefaultSelectedLayerGroup(mapSettingsResponse.result.layers.defaultLayer.group)
            setMapIsMBCLoudLayer(mapSettingsResponse.result.mbCloudLayer.isMbCloudEnabled)
            Log.d("NCMCommonsLogs", Gson().toJson(mapSettingsResponse))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setMapIsMBCLoudLayer(cloud: Int) {
        NCMPreferencesHelper.setPrefs("SET_MB_CLOUD_ENABLED", cloud == 1)
    }

    public fun isMapIsMBCLoudLayerEnabled(): Boolean {
        return NCMPreferencesHelper.getBoolPrefs("SET_MB_CLOUD_ENABLED")
    }

    private fun setMapDefaultSelectedLayer(ref: String) {
        NCMPreferencesHelper.setPrefs("MAP_SELECTED_LAYER_D", ref)
    }

    public fun getMapDefaultSelectedLayer(): String {
        return NCMPreferencesHelper.getStringPrefs("MAP_SELECTED_LAYER_D") ?: ""
    }

    private fun setMapDefaultSelectedLayerGroup(ref: String) {
        NCMPreferencesHelper.setPrefs("MAP_SELECTED_LAYER_GROUP", ref)
    }

    public fun getMapDefaultSelectedLayerGroup(): String {
        return NCMPreferencesHelper.getStringPrefs("MAP_SELECTED_LAYER_GROUP") ?: ""
    }

    private fun setDataApiKey(string: String) {
        NCMPreferencesHelper.setPrefs("DATA_API_KEY", string)
    }

    private fun setNWPApiKey(string: String) {
        NCMPreferencesHelper.setPrefs("NWP_API_KEY", string)
    }

    private fun setNwpURL(string: String) {
        NCMPreferencesHelper.setPrefs("NWP_URL_", string)
    }

    private fun setAppApiURL(string: String) {
        NCMPreferencesHelper.setPrefs("APP_API_URL", string)
    }

    private fun getDataApiKey(): String {
        return NCMPreferencesHelper.getStringPrefs("DATA_API_KEY") ?: ""
    }

    private fun getNwpURL(): String {
        return NCMPreferencesHelper.getStringPrefs("NWP_URL_") ?: ""
    }

    private fun getAppApiURL(): String {
        return NCMPreferencesHelper.getStringPrefs("APP_API_URL") ?: ""
    }

    fun getNwpForecastApiKey(): String {
        return NCMPreferencesHelper.getStringPrefs("NWP_API_KEY") ?: ""
    }

    fun getMobileAppApiKey(): String {
        return MOBILE_APP_KEY_SECRET
    }


    private fun setCurrentPlayStoreVersion(string: String) {
        NCMPreferencesHelper.setPrefs("PLAY_STORE_VERSION", string)
    }

    fun getCurrentPlayStoreVersion(): String {
        return NCMPreferencesHelper.getStringPrefs("PLAY_STORE_VERSION") ?: ""
    }

    fun setIconBaseUrl(iconsBaseUrl: String?) {
        NCMPreferencesHelper.setPrefs(
            NcmConstants.PrefConstants.APP_MAP_ICON_BASE_URL,
            iconsBaseUrl
        )
    }

    fun setMapsBaseURL(string: String?) {
        NCMPreferencesHelper.setPrefs(NcmConstants.PrefConstants.APP_MAP_BASE_URL, string)
    }

    fun setMapAccessToken(string: String?) {
        NCMPreferencesHelper.setPrefs(NcmConstants.PrefConstants.APP_MAP_ACCESS_TOKEN, string)
        if (!string.isNullOrEmpty()) {
            NCMPreferencesHelper.setMapTokenFetchedTime()
        }
    }

    fun getHeaders(): HashMap<String, String> {
        val header = HashMap<String, String>()
        header[API_KEY] = getDataApiKey()
        header[FORECAST_API_KEY] = getNwpForecastApiKey()
        header[AGENT] = userAgentString
        return header
    }

    fun saveMapLayersData(data: MapLayersResponse) {
        try {
            data.mapLayersResult?.let { model ->
                model.nwp?.forEach {
                    it.layerType = NcmConstants.LayerType.NWP
                }
                model.radar?.forEach {
                    it.layerType = NcmConstants.LayerType.RADAR
                }
                model.satellite?.forEach {
                    it.layerType = NcmConstants.LayerType.SATELLITE
                }

            }
            val response = Gson().toJson(data)
            NCMPreferencesHelper.setPrefs(MAP_LAYERS_DATA, response)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun getMapLayersData(): MapLayersResponse? {
        val response: String? = NCMPreferencesHelper.getStringPrefs(MAP_LAYERS_DATA)
        var mData: MapLayersResponse? = null
        try {
            if (response != null) mData = Gson().fromJson(response, MapLayersResponse::class.java)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return mData
    }

    fun round(d: Float, decimalPlace: Int): Float {
        var bd = BigDecimal(d.toString())
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP)
        return bd.toFloat()
    }

    fun getNumberValue(value: Double, fractions: Int = 4): Double {
        var bd = BigDecimal(value)
        bd = bd.setScale(fractions, RoundingMode.HALF_UP)
        return bd.toDouble()
    }

    fun isUAETimeUnit(): Boolean {
        return true
    }

    fun isMapZoom(fragment: Fragment): Boolean {
        return false
    }

    fun getTileURL(
        wmsURL: String,
        layerID: String,
        time: String,
        param: String? = "",
        nwpTime: String = "",
        boolean: Boolean = false
    ): String {
        val finalURL = if (boolean && nwpTime.isNotEmpty()) {
            val bbBoxParam = if (param.isNullOrEmpty()) "" else param
            val timeParam = "&DIM_RUN=$time&DIM_FORECAST=$nwpTime"
            getMapURL() +
                    wmsURL +
                    "?SERVICE=WMS&REQUEST=GetGTile&VERSION=1.3.0&DPI=72&CRS=EPSG:900913&" + bbBoxParam + "&TRANSPARENT=true&TILEZOOM={z}&TILEROW={y}&TILECOL={x}&LAYER=" + layerID + timeParam
        } else {
            val timeParam = "&TIME=$time"

            getMapURL() +
                    wmsURL +
                    "?SERVICE=WMS&REQUEST=GetGTile&VERSION=1.3.0&DPI=72&CRS=EPSG:900913&TRANSPARENT=true&TILEZOOM={z}&TILEROW={y}&TILECOL={x}&LAYER=" + layerID + timeParam

        }
        printLog("MAP_TOKEN", finalURL)
        return finalURL
    }

    private fun hexStringToUIColor(s: String): String {
        return "#$s"
    }

    fun getAWSType(): String {
        return if (NCMPreferencesHelper.getStringPrefs(AWS_MAP_STYLE).isNullOrEmpty())
            AWS_TEMP else
            NCMPreferencesHelper.getStringPrefs(AWS_MAP_STYLE)!!
    }


    fun setAWSMapStyle(string: String) {
        NCMPreferencesHelper.setPrefs(AWS_MAP_STYLE, string)
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

    fun getTemperatureRoundValue(temp: Double): String {
        try {
            val tempValue: Double = if (isCelsiusTemperature()) {
                temp
            } else {
                UnitsUtils.convertCelsiusToFahrenheit(temp)
            }
            return tempValue.setFractions(1)
        } catch (e: Exception) {
            return "-"
            e.printStackTrace()
        }
    }

    fun getWindValue(wind: Double?, noOfFractions: Int): String? {
        if (wind == null) return "0"
        var fractions = 1.0
        for (i in 0 until noOfFractions) {
            fractions *= 10.0
        }
        val result = if (isKMHWind()) {
            wind
        } else (wind / 1.852)
        val finalResult = (result * fractions).roundToInt() / fractions
        return finalResult.toString()
    }

    fun getRoundValue(value: String?): Int {
        return if (value.isNullOrEmpty()) 0
        else {
            try {
                round(value, 0).roundToInt()
            } catch (ex: NumberFormatException) {
                0
            }
        }
    }

    fun round(d: String, decimalPlace: Int): Double {
        var bd = BigDecimal(d)
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP)
        return bd.toDouble()
    }

    fun getColor(value: Double): String? {
        return when {
            getAWSType() == AWS_WIND -> {
                getWindTextColor(value)
            }

            getAWSType() == AWS_TEMP -> {
                getColorForTemp(value.toDouble())
            }

            getAWSType() == AWS_SOLAR -> {
                getColorRadiations(value.toDouble())
            }

            getAWSType() == AWS_AIR_PRESSURE -> {
                getColorForAirPressure(value.toDouble())
            }

            getAWSType() == AWS_HUMIDITY -> {
                getColorForHumidity(value.toDouble())
            }

            getAWSType() == AWS_HUMIDITY -> {
                getColorForHumidity(value.toDouble())
            }

            getAWSType() == AWS_DEW_POINT -> {
                getColorForTemp(value.toDouble())
            }

            getAWSType() == AWS_VISIBILITY -> {
                getColorAWSVisibility(value.toDouble())
            }

            else -> "#FFFFFF"
        }
    }

    private fun getWindTextColor(string: Double): String {
        when (string.toInt()) {
            in getWindValue(0)..getWindValue(10) -> {
                return "#FFFFFF"
            }

            in getWindValue(11)..getWindValue(20) -> {
                return "#FFFFFF"
            }

            in getWindValue(21)..getWindValue(30) -> {
                return "#91F522"
            }

            in getWindValue(31)..getWindValue(40) -> {
                return "#91F522"
            }

            in getWindValue(41)..getWindValue(50) -> {
                return "#FFBA4A"
            }

            in getWindValue(51)..getWindValue(60) -> {
                return "#FFBA4A"
            }

            in getWindValue(61)..getWindValue(70) -> {
                return "#F8E71C"
            }

            in getWindValue(71)..getWindValue(80) -> {
                return "#F8E71C"
            }

            in getWindValue(81)..getWindValue(90) -> {
                return "#6BF8FF"
            }

            else -> {
                return "#6BF8FF"
            }
        }

        return "#6BF8FF"
    }

    private fun getColorAWSVisibility(temp: Double): String {
        return when {
            temp > 2 -> {
                hexStringToUIColor("49FF49")
            }

            temp < 2 && temp > 1 -> {
                hexStringToUIColor("FFF40E")
            }

            else -> {
                hexStringToUIColor("FF5757")
            }
        }
    }


    private fun getColorForTemp(temp: Double): String {
        when {
            temp < getTemperatureRoundValue(-5) -> {
                return hexStringToUIColor("02AAFE")
            }

            temp < getTemperatureRoundValue(0) -> {
                return hexStringToUIColor("8DCBFE")
            }

            temp < getTemperatureRoundValue(5) -> {
                return hexStringToUIColor("9EC6FF")
            }

            temp < getTemperatureRoundValue(12) -> {
                return hexStringToUIColor("80EBFD")
            }

            temp < getTemperatureRoundValue(14) -> {
                return hexStringToUIColor("71EAD7")
            }

            temp < getTemperatureRoundValue(16) -> {
                return hexStringToUIColor("06DD8A")
            }

            temp < getTemperatureRoundValue(18) -> {
                return hexStringToUIColor("02DD00")
            }

            temp < getTemperatureRoundValue(20) -> {
                return hexStringToUIColor("02FE06")
            }

            temp < getTemperatureRoundValue(24) -> {
                return hexStringToUIColor("88FF00")
            }

            temp < getTemperatureRoundValue(28) -> {
                return hexStringToUIColor("CFFE00")
            }

            temp < getTemperatureRoundValue(32) -> {
                return hexStringToUIColor("FDFF00")
            }

            temp < getTemperatureRoundValue(36) -> {
                return hexStringToUIColor("FFE300")
            }

            temp < getTemperatureRoundValue(40) -> {
                return hexStringToUIColor("FECB02")
            }

            temp < getTemperatureRoundValue(44) -> {
                return hexStringToUIColor("FF9C00")
            }

            temp < getTemperatureRoundValue(48) -> {
                return hexStringToUIColor("FF5E01")
            }

            temp < getTemperatureRoundValue(55) -> {
                return hexStringToUIColor("FF4244")
            }

            else -> {
                return hexStringToUIColor("F0EFEF")
            }
        }
    }

    private fun getDewPointColor(temp: Double): String {
        when {
            temp < getTemperatureRoundValue(-5) -> {
                return hexStringToUIColor("8DCBFE")
            }

            temp < getTemperatureRoundValue(0) -> {
                return hexStringToUIColor("02AAFE")
            }

            temp < getTemperatureRoundValue(5) -> {
                return hexStringToUIColor("004CFF")
            }

            temp < getTemperatureRoundValue(12) -> {
                return hexStringToUIColor("0048B9")
            }

            temp < getTemperatureRoundValue(14) -> {
                return hexStringToUIColor("029D61")
            }

            temp < getTemperatureRoundValue(16) -> {
                return hexStringToUIColor("02A700")
            }

            temp < getTemperatureRoundValue(18) -> {
                return hexStringToUIColor("02DD00")
            }

            temp < getTemperatureRoundValue(20) -> {
                return hexStringToUIColor("02FE06")
            }

            temp < getTemperatureRoundValue(24) -> {
                return hexStringToUIColor("88FF00")
            }

            temp < getTemperatureRoundValue(28) -> {
                return hexStringToUIColor("CFFE00")
            }

            temp < getTemperatureRoundValue(32) -> {
                return hexStringToUIColor("FDFF00")
            }

            temp < getTemperatureRoundValue(36) -> {
                return hexStringToUIColor("FFE300")
            }

            temp < getTemperatureRoundValue(40) -> {
                return hexStringToUIColor("FECB02")
            }

            temp < getTemperatureRoundValue(44) -> {
                return hexStringToUIColor("FF9C00")
            }

            temp < getTemperatureRoundValue(48) -> {
                return hexStringToUIColor("FF5E01")
            }

            temp < getTemperatureRoundValue(55) -> {
                return hexStringToUIColor("FF0002")
            }

            else -> {
                return hexStringToUIColor("D70100")
            }

        }
    }


    private fun getColorForHumidity(humidity: Double): String {
        when {
            humidity < 20 -> {
                return hexStringToUIColor("DDFFE3")
            }

            humidity < 30 -> {
                return hexStringToUIColor("008F01")
            }

            humidity < 40 -> {
                return hexStringToUIColor("00B600")
            }

            humidity < 50 -> {
                return hexStringToUIColor("00DD00")
            }

            humidity < 60 -> {
                return hexStringToUIColor("88FE00")
            }

            humidity < 70 -> {
                return hexStringToUIColor("D0FE00")
            }

            humidity < 80 -> {
                return hexStringToUIColor("FCFE00")
            }

            humidity < 90 -> {
                return hexStringToUIColor("FFCB00")
            }

            humidity < 100 -> {
                return hexStringToUIColor("FE5800")
            }

            else -> {
                return hexStringToUIColor("CA0000")
            }
        }
    }

    private fun getColorForAirPressure(pressure: Double): String {
        when {
            pressure < 870 -> {
                return hexStringToUIColor("C7FD2F")
            }

            pressure < 910 -> {
                return hexStringToUIColor("C9D132")
            }

            pressure < 920 -> {
                return hexStringToUIColor("FCA016")
            }

            pressure < 940 -> {
                return hexStringToUIColor("F97A1E")
            }

            pressure < 950 -> {
                return hexStringToUIColor("F28F56")
            }

            pressure < 960 -> {
                return hexStringToUIColor("EFAE73")
            }

            pressure < 970 -> {
                return hexStringToUIColor("E6CCA8")
            }

            pressure < 980 -> {
                return hexStringToUIColor("DFD4C2")
            }

            else -> {
                return hexStringToUIColor("D6E2EB")
            }
        }
    }

    private fun getColorRadiations(radiation: Double): String {
        when {
            radiation < 870 -> {
                return hexStringToUIColor("C7FD2F")
            }

            radiation < 910 -> {
                return hexStringToUIColor("C9D132")
            }

            radiation < 920 -> {
                return hexStringToUIColor("FCA016")
            }

            radiation < 940 -> {
                return hexStringToUIColor("F97A1E")
            }

            radiation < 950 -> {
                return hexStringToUIColor("F28F56")
            }

            radiation < 960 -> {
                return hexStringToUIColor("EFAE73")
            }

            radiation < 970 -> {
                return hexStringToUIColor("E6CCA8")
            }

            radiation < 980 -> {
                return hexStringToUIColor("DFD4C2")
            }

            else -> {
                return hexStringToUIColor("D6E2EB")
            }
        }
    }


    fun getWindValue(wind: Int): Int {
        return if (isKMHWind()) {
            wind
        } else (wind / 1.852).toInt()
    }

    private fun getTemperatureRoundValue(temperature: Int): Int {
        return if (isCelsiusTemperature()) {
            temperature
        } else {
            UnitsUtils.convertCelsiusToFahrenheit(temperature.toDouble()).toInt()
        }
    }


    fun getWarningColor(value: String): String {
        return when (value) {
            "Be aware" -> {
                hexStringToUIColor("fffc00")
            }

            "Be prepared" -> {
                hexStringToUIColor("ff8a00")
            }

            "Take action" -> {
                hexStringToUIColor("ff0000")
            }

            else -> {
                hexStringToUIColor("fffc00")
            }
        }

    }

    fun getCurrentStyleUrl(): String {
        val prefValue = NCMPreferencesHelper.getStringPrefs(MAP_STYLE)
        val style = if (prefValue.isNullOrEmpty()) MAP_BLUE_STYLE else prefValue
        return getMapStaticTokenURL() + style
    }

    private fun getMapStaticTokenURL(): String {
        return getMapBaseURL() + STATIC_MAP_TOKEN
    }


}