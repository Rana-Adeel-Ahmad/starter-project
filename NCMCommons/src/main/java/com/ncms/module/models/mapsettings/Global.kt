package com.ncms.module.models.mapsettings


import com.google.gson.annotations.SerializedName

data class Global(
    @SerializedName("android_version")
    var androidVersion: String = "",
    @SerializedName("client_ip")
    var clientIp: String = "",
    @SerializedName("client_ua")
    var clientUa: String = "",
    @SerializedName("icons_base_url")
    var iconsBaseUrl: String = "",
    @SerializedName("ios_version")
    var iosVersion: String = ""
)