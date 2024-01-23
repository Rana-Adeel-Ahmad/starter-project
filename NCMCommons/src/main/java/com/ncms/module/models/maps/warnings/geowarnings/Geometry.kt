package com.ncms.module.models.maps.warnings.geowarnings


import com.google.gson.annotations.SerializedName

data class Geometry(
    @SerializedName("coordinates")
    val coordinates: List<List<List<Any>>>,
    @SerializedName("type")
    val type: String
)