package com.ncms.module.models.maps.warnings.geowarnings


import com.google.gson.annotations.SerializedName

data class Properties(
    @SerializedName("type")
    val type: String
)