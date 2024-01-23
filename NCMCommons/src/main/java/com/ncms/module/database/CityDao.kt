package com.ncms.module.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ncms.module.constants.NcmConstants
import com.ncms.module.database.DBConfig.CityTable.ORDER_BY_ARABIC
import com.ncms.module.database.DBConfig.CityTable.ORDER_BY_ENGLISH
import com.ncms.module.database.models.CityModel
import com.ncms.module.database.models.StationModel

@Dao
interface CityDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveCities(cities: List<CityModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCityOrReplace(city: CityModel)

    @Query("DELETE FROM ${DBConfig.CityTable.TABLE_NAME} WHERE RECORD_ID = :id")
    suspend fun deleteCityById(id: Long)

    @Query("SELECT * FROM ${DBConfig.CityTable.TABLE_NAME} WHERE RECORD_ID = :recordId LIMIT 1")
    suspend fun findCityById(recordId: Long): CityModel?

    @Query("SELECT * FROM ${DBConfig.CityTable.TABLE_NAME} WHERE RECORD_ID = :recordId LIMIT 1")
    fun findCityByIdSync(recordId: Long): CityModel?

    @Query("SELECT * FROM ${DBConfig.StationsTable.TABLE_NAME} WHERE ${DBConfig.CityTable.AWS_STATION_ID} = :recordId LIMIT 1")
    suspend fun findStationById(recordId: Long): CityModel?

    @Query("SELECT * FROM ${DBConfig.StationsTable.TABLE_NAME} WHERE ${DBConfig.CityTable.AWS_STATION_ID} = :recordId LIMIT 1")
    fun findStationByIdSync(recordId: Long): CityModel?

    @Query("SELECT DISTINCT * FROM ${DBConfig.CityTable.TABLE_NAME} WHERE  ${DBConfig.CityTable.TYPE} = 1 AND ${DBConfig.CityTable.COUNTRY_CODE} != '${NcmConstants.CountryCodes.uae}' AND FEATURE_CODE='PPLC' $ORDER_BY_ENGLISH LIMIT 100")
    suspend fun getCitiesWorldEn(): List<CityModel>

    @Query("SELECT DISTINCT * FROM ${DBConfig.CityTable.TABLE_NAME} WHERE  ${DBConfig.CityTable.TYPE} = 1 AND ${DBConfig.CityTable.COUNTRY_CODE} != '${NcmConstants.CountryCodes.uae}' AND FEATURE_CODE='PPLC' $ORDER_BY_ARABIC LIMIT 100")
    suspend fun getCitiesWorldAr(): List<CityModel>

    @Query("SELECT DISTINCT * FROM ${DBConfig.CityTable.TABLE_NAME} WHERE  ${DBConfig.CityTable.COUNTRY_CODE} = '${NcmConstants.CountryCodes.uae}' AND ${DBConfig.CityTable.USE_QUERY_BY} = '${NcmConstants.QueryBy.icao}' AND ${DBConfig.CityTable.AWS_STATION_ID} IS NULL $ORDER_BY_ENGLISH LIMIT 100")
    suspend fun getCitiesUaeEn(): List<CityModel>

    @Query("SELECT DISTINCT * FROM ${DBConfig.CityTable.TABLE_NAME} WHERE  ${DBConfig.CityTable.COUNTRY_CODE} = '${NcmConstants.CountryCodes.uae}' AND ${DBConfig.CityTable.USE_QUERY_BY} = '${NcmConstants.QueryBy.icao}' AND ${DBConfig.CityTable.AWS_STATION_ID} IS NULL $ORDER_BY_ARABIC LIMIT 100")
    suspend fun getCitiesUaeAr(): List<CityModel>

    @Query("SELECT DISTINCT * FROM ${DBConfig.CityTable.TABLE_NAME} WHERE ${DBConfig.CityTable.AIRPORT_ICAO} IS NOT NULL AND ${DBConfig.CityTable.AIRPORT_ICAO} != \"\" AND ${DBConfig.CityTable.AWS_STATION_ID} IS NOT NULL $ORDER_BY_ENGLISH LIMIT 100")
    suspend fun getCitiesStationsEn(): List<CityModel>

    @Query("SELECT DISTINCT * FROM ${DBConfig.CityTable.TABLE_NAME} WHERE ${DBConfig.CityTable.AIRPORT_ICAO} IS NOT NULL AND ${DBConfig.CityTable.AIRPORT_ICAO} != \"\" AND ${DBConfig.CityTable.AWS_STATION_ID} IS NOT NULL $ORDER_BY_ARABIC LIMIT 100")
    suspend fun getCitiesStationsAr(): List<CityModel>

    @Query("SELECT DISTINCT * FROM ${DBConfig.CityTable.TABLE_NAME} WHERE ${DBConfig.CityTable.COUNTRY_CODE} != '${NcmConstants.CountryCodes.uae}' AND ${DBConfig.CityTable.NAME_EN} LIKE :nameLike OR ${DBConfig.CityTable.NAME_AR} LIKE :nameLike $ORDER_BY_ENGLISH LIMIT 200")
    suspend fun getSearchWorldEn(nameLike: String): List<CityModel>

    @Query("SELECT DISTINCT * FROM ${DBConfig.CityTable.TABLE_NAME} WHERE ${DBConfig.CityTable.COUNTRY_CODE} != '${NcmConstants.CountryCodes.uae}' AND ${DBConfig.CityTable.NAME_EN} LIKE :nameLike OR ${DBConfig.CityTable.NAME_AR} LIKE :nameLike $ORDER_BY_ARABIC LIMIT 200")
    suspend fun getSearchWorldAr(nameLike: String): List<CityModel>

    @Query("SELECT DISTINCT * FROM ${DBConfig.CityTable.TABLE_NAME} WHERE ${DBConfig.CityTable.COUNTRY_CODE} = '${NcmConstants.CountryCodes.uae}' AND ${DBConfig.CityTable.USE_QUERY_BY} = '${NcmConstants.QueryBy.icao}' AND (${DBConfig.CityTable.NAME_EN} LIKE :nameLike OR ${DBConfig.CityTable.NAME_AR} LIKE :nameLike) AND ${DBConfig.CityTable.AWS_STATION_ID} IS NULL $ORDER_BY_ENGLISH LIMIT 100")
    suspend fun getSearchUaeEn(nameLike: String): List<CityModel>

    @Query("SELECT DISTINCT * FROM ${DBConfig.CityTable.TABLE_NAME} WHERE ${DBConfig.CityTable.COUNTRY_CODE} = '${NcmConstants.CountryCodes.uae}' AND ${DBConfig.CityTable.USE_QUERY_BY} = '${NcmConstants.QueryBy.icao}' AND (${DBConfig.CityTable.NAME_EN} LIKE :nameLike OR ${DBConfig.CityTable.NAME_AR} LIKE :nameLike) AND ${DBConfig.CityTable.AWS_STATION_ID} IS NULL $ORDER_BY_ARABIC LIMIT 100")
    suspend fun getSearchUaeAr(nameLike: String): List<CityModel>

    @Query("SELECT DISTINCT * FROM ${DBConfig.StationsTable.TABLE_NAME} WHERE ${DBConfig.CityTable.AIRPORT_ICAO} IS NOT NULL AND ${DBConfig.CityTable.AIRPORT_ICAO} != \"\" AND (${DBConfig.CityTable.NAME_EN} LIKE :nameLike OR ${DBConfig.CityTable.NAME_AR} LIKE :nameLike) AND ${DBConfig.CityTable.AWS_STATION_ID} IS NOT NULL $ORDER_BY_ENGLISH LIMIT 100")
    suspend fun getSearchStationsEn(nameLike: String): List<CityModel>

    @Query("SELECT DISTINCT * FROM ${DBConfig.StationsTable.TABLE_NAME} WHERE ${DBConfig.CityTable.AIRPORT_ICAO} IS NOT NULL AND ${DBConfig.CityTable.AIRPORT_ICAO} != \"\" AND (${DBConfig.CityTable.NAME_EN} LIKE :nameLike OR ${DBConfig.CityTable.NAME_AR} LIKE :nameLike) AND ${DBConfig.CityTable.AWS_STATION_ID} IS NOT NULL $ORDER_BY_ARABIC LIMIT 100")
    suspend fun getSearchStationsAr(nameLike: String): List<CityModel>

    @Query("SELECT DISTINCT * FROM ${DBConfig.CityTable.TABLE_NAME} WHERE ${DBConfig.CityTable.NAME_EN} = :name")
    suspend fun getCitiesByName(name: String): List<CityModel>

    @Query("SELECT DISTINCT * FROM ${DBConfig.CityTable.TABLE_NAME}")
    suspend fun getCities(): List<CityModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addStation(station: StationModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addStations(station: List<StationModel>)
}