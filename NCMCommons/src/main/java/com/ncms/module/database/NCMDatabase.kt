package com.ncms.module.database

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ncms.module.database.DBConfig.DATABASE_VERSION
import com.ncms.module.database.models.City
import com.ncms.module.database.models.CityModel
import com.ncms.module.database.models.FavouriteCityModel
import com.ncms.module.database.models.StationModel

@Database(entities = [CityModel::class, StationModel::class, FavouriteCityModel::class], version = DATABASE_VERSION)
abstract class NCMDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao
    abstract fun favouriteCityDao(): FavouriteCityDao
    companion object {
        @Volatile
        private var INSTANCE: NCMDatabase? = null

        fun getInstance(context: Context): NCMDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                NCMDatabase::class.java, "PF_CITIES1.db3")
//                .createFromAsset("databases/$DATABASE_NAME")
//                .addMigrations(DBConfig.MIGRATION_1_TO_2)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()

        suspend fun copyData(shippedDb: SQLiteDatabase, newDb: NCMDatabase){

            val isRequired = newDb.cityDao().getCities().size <= 1
            if (!isRequired){
                return
            }
            try {
                val mCities = ArrayList<CityModel>()
                val query = "SELECT * FROM ${DBConfig.CityTable.TABLE_NAME}"
                val cursor: Cursor? = shippedDb.rawQuery(query, null)
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        val city = City(cursor)
                        mCities.add(city.toCityModel())
                    }
                    cursor.close()
                }
                newDb.cityDao().saveCities(mCities)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }
}