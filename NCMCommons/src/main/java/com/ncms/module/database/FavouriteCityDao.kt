package com.ncms.module.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ncms.module.constants.NcmConstants
import com.ncms.module.database.models.FavouriteCityModel

@Dao
interface FavouriteCityDao {

    @Query("SELECT MAX(${DBConfig.CityTable.POSITION}) FROM ${DBConfig.FavouriteCityTable.TABLE_NAME}")
    suspend fun getMaxFavouritePosition(): Int?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteCity(favouriteCity: FavouriteCityModel)

    @Delete
    suspend fun deleteFavouriteCity(favouriteCity: FavouriteCityModel)

    @Query("DELETE FROM ${DBConfig.FavouriteCityTable.TABLE_NAME} WHERE RECORD_ID = :id")
    suspend fun deleteFavouriteCityById(id: Long)

    @Query("SELECT * FROM ${DBConfig.FavouriteCityTable.TABLE_NAME} WHERE RECORD_ID = :recordId LIMIT 1")
    suspend fun findFavouriteCityById(recordId: Long): FavouriteCityModel?

    @Query("SELECT * FROM ${DBConfig.FavouriteCityTable.TABLE_NAME}")
    suspend fun getAllFavouriteCities(): List<FavouriteCityModel>

    @Query("SELECT * FROM ${DBConfig.FavouriteCityTable.TABLE_NAME} " +
            "WHERE (${DBConfig.CityTable.NAME_EN} LIKE :nameLike OR ${DBConfig.CityTable.NAME_AR} LIKE :nameLike) AND " +
            "${DBConfig.CityTable.COUNTRY_CODE} NOT LIKE :stationLike AND " +
            "${DBConfig.CityTable.COUNTRY_CODE} NOT LIKE :aeLike " +
            "ORDER BY ${DBConfig.CityTable.POSITION} ASC")
    suspend fun getFavouriteWorldCities(nameLike: String,
                                        stationLike: String = "'%${NcmConstants.CountryCodes.station.uppercase()}%'",
                                        aeLike: String = "'%${NcmConstants.CountryCodes.uae.uppercase()}%'"
    ): List<FavouriteCityModel>

    @Query("SELECT * FROM ${DBConfig.FavouriteCityTable.TABLE_NAME} " +
            "WHERE (${DBConfig.CityTable.NAME_EN} LIKE :nameLike OR ${DBConfig.CityTable.NAME_AR} LIKE :nameLike) AND " +
            "${DBConfig.CityTable.COUNTRY_CODE} LIKE :aeLike ORDER BY ${DBConfig.CityTable.POSITION} ASC")
    suspend fun getFavouriteUaeCities(nameLike: String,
                                      aeLike: String = "'%${NcmConstants.CountryCodes.uae.uppercase()}%'"
    ): List<FavouriteCityModel>

    @Query("SELECT * FROM ${DBConfig.FavouriteCityTable.TABLE_NAME} " +
            "WHERE (${DBConfig.CityTable.NAME_EN} LIKE :nameLike OR ${DBConfig.CityTable.NAME_AR} LIKE :nameLike) AND " +
            "${DBConfig.CityTable.COUNTRY_CODE} LIKE :stationLike ORDER BY ${DBConfig.CityTable.POSITION} ASC")
    suspend fun getFavouriteStations(nameLike: String,
                                     stationLike: String = "'%${NcmConstants.CountryCodes.station.uppercase()}%'"
    ): List<FavouriteCityModel>
}