package com.ncms.module.mapview

import android.content.Context
import android.util.AttributeSet
import com.google.gson.Gson
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.layers.FillLayer
import com.mapbox.mapboxsdk.style.layers.PropertyFactory
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import com.ncms.module.ext.isSuccessResponseCode
import com.ncms.module.models.maps.warnings.geowarnings.GeoWarningResponse
import com.ncms.module.models.maps.warnings.geowarnings.GeoWarningResult
import com.ncms.module.models.maps.observiatons.ObservationResponse
import com.ncms.module.network.OnNCMResponse
import com.ncms.module.repositry.NcmRepository
import com.ncms.module.utils.NCMUtility
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class WarningsBaseMapE(context: Context, attrs: AttributeSet?) :
    AriesBaseMapD(context, attrs) {

    protected var latestWarningFeatureCollection: FeatureCollection? = null
    protected val latestWarningList: ArrayList<GeoWarningResult> = ArrayList()
    protected var latestWarningSelectedItem: GeoWarningResult? = null


    fun loadWarnings(layerID: String) {
        this.selectedMapLayerID = layerID
        listener?.onLoadingStart()
        this.isFirstLoad = true
        CoroutineScope(Dispatchers.IO).launch {
            val data = NcmRepository().callGetLatestGeoWarnings()
            CoroutineScope(Dispatchers.Main).launch {
                when (data) {
                    is OnNCMResponse.Success<GeoWarningResponse> -> {
                        listener?.onLoadingStop()
                        val response = data.value
                        handleGeoWarningsData(response)
                    }

                    is OnNCMResponse.Failure -> {
                        listener?.onLoadingStop()
                    }

                    is OnNCMResponse.Loading -> {
                        listener?.onLoadingStart()
                    }
                }
            }
        }
    }

    private fun handleGeoWarningsData(response: GeoWarningResponse) {
        latestWarningList.clear()
        if (response.status.isSuccessResponseCode()) {
            if (response.geoWarningResult.isNullOrEmpty()) {
//                if (this is FullMapBoxFragment) {
//                    PopUpUtils.showMessagePopupWindow(
//                        mainActivity,
//                        getString(R.string.warnings),
//                        getString(R.string.no_active_warnings), mapView
//                    )
//                    onUpdateLatestMapWarnings(latestWarningList)
//                    return
//                }
//                onUpdateLatestMapWarningsCount(0)

                listener?.onMapPlayerCount(latestWarningList.size)
            } else {
                val sortedItems = ArrayList(response.geoWarningResult)
//                onUpdateLatestMapWarningsCount(sortedItems.count())
                listener?.onMapPlayerCount(sortedItems.size)
                sortedItems.sortBy { it.publishedOn }
                val distinctItems = ArrayList(sortedItems.distinctBy { it.severity })
                distinctItems.sortBy { it.severity }
                val groupedItems = ArrayList<GeoWarningResult>()
                for (item in distinctItems) {
                    groupedItems.addAll(sortedItems.filter { it.severity == item.severity })
                }
                latestWarningList.addAll(groupedItems)
//                setRestrictedZoom(true)
            }
        }
        drawLatestWarningPolygonsOnMap(latestWarningList)
//        onUpdateLatestMapWarnings(latestWarningList)
    }

    open fun drawLatestWarningPolygonsOnMap(response: ArrayList<GeoWarningResult>) {
        val featureList: MutableList<Feature> = ArrayList()
        for (geoJsonModel in response) {
            val severityText = geoJsonModel.severityText
            val warningID = geoJsonModel.warningId
            for (index in geoJsonModel.geojson.features.indices) {
                val featureModel = geoJsonModel.geojson.features[index]
                val featureJson = Gson().toJson(featureModel).toString()
                val feature = Feature.fromJson(featureJson)
                feature.addStringProperty(PROPERTY_NAME, severityText)
                feature.addNumberProperty(PROPERTY_WARNING_INDEX, warningID)
                feature.addStringProperty(
                    PROPERTY_COLOR_VALUE,
                    NCMUtility.getWarningColor(severityText)
                )
                featureList.add(feature)
            }
        }
        latestWarningFeatureCollection = FeatureCollection.fromFeatures(featureList)

        mapView.getMapAsync {
            it.getStyle { style ->
                style.apply {
                    addSource(GeoJsonSource(SOURCE_ID, latestWarningFeatureCollection))
                    currentLayer = FillLayer(MARKER_LAYER_ID, SOURCE_ID).withProperties(
                        PropertyFactory.fillColor(getTextColorExpression()),
                        PropertyFactory.fillOpacity(0.70F),
                        PropertyFactory.circleStrokeWidth(1F),
                        PropertyFactory.circleStrokeColor(getTextColorExpression()),
                        PropertyFactory.fillOutlineColor(getTextColorExpression())

                    )
                    currentLayer?.let { layer ->
                        addLayer(layer)
                    }
                    currentStyle = style
                }
            }
        }

        setMapBoxZoom()
    }

}