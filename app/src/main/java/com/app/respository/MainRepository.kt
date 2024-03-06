package com.app.respository

import com.app.bases.BaseRepository
import com.app.database.AppDao
import com.app.network.ApiClientInterface
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Muzzamil Saleem
 */
@Singleton
class MainRepository @Inject constructor(
    private val apiClientInterface: ApiClientInterface,
    private val appDao: AppDao
) :
    BaseRepository() {

    /*
    suspend fun callLogin(
          userName: String,
          password: String
      ) = safeApiCall {
          apiClientInterface.callLogin(userName, password)
      }
      */


}