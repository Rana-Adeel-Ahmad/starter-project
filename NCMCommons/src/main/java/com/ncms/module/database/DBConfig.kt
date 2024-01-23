package com.ncms.module.database

object DBConfig {
    const val TEMP_DATABASE_NAME = "temp_PF_CITIES.db3"
    const val DATABASE_NAME = "PF_CITIES.db3"
    const val DATABASE_VERSION = 6
    const val DB_PATH_SUFFIX = "/databases/"

    val TEMP_DATABASE_PATH: String
        get() = (DB_PATH_SUFFIX
                + TEMP_DATABASE_NAME)

    object CityTable {
        const val TABLE_NAME = "NCM_PF_CITIES"
        const val RECORD_ID = "RECORD_ID"
        const val TYPE = "TYPE"
        const val GEONAME_ID = "GEONAME_ID"
        const val AWS_STATION_ID = "AWS_STATION_ID"
        const val POI_ID = "POI_ID"
        const val NAME_EN = "NAME_EN"
        const val NAME_AR = "NAME_AR"
        const val LATITUDE = "LATITUDE"
        const val LONGITUDE = "LONGITUDE"
        const val COUNTRY_CODE = "COUNTRY_CODE"
        const val POPULATION = "POPULATION"
        const val TIMEZONE = "TIMEZONE"
        const val AIRPORT_NAME_EN = "AIRPORT_NAME_EN"
        const val AIRPORT_NAME_AR = "AIRPORT_NAME_AR"
        const val AIRPORT_LATITUDE = "AIRPORT_LATITUDE"
        const val AIRPORT_LONGITUDE = "AIRPORT_LONGITUDE"
        const val AIRPORT_IATA = "AIRPORT_IATA"
        const val AIRPORT_ICAO = "AIRPORT_ICAO"
        const val AIRPORT_ACTIVE = "AIRPORT_ACTIVE"
        const val IS_FAVOURITE = "IS_FAVOURITE"
        const val EXTRA_TEMP = "EXTRA_TEMP"
        const val POSITION = "POSITION"
        const val COUNTRY_NAME_EN = "COUNTRY_NAME_EN"
        const val COUNTRY_NAME_AR = "COUNTRY_NAME_AR"
        const val USE_QUERY_BY = "USE_QUERY_BY"
        const val TZ_OFFSET = "TZ_OFFSET"
        const val EXTRA_1 = "EXTRA_1"
        const val EXTRA_2 = "EXTRA_2"
        const val EXTRA_3 = "EXTRA_3"

        const val ORDER_BY_ARABIC =
            "ORDER BY $NAME_AR ASC"

        const val ORDER_BY_ENGLISH =
            "ORDER BY $NAME_EN ASC"
    }

    object FavouriteCityTable {
        const val TABLE_NAME = "NCM_PF_CITIES_Favourite"
    }

    object StationsTable {
        const val TABLE_NAME = "NCM_PF_Stations"
        const val ELEVATION = "ELEVATION"
        const val ZOMM = "ZOMM"
        const val CAMERA = "CAMERA"
    }

}