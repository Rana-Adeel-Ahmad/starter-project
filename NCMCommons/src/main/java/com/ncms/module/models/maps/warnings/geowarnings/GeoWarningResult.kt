package com.ncms.module.models.maps.warnings.geowarnings


import com.google.gson.annotations.SerializedName

data class GeoWarningResult(
    @SerializedName("conditions")
    val conditions: List<String>,
    @SerializedName("geojson")
    val geojson: Geojson,
    @SerializedName("severity")
    val severity: String,
    @SerializedName("severity_text")
    val severityText: String,
    @SerializedName("update_no")
    val updateNo: String,
    @SerializedName("warning_id")
    val warningId: Int,
    @SerializedName("validity_from")
    val validityFrom: String,
    @SerializedName("validity_to")
    val validityTo: String,
    @SerializedName("warning_ar")
    val warningAr: String,
    @SerializedName("warning_en")
    val warningEn: String,
    @SerializedName("published_on")
    val publishedOn: String
)