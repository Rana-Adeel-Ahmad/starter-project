package com.ncms.module.network


import com.ncms.module.models.maps.layers.MapLayersResponse
import com.ncms.module.models.maps.time.TimeStampResponse
import com.ncms.module.models.maps.warnings.geowarnings.GeoWarningResponse
import com.ncms.module.models.mapsettings.MapSettingsResponse
import com.ncms.module.models.maps.observiatons.ObservationResponse
import okhttp3.ResponseBody
import retrofit2.http.*

interface ServicesApiInterface {
    @GET()
    suspend fun callGetMapSettings(
        @HeaderMap headers: Map<String, String>,
        @Url url: String
    ): MapSettingsResponse

    @GET()
    suspend fun callGetMapLayers(
        @HeaderMap headers: Map<String, String>,
        @Url url: String,
    ): MapLayersResponse


    @GET()
    suspend fun callGetMapRadars(
        @HeaderMap headers: Map<String, String>,
        @Url url: String
    ): ResponseBody

    @GET()
    suspend fun callGetAWSObservationsMaps(
        @HeaderMap headers: Map<String, String>,
        @Url url: String,
    ): ObservationResponse

    @GET()
    suspend fun callGetSatTimeStamps(
        @HeaderMap headers: Map<String, String>,
        @Url url: String
    ): TimeStampResponse


    @GET()
    suspend fun callGetMapWarningsGEOWarnings(
        @HeaderMap headers: Map<String, String>,
        @Url url: String,
    ): GeoWarningResponse

}
