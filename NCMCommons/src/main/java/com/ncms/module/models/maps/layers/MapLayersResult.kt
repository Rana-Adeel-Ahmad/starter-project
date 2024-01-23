package com.ncms.module.models.maps.layers


import com.google.gson.annotations.SerializedName

data class MapLayersResult(
    @SerializedName("aws")
    var awsObjs:  ArrayList<MapLayerItem>? = ArrayList(),
    @SerializedName("nwp")
    var nwp:  ArrayList<MapLayerItem>? = ArrayList(),
    @SerializedName("radar")
    var radar:  ArrayList<MapLayerItem>? = ArrayList(),
    @SerializedName("satellite")
    var satellite:  ArrayList<MapLayerItem>? = ArrayList(),

    var stations:  ArrayList<MapLayerItem>? = ArrayList()
)