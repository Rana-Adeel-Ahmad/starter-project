package com.ncms.module.models.mapsettings


import com.google.gson.annotations.SerializedName

data class MBLayer(
    @SerializedName("is_mb_cloud_enabled")
    var isMbCloudEnabled: Int = 1
)