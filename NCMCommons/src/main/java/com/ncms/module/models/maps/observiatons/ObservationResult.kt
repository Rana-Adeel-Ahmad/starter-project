package com.ncms.module.models.maps.observiatons


import com.google.gson.annotations.SerializedName

data class ObservationResult(
    @SerializedName("camera")
    val camera: String,
    @SerializedName("elevation")
    val elevation: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("lat")
    val lat: String,
    @SerializedName("lng")
    val lng: String,
    @SerializedName("name_ar")
    val nameAr: String,
    @SerializedName("name_en")
    val nameEn: String,
    @SerializedName("observations")
    val observations: List<Observation>,
    @SerializedName("zoom")
    val zoom: String
)