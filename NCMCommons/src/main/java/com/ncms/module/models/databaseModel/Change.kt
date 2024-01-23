package com.ncms.module.models.databaseModel

import com.ncms.module.database.models.City

data class Change(
    val action_time: String,
    val action_type: String,
    val airport_active: String,
    val airport_iata: String,
    val airport_icao: String,
    val airport_latitude: String,
    val airport_longitude: String,
    val airport_name_ar: String,
    val airport_name_en: String,
    val aws_station_id: String,
    val country_code: String,
    val country_name_ar: String,
    val country_name_en: String,
    val feature_class: String,
    val feature_code: String,
    val geoname_city_id: String,
    val geoname_id: String,
    val latitude: Double,
    val longitude: Double,
    val name_ar: String,
    val name_en: String,
    val poi_id: String,
    val population: String,
    val record_id: String,
    val timezone: String,
    val type: String,
    val tz_offset: String,
    val use_query_by: String,
    val version_no: Int
){
    fun toCity(): City {
        return City(
            name_en ?: "",
            name_ar ?: "",
            ""
        ).apply {
            RECORD_ID = record_id.toLong()
            TYPE = type?.toInt()
            GEONAME_ID = geoname_id?.toLong()
            AWS_STATION_ID = aws_station_id?.toLong()
            POI_ID = poi_id?.toLong()
            LATITUDE = latitude
            LONGITUDE = longitude
            COUNTRY_CODE = country_code
            POPULATION = population?.toLong()
            TIMEZONE = timezone
            AIRPORT_NAME_EN = airport_name_en
            AIRPORT_NAME_AR = airport_name_ar
            AIRPORT_LATITUDE = airport_latitude?.toDoubleOrNull()
            AIRPORT_LONGITUDE = airport_longitude?.toDoubleOrNull()
            AIRPORT_IATA = airport_iata
            AIRPORT_ICAO = airport_icao
            AIRPORT_ACTIVE = airport_active?.toIntOrNull()
            COUNTRY_NAME_EN = country_name_en
            COUNTRY_NAME_AR = country_name_ar
            USE_QUERY_BY = use_query_by
            TZ_OFFSET = tz_offset
        }
    }
}