package com.ncms.module.models.mapsettings


import com.google.gson.annotations.SerializedName

data class MapSettingsResponse(
    @SerializedName("result")
    var result: Result = Result(),
    @SerializedName("status")
    var status: String = ""
)