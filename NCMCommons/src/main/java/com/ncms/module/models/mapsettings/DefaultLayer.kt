package com.ncms.module.models.mapsettings


import com.google.gson.annotations.SerializedName

data class DefaultLayer(
    @SerializedName("group")
    var group: String = "",
    @SerializedName("ref")
    var ref: String = ""
)