package com.ncms.module.database.helper


import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.ncms.module.database.DBConfig.DATABASE_NAME
import com.ncms.module.database.DBConfig.DATABASE_VERSION
import com.ncms.module.database.DBConfig.DB_PATH_SUFFIX
import com.ncms.module.database.DBConfig.TEMP_DATABASE_NAME
import com.ncms.module.database.DBConfig.TEMP_DATABASE_PATH
import com.ncms.module.preferences.NCMPreferencesHelper
import java.io.*

class DBCopyHelper(var ctx: Context) :
    SQLiteOpenHelper(
        ctx,
        TEMP_DATABASE_NAME,
        null,
        DATABASE_VERSION
    ) {

    @Throws(IOException::class)
    fun copyDataBaseFromAsset() {
        val myInput: InputStream =
            ctx.assets.open("databases/$DATABASE_NAME")
        // Path to the just created empty db
        val outFileName = ctx.applicationInfo.dataDir + TEMP_DATABASE_PATH
        // if the path doesn't exist first, create it
        val f = File(ctx.applicationInfo.dataDir + DB_PATH_SUFFIX)
        if (!f.exists()) f.mkdir()
        // Open the empty db as the output stream
        val myOutput: OutputStream = FileOutputStream(outFileName)
        // transfer bytes from the inputfile to the outputfile
        val buffer = ByteArray(1024)
        var length = 0
        while (myInput.read(buffer).also { length = it } > 0) {
            myOutput.write(buffer, 0, length)
        }
        // Close the streams
        myOutput.flush()
        myOutput.close()
        myInput.close()
    }

    @Throws(SQLException::class)
    fun openDataBase(): SQLiteDatabase {
        val dbFile: File =
            ctx.getDatabasePath(TEMP_DATABASE_NAME)
        val oldDBVersion = NCMPreferencesHelper.getDBOldVersion()
        if (!dbFile.exists() || oldDBVersion != DATABASE_VERSION) {
            try {
                copyDataBaseFromAsset()
                NCMPreferencesHelper.saveDBOldVersion(DATABASE_VERSION)
            } catch (e: IOException) {
                throw RuntimeException("Error creating source database", e)
            }
        }
        val sq = SQLiteDatabase.openDatabase(
            dbFile.getPath(),
            null,
            SQLiteDatabase.NO_LOCALIZED_COLLATORS or SQLiteDatabase.CREATE_IF_NECESSARY
        )
        return sq
    }

    override fun onCreate(db: SQLiteDatabase) {
        // create database query
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
    }


}