package com.ncms.module.database.models


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ncms.module.database.DBConfig

@Entity(DBConfig.FavouriteCityTable.TABLE_NAME)
data class FavouriteCityModel internal constructor(
    @PrimaryKey
    val RECORD_ID: Long,
    val TYPE: String?,
    val GEONAME_ID: String?,
    val AWS_STATION_ID: String?,
    val POI_ID: String?,
    val NAME_EN: String?,
    val NAME_AR: String?,
    val LATITUDE: String?,
    val LONGITUDE: String?,
    val COUNTRY_CODE: String?,
    val POPULATION: String?,
    val TIMEZONE: String?,
    val AIRPORT_NAME_EN: String?,
    val AIRPORT_NAME_AR: String?,
    val AIRPORT_LATITUDE: String?,
    val AIRPORT_LONGITUDE: String?,
    val AIRPORT_IATA: String?,
    val AIRPORT_ICAO: String?,
    val AIRPORT_ACTIVE: String?,
    val GEONAME_CITY_ID: String?,
    val FEATURE_CLASS: String?,
    val FEATURE_CODE: String?,
    val COUNTRY_NAME_EN: String?,
    val COUNTRY_NAME_AR: String?,
    val USE_QUERY_BY: String?,
    val TZ_OFFSET: String?,
    val POSITION: Int?,
){
    fun toCity() : City {
        val city = City(
            NAME_EN ?: "",
            NAME_AR ?: "",
            ""
        )
        city.RECORD_ID = RECORD_ID.toLong()
        city.TYPE = TYPE?.toInt()
        city.GEONAME_ID = GEONAME_ID?.toLong()
        city.AWS_STATION_ID = AWS_STATION_ID?.toLong()
        city.POI_ID = POI_ID?.toLong()
        city.LATITUDE = LATITUDE?.toDoubleOrNull()
        city.LONGITUDE = LONGITUDE?.toDoubleOrNull()
        city.COUNTRY_CODE = COUNTRY_CODE
        city.POPULATION = POPULATION?.toLong()
        city.TIMEZONE = TIMEZONE
        city.AIRPORT_NAME_EN = AIRPORT_NAME_EN
        city.AIRPORT_NAME_AR = AIRPORT_NAME_AR
        city.AIRPORT_LATITUDE = AIRPORT_LATITUDE?.toDoubleOrNull()
        city.AIRPORT_LONGITUDE = AIRPORT_LONGITUDE?.toDoubleOrNull()
        city.AIRPORT_IATA = AIRPORT_IATA
        city.AIRPORT_ICAO = AIRPORT_ICAO
        city.AIRPORT_ACTIVE = AIRPORT_ACTIVE?.toIntOrNull()
        city.COUNTRY_NAME_EN = COUNTRY_NAME_EN
        city.COUNTRY_NAME_AR = COUNTRY_NAME_AR
        city.USE_QUERY_BY = USE_QUERY_BY
        city.TZ_OFFSET = TZ_OFFSET
        city.POSITION = POSITION
        return city
    }

}