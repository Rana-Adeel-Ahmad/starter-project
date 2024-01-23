package com.ncms.module.models.maps.layers



import com.google.gson.annotations.SerializedName


data class MapLayersResponse(
    @SerializedName("result")
    var mapLayersResult: MapLayersResult = MapLayersResult(),
    @SerializedName("status")
    val status: String = "200"
)