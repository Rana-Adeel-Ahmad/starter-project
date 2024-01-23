package com.ncms.module.preferences

import android.content.SharedPreferences
import com.ncms.module.NcmAppCommons

/**
 * @author Muzzamil
 */
object PreferenceUtility {
    /*
     * Function for getting a value from shared preferences
     */

    fun getEncryptedSharedPreferences(): SharedPreferences {
        return NcmAppCommons.encryptedSharedPreferences!!
    }

    fun getPreference(
        key: String?
    ): String? {
        val usePref = getEncryptedSharedPreferences()
        return usePref.getString(key, "")
    }

    /*
     * Function for setting values for shared preferences
     */
    fun setPreference(

        key: String?,
        value: String?
    ) {
        val userPref = getEncryptedSharedPreferences()
        val editor = userPref.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun setBoolPreference(
        key: String?,
        value: Boolean
    ) {
        val userPref = getEncryptedSharedPreferences()
        val editor = userPref.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBoolPreference(

        key: String?
    ): Boolean {
        val usePref = getEncryptedSharedPreferences()
        return usePref.getBoolean(key, false)
    }

    fun getBoolPreference(

        key: String?, boolean: Boolean
    ): Boolean {
        val usePref = getEncryptedSharedPreferences()
        return usePref.getBoolean(key, boolean)
    }

    fun getBoolPrefsWithDefault(

        key: String?,
        defaultValue: Boolean
    ): Boolean {
        val usePref = getEncryptedSharedPreferences()
        return usePref.getBoolean(key, defaultValue)
    }

    fun getBoolPreferenceSettings(
        key: String?
    ): Boolean {
        val usePref = getEncryptedSharedPreferences()
        return usePref.getBoolean(key, true)
    }

    fun setIntPreference(
        key: String?,
        value: Int
    ) {
        val userPref = getEncryptedSharedPreferences()
        val editor = userPref.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun getIntPreference(
        key: String?
    ): Int {
        val usePref = getEncryptedSharedPreferences()
        return usePref.getInt(key, 0)
    }

    fun getIntPreferenceWithDefault(
        key: String?,
        defaultValue: Int
    ): Int {
        val usePref = getEncryptedSharedPreferences()
        return usePref.getInt(key, defaultValue)
    }

    fun setLongPreference(
        key: String?,
        value: Long
    ) {
        val userPref = getEncryptedSharedPreferences()
        val editor = userPref.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    fun getLongPreference(

        key: String?
    ): Long {
        val usePref = getEncryptedSharedPreferences()
        return usePref.getLong(key, 0)
    }

    fun removeAllPreferences() {
        val userPref = getEncryptedSharedPreferences()
        val editor = userPref.edit()
        editor.clear()
        editor.apply()
    }
}