package com.ncms.module.models

import com.google.gson.annotations.SerializedName

open class BaseResponseModel {
    @SerializedName("status")
    var status: Int = 0
}