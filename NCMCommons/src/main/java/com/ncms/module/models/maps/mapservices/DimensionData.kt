package com.ncms.module.models.maps.mapservices

import com.ncms.module.models.maps.mapservices.DimensionItem
import com.ncms.module.models.maps.mapservices.EXGeographicBoundingBox
import com.ncms.module.models.maps.mapservices.LayerStyle

data class DimensionData (
    var exGeographicBoundingBox: EXGeographicBoundingBox = EXGeographicBoundingBox(),
    var dimensions: ArrayList<DimensionItem> = ArrayList(),
    var style: LayerStyle = LayerStyle()
)
