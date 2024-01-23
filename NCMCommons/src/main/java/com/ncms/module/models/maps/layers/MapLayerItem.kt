package com.ncms.module.models.maps.layers



import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class MapLayerItem(
    @SerializedName("can_merge_with")
    val canMergeWith: List<String>?,
    @SerializedName("default_center")
    val defaultCenter: List<String>?,
    @SerializedName("default_level")
    val defaultLevel: String?,
    @SerializedName("default_zoom")
    val defaultZoom: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("is_wms")
    val isWms: String?,
    @SerializedName("levels")
    val levels: List<String>?,
    @SerializedName("model")
    val model: String?,
    @SerializedName("name_ar")
    val nameAr: String?,
    @SerializedName("name_en")
    val nameEn: String?,
    @SerializedName("params")
    val params: String?,
    @SerializedName("ref")
    val ref: String?,
    @SerializedName("ria_params")
    val riaParams: String?,
    @SerializedName("show_border")
    val showBorder: String?,
    @SerializedName("wms_url")
    val wmsUrl: String?,
    @SerializedName("icon")
    val icon: String?,
    @SerializedName("url_icon")
    val iconURL: String?,
    @SerializedName("frame_interval_mn")
    val interval: Long? = 0,
    @SerializedName("frame_backward_count")
    var backwardFrameCount: Int? = 0,
    @SerializedName("frame_forward_count")
    var forwardFrameCount: Int? = 0,
    @SerializedName("layer_id")
    var layerID: String? = "",
    @SerializedName("tile_ext")
    var tileExt: String? = "",
    @SerializedName("url_legend")
    var urlLegend: String? = "",
    @SerializedName("url_params")
    var urlParams: String? = "",
    var localLayerID: String? = "NCMSMapUtils.ARIES_TILES_ID",
    var isSelected: Boolean = false,
    var layerType: String? = "AppConstants.LayerType.RADAR",
    ) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.createStringArrayList(),
        parcel.createStringArrayList(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeStringList(canMergeWith)
        parcel.writeStringList(defaultCenter)
        parcel.writeString(defaultLevel)
        parcel.writeString(defaultZoom)
        parcel.writeString(id)
        parcel.writeString(isWms)
        parcel.writeStringList(levels)
        parcel.writeString(model)
        parcel.writeString(nameAr)
        parcel.writeString(nameEn)
        parcel.writeString(params)
        parcel.writeString(ref)
        parcel.writeString(riaParams)
        parcel.writeString(showBorder)
        parcel.writeString(wmsUrl)
        parcel.writeString(icon)
        parcel.writeString(iconURL)
        parcel.writeValue(interval)
        parcel.writeValue(backwardFrameCount)
        parcel.writeValue(forwardFrameCount)
        parcel.writeString(layerID)
        parcel.writeString(tileExt)
        parcel.writeString(urlLegend)
        parcel.writeString(urlParams)
        parcel.writeString(localLayerID)
        parcel.writeByte(if (isSelected) 1 else 0)
        parcel.writeString(layerType)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MapLayerItem> {
        override fun createFromParcel(parcel: Parcel): MapLayerItem {
            return MapLayerItem(parcel)
        }

        override fun newArray(size: Int): Array<MapLayerItem?> {
            return arrayOfNulls(size)
        }
    }

}