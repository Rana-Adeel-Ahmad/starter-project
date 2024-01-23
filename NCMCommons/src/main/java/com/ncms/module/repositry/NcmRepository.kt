package com.ncms.module.repositry

import com.ncms.module.constants.NcmConstants
import com.ncms.module.network.ApiClient
import com.ncms.module.network.OnNCMResponse
import com.ncms.module.utils.NCMUtility
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class NcmRepository : BaseRepository() {

    suspend fun callGetAppSettings() = safeApiCall {
        ApiClient.build().callGetMapSettings(
            NCMUtility.getHeadersAppSettings(),
            NCMUtility.getAppSettingURL()
        )
    }

    suspend fun callGetMapLayers() = safeApiCall {
        val url = NCMUtility.getAPIAppsBaseUrl() + NcmConstants.WebURL.MAP_LAYERS
        ApiClient.build().callGetMapLayers(NCMUtility.getHeaders(), url)
    }

    suspend fun callGetMapServices(url: String) = safeApiCall {
        ApiClient.build().callGetMapRadars(NCMUtility.getHeaders(), url)
    }

    suspend fun callGetAWSObservations() = safeApiCall {
        val url = NCMUtility.getAPIAppsBaseUrl() + NcmConstants.WebURL.AWS_OBSERVATIONS_MAPS
        ApiClient.build().callGetAWSObservationsMaps(NCMUtility.getHeaders(), url)
    }

    suspend fun callGetSatTimeStamps(url: String) = safeApiCall {
        ApiClient.build().callGetSatTimeStamps(NCMUtility.getHeaders(), url)
    }


    suspend fun callGetLatestGeoWarnings() = safeApiCall {
        val url = NCMUtility.getAPIAppsBaseUrl() + NcmConstants.WebURL.WARNINGS_GEO_WARNINGS
        ApiClient.build().callGetMapWarningsGEOWarnings(NCMUtility.getHeaders(), url)
    }

}