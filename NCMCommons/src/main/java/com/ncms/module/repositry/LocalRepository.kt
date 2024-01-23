package com.ncms.module.repositry

import com.ncms.module.constants.NcmConstants
import com.ncms.module.database.NCMDatabase
import com.ncms.module.database.models.City
import com.ncms.module.database.models.CityModel
import com.ncms.module.models.CityTypes
import com.ncms.module.models.databaseModel.Change
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocalRepository (private val db: NCMDatabase){

    suspend fun getCities(name: String, cityType: CityTypes, isArabic: Boolean): ArrayList<City> {
        val cities = ArrayList<City>()
        try {
            val _cities = if (name.isEmpty()) getDefaultSearchQuery(
                name,
                cityType,
                isArabic,
                db
            ) else
                getSearchQuery(name,
                    cityType,
                    isArabic,
                    db)
            cities.addAll(convertToCity(_cities))
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return if (cityType != CityTypes.STATIONS) {
            val filteredList = cities.filter { it.TYPE != 2 }
            ArrayList(filteredList)
        } else {
            cities
        }
    }
    suspend fun isFavouriteCity(recordId: Long): Boolean {
        return db.favouriteCityDao().findFavouriteCityById(recordId) != null
    }

    suspend fun findCityById(recordId: Long, cityType: CityTypes = CityTypes.WORLD): City? {
        return if (cityType == CityTypes.STATIONS) {
            db.cityDao().findStationById(recordId)?.toCity()
        }else{
            db.cityDao().findCityById(recordId)?.toCity()
        }
    }
    fun findCityByIdSync(recordId: Long, cityType: CityTypes = CityTypes.WORLD): City? {
        return if (cityType == CityTypes.STATIONS) {
            db.cityDao().findStationByIdSync(recordId)?.toCity()
        }else{
            db.cityDao().findCityByIdSync(recordId)?.toCity()
        }
    }

    suspend fun updateFavouriteCity(city: City, fav: Boolean) {
        if (fav){
            val max = (db.favouriteCityDao().getMaxFavouritePosition() ?: 0) + 1
            city.POSITION = max
            db.favouriteCityDao().insertFavouriteCity(city.toCityModel().toFavModel())
        }else{
            val id: Long = city.RECORD_ID ?: 0
            db.favouriteCityDao().deleteFavouriteCityById(id)
        }

    }
    suspend fun getFavouriteCities(name: String, cityType: CityTypes): ArrayList<City> {
        val cities = ArrayList<City>()
        val favs =  if (cityType == CityTypes.WORLD) {
            db.favouriteCityDao().getFavouriteWorldCities("%$name%")
        } else if (cityType == CityTypes.UAE) {
            db.favouriteCityDao().getFavouriteUaeCities("%$name%")
        } else if (cityType == CityTypes.STATIONS) {
            db.favouriteCityDao().getFavouriteStations("%$name%")
        }else{
            ArrayList()
        }
        for (aCity in favs){
            val convertedCity = aCity.toCity()
            convertedCity.IS_FAVOURITE = 1
            cities.add(convertedCity)
        }
        return cities
    }

    suspend fun getFavouriteCities(): ArrayList<City> {
        val cities = ArrayList<City>()
        val favs = db.favouriteCityDao().getAllFavouriteCities()
        for (aCity in favs){
            val convertedCity = aCity.toCity()
            convertedCity.IS_FAVOURITE = 1
            cities.add(convertedCity)
        }
        return cities
    }

    private suspend fun getSearchQuery(name: String, cityType: CityTypes, isArabic: Boolean, db: NCMDatabase): List<CityModel> {
        return if (cityType == CityTypes.WORLD) {
            if (isArabic)
                db.cityDao().getSearchWorldAr("%${name}%")
            else
                db.cityDao().getSearchWorldEn("%${name}%")
        } else if (cityType == CityTypes.UAE) {
            if (isArabic)
                db.cityDao().getSearchUaeAr("%${name}%")
            else
                db.cityDao().getSearchUaeEn("%${name}%")
        } else if (cityType == CityTypes.STATIONS) {
            if (isArabic)
                db.cityDao().getSearchStationsAr("%${name}%")
            else
                db.cityDao().getSearchStationsEn("%${name}%")
        }else{
            ArrayList()
        }
    }

    private suspend fun getDefaultSearchQuery(
        name: String,
        cityType: CityTypes,
        isArabic: Boolean,
        db: NCMDatabase
    ): List<CityModel> {
        return if (cityType == CityTypes.WORLD) {
            if (isArabic)
                db.cityDao().getCitiesWorldAr()
            else
                db.cityDao().getCitiesWorldEn()
        } else if (cityType == CityTypes.UAE) {
            if (isArabic)
                db.cityDao().getCitiesUaeAr()
            else
                db.cityDao().getCitiesUaeEn()
        } else if (cityType == CityTypes.STATIONS) {
            if (isArabic)
                db.cityDao().getCitiesStationsAr()
            else
                db.cityDao().getCitiesStationsEn()
        }else{
            ArrayList()
        }
    }

    suspend fun getCity(
        name: String?
    ): ArrayList<City> {
        return convertToCity(db.cityDao().getCitiesByName(name ?: ""))
    }

    private suspend fun convertToCity(
        _cities: List<CityModel>
    ): ArrayList<City> {
        val cities = ArrayList<City>()
        val favouriteCities = getFavouriteCities()
        for (aCity in _cities){
            val mCity = aCity.toCity()
            if (favouriteCities.find { aCity.RECORD_ID == it.RECORD_ID } != null){
                mCity.IS_FAVOURITE = 1
            }else{
                mCity.IS_FAVOURITE = 0
            }
            cities.add(mCity)
        }
        return cities
    }

    private suspend fun deleteCity(id: Long?){
        id?.let {
            db.favouriteCityDao().deleteFavouriteCityById(it)
            db.cityDao().deleteCityById(it)
        }
    }

    fun addUpdateDeleteCities(changes: List<Change>?){
        if (changes.isNullOrEmpty()){
            return
        }
        CoroutineScope(Dispatchers.IO).launch {
            for (aChange in changes){
                addUpdateDeleteCity(aChange)
            }
        }
    }
    private suspend fun addUpdateDeleteCity(change: Change) {
        try {
            val mCity = change.toCity()
            mCity.RECORD_ID?.let {id ->
                when (change.action_type) {
                    NcmConstants.DataBaseActionType.DELETE -> {
                        deleteCity(id)
                    }
                    else -> {
                        val isFavouriteCity = isFavouriteCity(id)
                        db.cityDao().addCityOrReplace(mCity.toCityModel())
                        if (isFavouriteCity){
                            db.favouriteCityDao().insertFavouriteCity(mCity.toCityModel().toFavModel())
                        }

                    }
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()

        }
    }

    fun addStation(city: City) {
        CoroutineScope(Dispatchers.IO).launch{
            db.cityDao().addStation(city.toStation())
        }
    }

    suspend fun addDefaultCity() {
        val city = findCityById(NcmConstants.DEFAULT_CITY_ID, CityTypes.UAE)
        val isFav = isFavouriteCity(NcmConstants.DEFAULT_CITY_ID)
        if (!isFav && city != null){
            updateFavouriteCity(city, true)
        }
    }
}