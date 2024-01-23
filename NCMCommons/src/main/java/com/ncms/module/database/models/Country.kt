package com.ncms.module.database.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Country(
    var NAME_EN: String = "",
    var NAME_AR: String = "",
    var COUNTRY_CODE: String = "",
    var FLAG: String = ""
): Parcelable