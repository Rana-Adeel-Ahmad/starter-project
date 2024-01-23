package com.ncms.module.models.maps.observiatons


import com.google.gson.annotations.SerializedName

data class ObservationResponse(
    @SerializedName("result")
    val observationResult: List<ObservationResult>,
    @SerializedName("status")
    val status: String
)