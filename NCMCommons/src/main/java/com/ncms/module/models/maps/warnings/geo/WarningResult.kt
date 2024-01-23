package com.ncms.module.models.maps.warnings.geo

data class WarningResult(
    val end_time: String,
    val geo_data: List<GeoData>,
    val severity: Int,
    val severity_text: String,
    val start_time: String,
    val warning_ar: String,
    val warning_codes: List<String>,
    val warning_en: String
)