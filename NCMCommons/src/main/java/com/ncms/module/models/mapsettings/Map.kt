package com.ncms.module.models.mapsettings


import com.google.gson.annotations.SerializedName

data class Map(
    @SerializedName("maps_access_token")
    var mapAccessToken: String = "",
    @SerializedName("maps_base_url")
    var mapsBaseUrl: String = "",
    @SerializedName("styles")
    var styles: List<Style> = listOf()
)