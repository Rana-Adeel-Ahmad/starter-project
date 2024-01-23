package com.ncms.module.models.mapsettings


import com.google.gson.annotations.SerializedName
import com.ncms.module.models.mapsettings.DefaultLayer

data class Layers(
    @SerializedName("default_layer")
    var defaultLayer: DefaultLayer = DefaultLayer(),
    @SerializedName("ui_model_order")
    var uiModelOrder: List<String> = listOf()
)