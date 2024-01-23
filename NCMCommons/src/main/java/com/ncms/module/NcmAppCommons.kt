package com.ncms.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.gson.Gson
import com.mapbox.mapboxsdk.Mapbox
import com.ncms.module.constants.NcmConstants
import com.ncms.module.database.NCMDatabase
import com.ncms.module.database.helper.DBCopyHelper
import com.ncms.module.models.mapsettings.MapSettingsResponse
import com.ncms.module.network.OnNCMResponse
import com.ncms.module.repositry.LocalRepository
import com.ncms.module.repositry.NcmRepository
import com.ncms.module.utils.NCMUtility
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

/**
 * @author Muzzamil
 */
object NcmAppCommons {

    var encryptedSharedPreferences: SharedPreferences? = null
    private var localRepo: LocalRepository? = null

    fun initCommonsSDK(appContext: Context) {
        Mapbox.getInstance(appContext, null)
        initMapToken()
        initSharedPreferences(appContext)
        createLocalRepository(appContext)
        CoroutineScope(Dispatchers.IO).launch {
            val shippedDb = DBCopyHelper(appContext).openDataBase()
            val localDb = NCMDatabase.getInstance(appContext)
            NCMDatabase.copyData(shippedDb, localDb)
            localRepo?.addDefaultCity()
        }
    }


    private fun initSharedPreferences(appContext: Context) {
        if (encryptedSharedPreferences == null) {
            try {
                encryptedSharedPreferences = EncryptedSharedPreferences.create(
                    appContext,
                    NcmConstants.PrefConstants.APP_PREFS,
                    MasterKey.Builder(appContext).setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                        .build(),
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                )
            } catch (e: Exception) {
                encryptedSharedPreferences =
                    appContext.getSharedPreferences(
                        NcmConstants.PrefConstants.APP_PREFS,
                        Application.MODE_PRIVATE
                    )
            }
        }
    }

    fun provideLocalRepository(context: Context): LocalRepository {
        synchronized(this) {
            return localRepo ?: createLocalRepository(context)
        }
    }

    private fun createLocalRepository(appContext: Context): LocalRepository {
        val newRepo = LocalRepository(NCMDatabase.getInstance(appContext))
        localRepo = newRepo
        return newRepo
    }

     fun initMapToken() {
        CoroutineScope(Dispatchers.IO).launch {
            val data = NcmRepository().callGetAppSettings()
            CoroutineScope(Dispatchers.Main).launch {
                when (data) {
                    is OnNCMResponse.Success<MapSettingsResponse> -> {
                        val response = data.value
                        Log.d("NCMCommonsLogs", Gson().toJson(response))
                        NCMUtility.setAppSettingsResponse(response)
                    }

                    is OnNCMResponse.Failure -> {
                        Log.d("NCMCommonsLogs", "Fail api...")

                    }

                    is OnNCMResponse.Loading -> {

                    }
                }
            }
        }
    }


}