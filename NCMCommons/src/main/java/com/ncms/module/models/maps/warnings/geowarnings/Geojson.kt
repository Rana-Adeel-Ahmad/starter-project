package com.ncms.module.models.maps.warnings.geowarnings


import com.google.gson.annotations.SerializedName

data class Geojson(
    @SerializedName("features")
    val features: List<Feature>,
    @SerializedName("type")
    val type: String
)