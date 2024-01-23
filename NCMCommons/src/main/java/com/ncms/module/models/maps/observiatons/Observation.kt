package com.ncms.module.models.maps.observiatons


import com.google.gson.annotations.SerializedName

data class Observation(
    @SerializedName("humidity") val humidity: String? = null,
    @SerializedName("precip") val precip: String? = null,
    @SerializedName("pressure") val pressure: Double? = null,
    @SerializedName("radiation") val radiation: Double? = null,
    @SerializedName("temp") val temp: Double? = null,
    @SerializedName("time") val time: String = "",
    @SerializedName("wind_compass") val windCompass: String? = null,
    @SerializedName("wind_dir") val windDir: Double? = null,
    @SerializedName("wind_speed") val windSpeed: Double? = null,
    @SerializedName("dewpoint") val dewpoint: Double? = null,
    @SerializedName("visibility") val visibility: Int? = null,
)