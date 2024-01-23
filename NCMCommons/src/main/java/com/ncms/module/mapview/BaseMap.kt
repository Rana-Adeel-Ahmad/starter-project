package com.ncms.module.mapview

import android.content.Context
import android.os.CountDownTimer
import android.util.AttributeSet
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.layers.Layer
import com.ncms.module.callbacks.NCMMapListener
import com.ncms.module.models.maps.layers.MapLayerItem
import com.ncms.module.models.maps.mapservices.LayerItem
import com.ncms.module.utils.NCMUtility
import com.ncms.module.utils.NCMUtility.getCurrentStyleUrl

open class BaseMap(context: Context, attrs: AttributeSet?) : MapView(context, attrs) {

    val MAPS_LOGS = "MAPS_LOGS"
    val MAPS_LOGS_2 = "MAPS_LOGS_2"
    val CALLOUT_LAYER_ID = "CALLOUT_LAYER_ID"
    val SOURCE_ID = "SOURCE_ID"
    val SOURCE_ID_1H = "SOURCE_ID_1H"
    val MARKER_IMAGE_ID = "MARKER_IMAGE_ID"
    val MARKER_LAYER_ID = "MARKER_LAYER_ID"
    val CLOUD_1H_LAYER_ID = "CLOUD_1H_LAYER_ID"
    val PROPERTY_SELECTED = "selected"
    val PROPERTY_SINGLE_MARKER = "PROPERTY_SINGLE_MARKER"
    val PROPERTY_NAME = "name"
    val PROPERTY_VALUE = "value"
    val PROPERTY_ICON_VALUE = "icon-rotate"
    val PROPERTY_ICON_ID = "icon-rotate"
    val PROPERTY_COLOR_VALUE = "color_value"
    val PROPERTY_WARNING_INDEX = "PROPERTY_WARNING_INDEX"
    val PROPERTY_DEPHT = "PROPERTY_DEPHT"
    val PROPERTY_MAGNITUDE = "PROPERTY_MAGNITUDE"
    val RC_PERMISSION = 1221


    protected var mapboxMap: MapboxMap? = null

    protected val mapTilt: Double = 0.0
    protected val mapBearing: Double = 0.0
    protected var restrictedMapZoom = 0.0


    protected var activity: AppCompatActivity? = null
    protected var listener: NCMMapListener? = null

    protected var countDownTimer: CountDownTimer? = null

    protected val mapView: MapView get() = this

    protected var currentLayer: Layer? = null
    protected var currentStyle: Style? = null

    protected var isFirstLoad = false
    protected var mapSelectedCurrentIndex = 0
    protected var mapInitialIndex = 0
    protected var itemsToShowForPlayer = 12
    protected var totalTilesCount = 0
    protected var currentLayerOpacity: Float = 1.0f
    protected var currentLayerId = ""

    protected var mapTimerStartIndex = 0
    protected var mapTimerEndIndex = 0

    protected var selectedMapLayerItem: MapLayerItem? = null
    protected var selectedMapTileItem: LayerItem? = null
    protected var selectedMapLayerID: String? = ""

    fun attachActivity(mainActivity: AppCompatActivity, listener: NCMMapListener?) {
        this.activity = mainActivity
        this.listener = listener
    }

    fun setMapBoxZoom(
        lat: Double = NCMUtility.MAPS_DEFAULT_LAT,
        lng: Double = NCMUtility.MAPS_DEFAULT_LNG,
        zoom: Double = NCMUtility.MAPS_DEFAULT_ZOOM,
        isAnimated: Boolean = true
    ) {
        val duration = if (isAnimated) 2000 else 100
        val position: CameraPosition = CameraPosition.Builder()
            .target(LatLng(lat, lng)) // Sets the new camera position
            .zoom(zoom) // Sets the zoom
            .tilt(mapTilt) // Set the camera tilt
            .bearing(mapBearing)
            .build() // Creates a CameraPosition from the builder

        mapboxMap?.animateCamera(
            CameraUpdateFactory
                .newCameraPosition(position), duration
        )
        mapboxMap?.setMinZoomPreference(restrictedMapZoom)

    }


    fun loadStyle() {
        val styleUrl = getCurrentStyleUrl()
        mapView.getMapAsync { mapboxMap ->
            mapboxMap.setStyle(Style.Builder().fromUri(styleUrl)) {
                it.apply {
                    currentStyle = it
                }
            }
        }
    }


    fun initializeMapsWithSettings() {
        val styleUrl = getCurrentStyleUrl()
        mapView.getMapAsync { mapboxMap ->
            mapboxMap.setStyle(Style.Builder().fromUri(styleUrl)) {
                it.apply {
                    currentStyle = it
                }
            }
        }
    }

}