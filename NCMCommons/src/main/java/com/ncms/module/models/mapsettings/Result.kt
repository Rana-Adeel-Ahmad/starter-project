package com.ncms.module.models.mapsettings


import com.google.gson.annotations.SerializedName
import com.ncms.module.models.mapsettings.DataEndpoint
import com.ncms.module.models.mapsettings.Global
import com.ncms.module.models.mapsettings.Layers
import com.ncms.module.models.mapsettings.MBLayer
import com.ncms.module.models.mapsettings.Map

data class Result(
    @SerializedName("data_endpoint")
    var dataEndpoint: DataEndpoint = DataEndpoint(),
    @SerializedName("global")
    var global: Global = Global(),
    @SerializedName("icons_base_url")
    var iconsBaseUrl: String = "",
    @SerializedName("layers")
    var layers: Layers = Layers(),
    @SerializedName("maps")
    var map: Map = Map(),
    @SerializedName("nwp_forecast")
    var nwpEndpoint: DataEndpoint = DataEndpoint(),
    @SerializedName("map_aeris_layers")
    var ariesEndpoint: DataEndpoint = DataEndpoint(),
    @SerializedName("time")
    var time: String = "",
    @SerializedName("version")
    var version: String = "",
    @SerializedName("cloud_layer")
    var mbCloudLayer: MBLayer = MBLayer(),
)