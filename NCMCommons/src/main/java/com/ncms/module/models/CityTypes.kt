package com.ncms.module.models

import com.ncms.module.constants.NcmConstants


enum class CityTypes(val intValue: Int) {
    WORLD(NcmConstants.MainActivityBottomIDs.ID_WORLD), UAE(NcmConstants.MainActivityBottomIDs.ID_SETTINGS), STATIONS(
        NcmConstants.MainActivityBottomIDs.ID_STATIONS);

    val stringValue: String
        get() = intValue.toString()

}