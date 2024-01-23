package com.ncms.module.database.models


import android.database.Cursor
import androidx.core.database.getDoubleOrNull
import androidx.core.database.getIntOrNull
import androidx.core.database.getLongOrNull
import androidx.core.database.getStringOrNull
import com.ncms.module.constants.NcmConstants
import com.ncms.module.database.DBConfig
import com.ncms.module.models.databaseModel.Change

class City {
    var RECORD_ID: Long? = null
    var TYPE: Int? = null
    var GEONAME_ID: Long? = null
    var AWS_STATION_ID: Long? = null
    var POI_ID: Long? = null
    var NAME_EN: String = ""
    var NAME_AR: String = ""
    var LATITUDE: Double? = null
    var LONGITUDE: Double? = null
    var COUNTRY_CODE: String? = null
    var POPULATION: Long? = null
    var TIMEZONE: String? = null
    var AIRPORT_NAME_EN: String? = null
    var AIRPORT_NAME_AR: String? = null
    var AIRPORT_LATITUDE: Double? = null
    var AIRPORT_LONGITUDE: Double? = null
    var AIRPORT_IATA: String? = null
    var AIRPORT_ICAO: String? = null
    var AIRPORT_ACTIVE: Int? = null
    var TEMP: String? = null
    var IS_FAVOURITE: Int? = null
    var POSITION: Int? = null
    var ELEVATION: String? = null
    var ZOMM: String? = null
    var CAMERA: String? = null
    var COUNTRY_NAME_EN: String? = null
    var COUNTRY_NAME_AR: String? = null
    var USE_QUERY_BY: String? = null
    var TZ_OFFSET: String? = null
    var EXTRA_1: String? = null
    var EXTRA_2: String? = null
    var EXTRA_3: String? = null
    var checked = false
    var isCustomMakeCity = false
    var mainCitySliderPosition: Int? = null


    constructor(nameEn: String, nameAr: String, temp: String) {
        NAME_EN = nameEn
        NAME_AR = nameAr
        TEMP = temp
    }

    constructor (cursor: Cursor) {
        this.RECORD_ID = cursor.getLong(cursor.getColumnIndexOrThrow(DBConfig.CityTable.RECORD_ID))
        this.TYPE = cursor.getInt(cursor.getColumnIndexOrThrow(DBConfig.CityTable.TYPE))
        this.GEONAME_ID = cursor.getLongOrNull(cursor.getColumnIndex(DBConfig.CityTable.GEONAME_ID))
        this.AWS_STATION_ID =
            cursor.getLongOrNull(cursor.getColumnIndex(DBConfig.CityTable.AWS_STATION_ID))
        this.POI_ID = cursor.getLongOrNull(cursor.getColumnIndex(DBConfig.CityTable.POI_ID))
        try {
            this.NAME_EN =
                cursor.getStringOrNull(cursor.getColumnIndex(DBConfig.CityTable.NAME_EN))!!
        } catch (ex: Exception) {
        }
        try {
            this.NAME_AR =
                cursor.getStringOrNull(cursor.getColumnIndex(DBConfig.CityTable.NAME_AR))!!
        } catch (ex: Exception) {
        }
        this.LATITUDE = cursor.getDouble(cursor.getColumnIndexOrThrow(DBConfig.CityTable.LATITUDE))
        this.LONGITUDE = cursor.getDouble(cursor.getColumnIndexOrThrow(DBConfig.CityTable.LONGITUDE))
        this.COUNTRY_CODE =
            cursor.getStringOrNull(cursor.getColumnIndex(DBConfig.CityTable.COUNTRY_CODE))
        this.POPULATION = cursor.getLongOrNull(cursor.getColumnIndex(DBConfig.CityTable.POPULATION))
        this.TIMEZONE = cursor.getStringOrNull(cursor.getColumnIndex(DBConfig.CityTable.TIMEZONE))
        this.AIRPORT_NAME_EN =
            cursor.getStringOrNull(cursor.getColumnIndex(DBConfig.CityTable.AIRPORT_NAME_EN))
        this.AIRPORT_NAME_AR =
            cursor.getStringOrNull(cursor.getColumnIndex(DBConfig.CityTable.AIRPORT_NAME_AR))
        this.AIRPORT_LATITUDE =
            cursor.getDoubleOrNull(cursor.getColumnIndex(DBConfig.CityTable.AIRPORT_LATITUDE))
        this.AIRPORT_LONGITUDE =
            cursor.getDoubleOrNull(cursor.getColumnIndex(DBConfig.CityTable.AIRPORT_LONGITUDE))
        this.AIRPORT_IATA =
            cursor.getStringOrNull(cursor.getColumnIndex(DBConfig.CityTable.AIRPORT_IATA))
        this.AIRPORT_ICAO =
            cursor.getStringOrNull(cursor.getColumnIndex(DBConfig.CityTable.AIRPORT_ICAO))
        this.AIRPORT_ACTIVE =
            cursor.getIntOrNull(cursor.getColumnIndex(DBConfig.CityTable.AIRPORT_ACTIVE))
        this.IS_FAVOURITE =
            cursor.getIntOrNull(cursor.getColumnIndex(DBConfig.CityTable.IS_FAVOURITE))
        this.POSITION =
            cursor.getIntOrNull(cursor.getColumnIndex(DBConfig.CityTable.POSITION))
        this.TEMP =
            cursor.getStringOrNull(cursor.getColumnIndex(DBConfig.CityTable.EXTRA_TEMP))
        this.COUNTRY_NAME_EN =
            cursor.getStringOrNull(cursor.getColumnIndex(DBConfig.CityTable.COUNTRY_NAME_EN))
        this.COUNTRY_NAME_AR =
            cursor.getStringOrNull(cursor.getColumnIndex(DBConfig.CityTable.COUNTRY_NAME_AR))
        this.USE_QUERY_BY =
            cursor.getStringOrNull(cursor.getColumnIndex(DBConfig.CityTable.USE_QUERY_BY))
        this.TZ_OFFSET =
            cursor.getStringOrNull(cursor.getColumnIndex(DBConfig.CityTable.TZ_OFFSET))
        this.EXTRA_1 =
            cursor.getStringOrNull(cursor.getColumnIndex(DBConfig.CityTable.EXTRA_1))
        this.EXTRA_2 =
            cursor.getStringOrNull(cursor.getColumnIndex(DBConfig.CityTable.EXTRA_2))
        this.EXTRA_3 =
            cursor.getStringOrNull(cursor.getColumnIndex(DBConfig.CityTable.EXTRA_3))
        this.CAMERA =
            cursor.getStringOrNull(cursor.getColumnIndex(DBConfig.CityTable.EXTRA_1))
    }

    internal fun toCityModel(): CityModel {
        return CityModel(
            RECORD_ID = this.RECORD_ID?.toLong() ?: 0,
            TYPE = this.TYPE?.toString(),
            GEONAME_ID = this.GEONAME_ID?.toString(),
            AWS_STATION_ID = this.AWS_STATION_ID?.toString(),
            POI_ID = this.POI_ID?.toString(),
            NAME_EN = this.NAME_EN,
            NAME_AR = this.NAME_AR,
            LATITUDE = this.LATITUDE?.toString(),
            LONGITUDE = this.LONGITUDE?.toString(),
            COUNTRY_CODE = this.COUNTRY_CODE,
            POPULATION = this.POPULATION?.toString(),
            TIMEZONE = this.TIMEZONE,
            AIRPORT_NAME_EN = this.AIRPORT_NAME_EN,
            AIRPORT_NAME_AR = this.AIRPORT_NAME_AR,
            AIRPORT_LATITUDE = this.AIRPORT_LATITUDE?.toString(),
            AIRPORT_LONGITUDE = this.AIRPORT_LONGITUDE?.toString(),
            AIRPORT_IATA = this.AIRPORT_IATA,
            AIRPORT_ICAO = this.AIRPORT_ICAO,
            AIRPORT_ACTIVE = this.AIRPORT_ACTIVE?.toString(),
            GEONAME_CITY_ID = null, // Add appropriate conversion logic or set to null
            FEATURE_CLASS = null, // Add appropriate conversion logic or set to null
            FEATURE_CODE = null, // Add appropriate conversion logic or set to null
            COUNTRY_NAME_EN = this.COUNTRY_NAME_EN,
            COUNTRY_NAME_AR = this.COUNTRY_NAME_AR,
            USE_QUERY_BY = this.USE_QUERY_BY,
            TZ_OFFSET = this.TZ_OFFSET,
            POSITION = this.POSITION,
        )
    }

    internal fun toStation(): StationModel {
        return StationModel(
            RECORD_ID = this.AWS_STATION_ID,
            TYPE = this.TYPE?.toString(),
            GEONAME_ID = this.GEONAME_ID?.toString(),
            AWS_STATION_ID = this.AWS_STATION_ID?.toString(),
            POI_ID = this.POI_ID?.toString(),
            NAME_EN = this.NAME_EN,
            NAME_AR = this.NAME_AR,
            LATITUDE = this.LATITUDE?.toString(),
            LONGITUDE = this.LONGITUDE?.toString(),
            COUNTRY_CODE = NcmConstants.CountryCodes.station,
            POPULATION = this.POPULATION?.toString(),
            TIMEZONE = this.TIMEZONE,
            AIRPORT_NAME_EN = this.AIRPORT_NAME_EN,
            AIRPORT_NAME_AR = this.AIRPORT_NAME_AR,
            AIRPORT_LATITUDE = this.AIRPORT_LATITUDE?.toString(),
            AIRPORT_LONGITUDE = this.AIRPORT_LONGITUDE?.toString(),
            AIRPORT_IATA = this.AIRPORT_IATA,
            AIRPORT_ICAO = this.AIRPORT_ICAO,
            AIRPORT_ACTIVE = this.AIRPORT_ACTIVE?.toString(),
            IS_FAVOURITE = this.IS_FAVOURITE,
            EXTRA_TEMP = this.TEMP,
            COUNTRY_NAME_EN = this.COUNTRY_NAME_EN,
            COUNTRY_NAME_AR = this.COUNTRY_NAME_AR,
            USE_QUERY_BY = this.USE_QUERY_BY,
            TZ_OFFSET = this.TZ_OFFSET,
            POSITION = this.POSITION,
            ELEVATION = this.ELEVATION,
            ZOMM = this.ZOMM,
            CAMERA = this.CAMERA,
            EXTRA_1 = this.EXTRA_1,
            EXTRA_2 = this.EXTRA_2,
            EXTRA_3 = this.EXTRA_3
        )
    }

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is City -> {
                other.RECORD_ID == RECORD_ID
            }
            is CityModel -> {
                other.RECORD_ID == RECORD_ID
            }
            is StationModel -> {
                other.RECORD_ID == RECORD_ID
            }
            is FavouriteCityModel -> {
                other.RECORD_ID == RECORD_ID
            }
            is Change -> {
                other.record_id == RECORD_ID.toString()
            }
            else -> {
                false
            }
        }
    }

}