package com.app.application

import android.content.Context
import android.content.SharedPreferences
import android.os.StrictMode
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.app.utils.AppExecutor
import com.app.bases.BaseApplication
import com.app.constants.AppConstants
import com.ncms.module.BuildConfig
import com.ncms.module.NcmAppCommons


/**
 * @author Muzzamil Saleem
 */
class ApplicationClass : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        NcmAppCommons.initCommonsSDK(appContext)

        AppExecutor.executorService!!.submit {
            if (BuildConfig.DEBUG) {
                StrictMode.setThreadPolicy(
                    StrictMode.ThreadPolicy.Builder()
                        .detectDiskReads()
                        .detectDiskWrites()
                        .detectNetwork() // or .detectAll() for all detectable problems
                        .penaltyLog()
                        .build()
                )
                StrictMode.setVmPolicy(
                    StrictMode.VmPolicy.Builder()
                        .detectLeakedSqlLiteObjects()
                        .penaltyLog()
                        .penaltyDeath()
                        .build()
                )

            }
        }



    }

    companion object {
        fun getInstance(): Context {
            return appContext
        }

        lateinit var appContext: Context


        var sp: SharedPreferences? = null

        fun getEncryptedSharedPreferences(): SharedPreferences {
            if (sp == null) {
                try {
                    sp = EncryptedSharedPreferences.create(
                        appContext,
                        AppConstants.PrefConstants.APP_PREFS,
                        MasterKey.Builder(appContext).setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                            .build(),
                        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                    )
                } catch (e: Exception) {
                    sp =
                        appContext.getSharedPreferences(
                            AppConstants.PrefConstants.APP_PREFS,
                            MODE_PRIVATE
                        )
                }
            }
            return sp!!
        }
    }


}