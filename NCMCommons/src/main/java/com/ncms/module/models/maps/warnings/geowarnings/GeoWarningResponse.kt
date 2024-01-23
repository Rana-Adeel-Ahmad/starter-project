package com.ncms.module.models.maps.warnings.geowarnings


import com.ncms.module.models.BaseResponseModel
import com.google.gson.annotations.SerializedName

data class GeoWarningResponse(
    @SerializedName("result")
    val geoWarningResult: List<GeoWarningResult>
): BaseResponseModel()