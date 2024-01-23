package com.ncms.module.database.models


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ncms.module.database.DBConfig

@Entity(DBConfig.StationsTable.TABLE_NAME)
data class StationModel internal constructor(
    @PrimaryKey
    val RECORD_ID: Long?,
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
    val IS_FAVOURITE: Int?,
    val EXTRA_TEMP: String?,
    val COUNTRY_NAME_EN: String?,
    val COUNTRY_NAME_AR: String?,
    val USE_QUERY_BY: String?,
    val TZ_OFFSET: String?,
    val POSITION: Int?,
    val ELEVATION: String?,
    val ZOMM: String?,
    val CAMERA: String?,
    val EXTRA_1: String?,
    val EXTRA_2: String?,
    val EXTRA_3: String?
){
    fun toCity() : City {
        val city = City(
            NAME_EN ?: "",
            NAME_AR ?: "",
            ""
        )
        city.RECORD_ID = RECORD_ID?.toLong()
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
    fun toFavModel(): FavouriteCityModel {
        return FavouriteCityModel(
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
            POSITION = this.POSITION
        )
    }
}