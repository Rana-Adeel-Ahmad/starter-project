package com.ncms.module.models.mapsettings


import com.google.gson.annotations.SerializedName

data class DataEndpoint(
    @SerializedName("endpoint")
    var endpoint: String = "",
    @SerializedName("show_ts_layer")
    var isTropicalVisible: Int = 0,
    @SerializedName("req_headers")
    var reqHeaders: ReqHeaders = ReqHeaders()
)