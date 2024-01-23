package com.ncms.module.models.maps.warnings.geo

data class WarningsResponse(
    val result: List<WarningResult>,
    val status: Int
)