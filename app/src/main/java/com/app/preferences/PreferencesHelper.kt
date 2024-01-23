package com.app.preferences

import android.content.Context
import com.app.application.ApplicationClass
import com.app.constants.AppConstants.PrefConstants.APP_LANGUAGE

/**
 * @author Muzzamil Saleem
 */
object PreferencesHelper {

    private const val APP_PREFS = "APP_PREFS"

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

    fun getBoolPrefs(key: String?): Boolean {
        return PreferenceUtility.getBoolPreference(key)
    }

    fun setAppLanguage(isArabic: Boolean) {
        setPrefs(APP_LANGUAGE, isArabic)
    }

    fun getAppLanguage(): Boolean {
        return getBoolPrefs(APP_LANGUAGE)
    }


}