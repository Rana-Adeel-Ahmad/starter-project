package com.ncms.module.models.maps.ncms_radars


import com.google.gson.annotations.SerializedName

data class LayerX(
    @SerializedName("Name")
    val name: String,
    @SerializedName("queryable")
    val queryable: String,
    @SerializedName("Title")
    val title: String
)