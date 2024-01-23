package com.ncms.module.models.mapsettings


import com.google.gson.annotations.SerializedName

data class Style(
    @SerializedName("default")
    var default: Boolean? = false,
    @SerializedName("icon")
    var icon: String = "",
    @SerializedName("url")
    var url: String = ""
)