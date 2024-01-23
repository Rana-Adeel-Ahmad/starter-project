package com.ncms.module.models.maps.mapservices

import com.ncms.module.models.maps.mapservices.DimensionData

data class LayerItem(
    var name: String = "",
    var title: String = "",
    var dimensions: DimensionData? = DimensionData()
)
