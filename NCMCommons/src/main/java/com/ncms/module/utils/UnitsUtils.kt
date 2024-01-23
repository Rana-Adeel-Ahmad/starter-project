package com.ncms.module.utils

import com.ncms.module.ext.isArabic

import android.content.Context
import com.ncms.module.R
import com.ncms.module.preferences.NCMPreferencesHelper

object UnitsUtils {

    /**
     * @return строковое представление единицы измерения атмосферного давления
     */
    fun getPressureUnits(context: Context): String {
        return context.getString(R.string.hpa)
    }

    /**
     * @return строковое представление единицы измерения температуры
     * в зависимости от предпочтительной системы измерения
     */
    fun getDegreesUnits(context: Context): String {
        val metric: Boolean = isMetricalPreferred(context)
        return if (isArabic()) {
            " ${if (metric) context.getString(R.string.str_c) else context.getString(R.string.str_f)} "
        } else {
            if (metric) context.getString(R.string.str_c) else context.getString(R.string.str_f)
        }
    }

    /**
     * @return является ли метрическая система предпочтительной
     */
    fun isMetricalPreferred(context: Context): Boolean {
        return getPreferredUnits(context).toString().toLowerCase() == "metric"
    }

    fun getPreferredUnits(context: Context): Units {
        var str = NCMPreferencesHelper.getStringPrefs("units")
        str = if (str.isNullOrEmpty()) "0" else str
        return Units.values()[str.toInt()]
    }

    fun celsiusToKelvin(value: Double): Double {
        return value + 273.15
    }

    fun fahrenheitToKelvin(value: Double): Double {
        return (value + 459.67) * 5 / 9
    }

    fun preferredUnitToKelvin(context: Context, value: Double): Double {
        if (isMetricalPreferred(context))
            return celsiusToKelvin(value)
        else
            return fahrenheitToKelvin(value)
    }

    fun kelvinToCelsius(value: Double): Double {
        return value - 273.15
    }

    fun kelvinToFahrenheit(value: Double): Double {
        return value * 9 / 5 - 459.67
    }

    fun kelvinToPreferredUnit(context: Context, value: Double): Double {
        if (isMetricalPreferred(context))
            return kelvinToCelsius(value)
        else
            return kelvinToFahrenheit(value)
    }


    fun convertFahrenheitToCelsius(fahrenheit: Double): Double {
        return (fahrenheit - 32) * 5 / 9
    }

    // Converts to fahrenheit
    fun convertCelsiusToFahrenheit(celsius: Double): Double {
        return celsius * 9 / 5 + 32
    }
}