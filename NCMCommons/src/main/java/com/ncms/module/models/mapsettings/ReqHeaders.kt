package com.ncms.module.models.mapsettings


import com.google.gson.annotations.SerializedName

data class ReqHeaders(
    @SerializedName("data-api-key")
    var dataApiKey: String = ""
)