package com.ncms.module.preferences


import com.ncms.module.constants.NcmConstants.PrefConstants.APP_LANGUAGE
import com.ncms.module.constants.NcmConstants.PrefConstants.APP_THEME
import com.ncms.module.constants.NcmConstants.PrefConstants.APP_THEME_PREFER
import com.ncms.module.constants.NcmConstants.PrefConstants.CALIBRATE_POPUP_SHOWN
import com.ncms.module.constants.NcmConstants.PrefConstants.PREF_BACKGROUND_REFRESH
import com.ncms.module.constants.NcmConstants.PrefConstants.IS_CLOUD_ENABLED
import com.ncms.module.constants.NcmConstants.PrefConstants.IS_ENABLE_FROM_SETTINGS
import com.ncms.module.constants.NcmConstants.PrefConstants.IS_FROM_SETTINGS
import com.ncms.module.constants.NcmConstants.PrefConstants.MAP_LAYERS_DATA
import com.ncms.module.constants.NcmConstants.PrefConstants.SHOW_CURRENT_LOCATION
import android.content.Context
import com.mapbox.mapboxsdk.geometry.LatLng
import com.google.gson.Gson
import com.ncms.module.constants.NcmConstants
import java.util.Date


object NCMPreferencesHelper {

    private const val CURR_COUNTRY_DATA = "CURR_COUNTRY_DATA"
    private const val BULLETINS_DATA = "BULLETINS_DATA"
    private const val MENU_ID = "MENU_ID"
    private const val FROM_MENU = "FROM_MENU"
    private const val MAIN_TAB_ID = "MAIN_TAB_ID"

    fun setPrefs(key: String?, value: String?) {
        PreferenceUtility.setPreference(
            key,
            value
        )
    }

    fun setPrefs(key: String?, value: Int) {
        PreferenceUtility.setIntPreference(

            key,
            value
        )
    }

    fun setPrefs(key: String?, value: Long) {
        PreferenceUtility.setLongPreference(

            key,
            value
        )
    }

    fun setPrefs(key: String?, value: Boolean) {
        PreferenceUtility.setBoolPreference(
            key,
            value
        )
    }

    fun getStringPrefs(key: String): String? {
        return PreferenceUtility.getPreference(

            key
        )
    }

    fun getIntPrefs(key: String?): Int {
        return PreferenceUtility.getIntPreference(
            key
        )
    }

    fun getIntPrefsWithDefault(key: String?, defaultValue: Int): Int {
        return PreferenceUtility.getIntPreferenceWithDefault(

            key, defaultValue
        )
    }

    fun getLongPrefs(key: String?): Long {
        return PreferenceUtility.getLongPreference(

            key
        )
    }

    fun getBoolPrefsWithDefault(key: String?, defaultValue: Boolean): Boolean {
        return PreferenceUtility.getBoolPrefsWithDefault(

            key,
            defaultValue
        )
    }

    fun getBoolPrefs(key: String?): Boolean {
        return PreferenceUtility.getBoolPreference(
            key
        )
    }

    fun getBoolPrefs(key: String?, boolean: Boolean): Boolean {
        return PreferenceUtility.getBoolPreference(

            key, boolean
        )
    }

    fun getBoolPrefsSettings(key: String?): Boolean {
        return PreferenceUtility.getBoolPreferenceSettings(
            key
        )
    }


    fun setAppLanguage(isArabic: Boolean) {
        setPrefs(APP_LANGUAGE, isArabic)
    }

    fun getAppLanguage(): Boolean {
        return getBoolPrefs(APP_LANGUAGE)
    }

    fun setAppFromSettings(boolean: Boolean) {
        setPrefs(IS_FROM_SETTINGS, boolean)
    }

    fun isFromSettings(): Boolean {
        return getBoolPrefs(IS_FROM_SETTINGS, false)
    }

    fun getTheme(): Int {
        return getIntPrefsWithDefault(APP_THEME, NcmConstants.PrefConstants.APP_THEME_DARK)
    }

    fun getCurrentPreferTheme(): Boolean {
        return getBoolPrefs(APP_THEME_PREFER, true)
    }

    fun setCurrentPreferTheme(boolean: Boolean) {
        setPrefs(APP_THEME_PREFER, boolean)
    }

    fun setTheme(int: Int) {
        setPrefs(APP_THEME, int)
    }

    fun isFirstTime(): Boolean {
        return getBoolPrefs(NcmConstants.PrefConstants.APP_FIRST_RUN, true)
    }

    fun isShowTutorial(): Boolean {
        return getBoolPrefs(NcmConstants.PrefConstants.APP_IS_SHOW_TUTORIAL, true)
    }

    fun setAPPFirstRunFalse() {
        setPrefs(NcmConstants.PrefConstants.APP_FIRST_RUN, false)
    }

    fun setShowTutorialFalse() {
        setPrefs(NcmConstants.PrefConstants.APP_IS_SHOW_TUTORIAL, false)
    }

    fun saveDBOldVersion(dbVersion: Int) {
        setPrefs("DBFromAppVersion", dbVersion)
    }

    fun getDBOldVersion(): Int {
        val value = getIntPrefs("DBFromAppVersion")
        return value
    }

    fun saveCurrentLocation(latLng: LatLng) {
        if (latLng != null) {
            val gson = Gson()
            val json = gson.toJson(latLng)
            setPrefs("USER_CURRENT_LOCATION", json)
        }
    }

    fun getCurrentLocation(): LatLng? {
        val gson = Gson()
        val value = getStringPrefs("USER_CURRENT_LOCATION")
        var latLng: LatLng? = null
        if (!value.isNullOrEmpty()) {
            latLng = gson.fromJson(value, LatLng::class.java)
        }
        return latLng
    }

    fun getLoadingCityRecordID(): Long? {
        return getLongPrefs("DEFAULT_RECORD_ID")
    }

    fun setLoadingCityRecordID(id: Long) {
        return setPrefs("DEFAULT_RECORD_ID", id)
    }

    fun isDefaultWorldAdded(): Boolean {
        return getBoolPrefs("WorldDefaultAdded", false)
    }

    fun setDefaultWorldAdded(isAdded: Boolean) {
        return setPrefs("WorldDefaultAdded", isAdded)
    }

    fun isDefaultUAEAdded(): Boolean {
        return getBoolPrefs("UAEDefaultAdded", false)
    }

    fun setDefaultUAEAdded(isAdded: Boolean) {
        return setPrefs("UAEDefaultAdded", isAdded)
    }

    fun isDefaultStationAdded(): Boolean {
        return getBoolPrefs("StationDefaultAdded", false)
    }

    fun setDefaultStationAdded(isAdded: Boolean) {
        return setPrefs("StationDefaultAdded", isAdded)
    }

    fun setAutoDetectLocation(boolean: Boolean) {
        setPrefs(SHOW_CURRENT_LOCATION, boolean)
    }

    fun isAutoDetectLocation(): Boolean {
        return getBoolPrefs(SHOW_CURRENT_LOCATION, true)
    }

    fun setBackgroundRefresh(boolean: Boolean) {
        setPrefs(PREF_BACKGROUND_REFRESH, boolean)
    }

    fun isBackgroundRefresh(): Boolean {
        return getBoolPrefs(PREF_BACKGROUND_REFRESH, true)
    }

    fun setLocationEnableFromSettings(boolean: Boolean) {
        setPrefs(IS_ENABLE_FROM_SETTINGS, boolean)
    }

    fun isLocationEnableFromSettings(): Boolean {
        return getBoolPrefs(IS_ENABLE_FROM_SETTINGS)
    }

    fun setIsCelsiusTemperature(isCelsius: Boolean) {
        setPrefs(NcmConstants.PrefConstants.APP_TEMP_UNIT, isCelsius)
    }

    fun isCelsiusTemperature(): Boolean {
        return getBoolPrefs(NcmConstants.PrefConstants.APP_TEMP_UNIT, true)
    }


    fun setMenuSelectedTabID(id: Int, mainTabID: Int = NcmConstants.MainActivityTabID.ID_WEATHER) {
        setPrefs(FROM_MENU, true)
        setPrefs(MENU_ID, id)
    }

    fun setMainSelectedTabID(mainTabID: Int = NcmConstants.MainActivityTabID.ID_WEATHER) {
        setPrefs(MAIN_TAB_ID, mainTabID)
    }

    fun getMenuSelectedTabID(): Int {
        if (getBoolPrefs(FROM_MENU, false)) {
            return getIntPrefs(MENU_ID)
        }
        return 0
    }

    fun isResumedFromMenu(): Boolean {
        return getBoolPrefs(FROM_MENU, false)
    }

    fun setResumedFromMenu(boolean: Boolean = false) {
        setPrefs(FROM_MENU, boolean)
    }

    fun isCloudEnabled(): Boolean {
        return getBoolPrefs(IS_CLOUD_ENABLED, false)
    }

    fun setCloudEnabled(boolean: Boolean = false) {
        setPrefs(IS_CLOUD_ENABLED, boolean)
    }


    fun getMainSelectedTabID(): Int {
        if (getIntPrefs(MAIN_TAB_ID) == 0)
            return NcmConstants.MenuIDs.MENU_WEATHER_RADARS
        return getIntPrefs(MAIN_TAB_ID)
    }

    fun resetMenuSelection() {
        setPrefs(MENU_ID, 0)
        setPrefs(FROM_MENU, false)
    }


    fun setMapStateFromBackground(b: Boolean = true) {
        setPrefs("MapStateFromBackground", b)
    }

    fun isMapStateFromBackground(): Boolean {
        return getBoolPrefs("MapStateFromBackground", true)
    }

    fun setAppOnPause() {
        val time = Date().time
        setPrefs("PAUSE_TIME", time)
    }

    fun isAppCameFromBackGroundAfter10Minutes(): Boolean {
        val fiveMinutes: Long = 1000 * 60 * 5
        val currentTime = Date().time
        val time = getLongPrefs("PAUSE_TIME")
        val difference = currentTime - time
        return difference > fiveMinutes
    }

    fun isMapTokenNeedToUpdate(): Boolean {
        val maxTime: Long = 1000 * 60 * 5
        val currentTime = Date().time
        val time = getLongPrefs("TOKEN_FETCHED_TIME")
        val difference = currentTime - time

        return difference > maxTime
    }

    fun setCalibratePopupShown(boolean: Boolean) {
        setPrefs(CALIBRATE_POPUP_SHOWN, boolean)
    }

    fun isCalibratePopupShown(): Boolean {
        return getBoolPrefs(CALIBRATE_POPUP_SHOWN, false)
    }

    fun setBackFromUpdateActivity(boolean: Boolean = true) {
        setPrefs("SET_BACK_FROM_UPDATE_ACTIVITY", boolean)
    }

    fun isBackFromUpdateActivity(): Boolean {
        return getBoolPrefs("SET_BACK_FROM_UPDATE_ACTIVITY", false)
    }

    fun setMapTokenFetchedTime() {
        val time = Date().time
        setPrefs("TOKEN_FETCHED_TIME", time)
    }

    fun setAppWindUnit(b: Boolean) {
        return setPrefs(NcmConstants.PrefConstants.APP_WIND_UNIT, b)
    }

    fun isKMHWind(): Boolean {
        return getBoolPrefs(NcmConstants.PrefConstants.APP_WIND_UNIT, true)
    }

    fun isDbCopyRequired(): Boolean {
        return getBoolPrefs("isDbCopyRequired", true)
    }

    fun setDbCopyRequired(isRequired: Boolean) {
        return setPrefs("isDbCopyRequired", isRequired)
    }


}