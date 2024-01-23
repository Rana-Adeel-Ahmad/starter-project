package com.ncms.module.mapview

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.layers.LineLayer
import com.mapbox.mapboxsdk.style.layers.Property
import com.mapbox.mapboxsdk.style.layers.PropertyFactory
import com.mapbox.mapboxsdk.style.layers.RasterLayer
import com.mapbox.mapboxsdk.style.layers.SymbolLayer
import com.mapbox.mapboxsdk.style.sources.RasterSource
import com.mapbox.mapboxsdk.style.sources.TileSet
import com.ncms.module.constants.NcmConstants
import com.ncms.module.ext.isArabic
import com.ncms.module.models.maps.layers.MapLayerItem
import com.ncms.module.models.maps.mapservices.DimensionItem
import com.ncms.module.models.maps.mapservices.LayerItem
import com.ncms.module.network.OnNCMResponse
import com.ncms.module.repositry.NcmRepository
import com.ncms.module.utils.CommonUtils
import com.ncms.module.utils.NCMUtility
import com.ncms.module.utils.NcmXmlParser
import com.ncms.module.utils.datetimeutils.DateTimeUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

open class WMSBaseMapA(context: Context, attrs: AttributeSet?) : BaseMap(context, attrs) {


    protected var normalTilesTimeList: ArrayList<String> = ArrayList()
    protected var nwpTilesTimeList: ArrayList<String> = ArrayList()
    protected var observationTileList: ArrayList<LayerItem> = ArrayList()

    fun loadWmsTilesOnMap(layerItem: MapLayerItem) {
        listener?.onLoadingStart()
        setMapBoxZoom()
        this.isFirstLoad = true
        this.selectedMapLayerItem = layerItem
        this.selectedMapLayerID = layerItem.layerID
        val service = "WMS"
        val request = "GetCapabilities"
        val timeStamp = CommonUtils.timestamp2
        layerItem.model?.let {
            var url =
                NCMUtility.getMapURL() + "/wms/$it?SERVICE=$service&REQUEST=$request&TIMESTAMP=$timeStamp"
            CoroutineScope(Dispatchers.IO).launch {
                val data = NcmRepository().callGetMapServices(url)
                CoroutineScope(Dispatchers.Main).launch {
                    when (data) {
                        is OnNCMResponse.Success<ResponseBody> -> {
                            listener?.onLoadingStop()
                            val response = data.value
                            onTileResponse(response)
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
    }

    private fun onTileResponse(data: ResponseBody) {
        try {
            val xmlString = data.string()
            val list = NcmXmlParser.parseMapTilesResponse(xmlString)
            if (list.isEmpty()) {
                listener?.onMapError(null)
            } else {
                observationTileList.clear()
                observationTileList.addAll(list)
                findLayerAndPopulateDataOnMap()
            }


//            selectedMapTileItem = list.find { layerItem ->
//                layerItem.name.equals(
//                    selectedMapLayerID,
//                    ignoreCase = true
//                )
//            }
//            if (selectedMapTileItem == null) {
//                mapsViewModel.mapTilesList.postValue(list)
//            } else {
//
//                if (this is FullMapBoxFragment) {
//                    if (isRefreshFromBackground) {
//                        setMapTimeOnRefresh(selectedMapLayerItem!!)
//                    } else
//                        loadTilesOnMaps(selectedMapLayerItem!!)
//                } else {
//                    mapsViewModel.mapTilesList.postValue(list)
//                }
//            }
        } catch (e: Exception) {
            e.printStackTrace()
//            PopUpUtils.showMessage(mView, getString(R.string.some_thing_went_wrong))
//            printMapLOG("Got Exception on tile response")


        }
//        if (PreferencesHelper.isCloudEnabled() && !isNWP) mapsViewModel.callGetRadar1hCloudData()

    }

    private fun findLayerAndPopulateDataOnMap() {
        if (observationTileList.isNotEmpty()) {
            selectedMapLayerItem?.id?.let { layerID ->
                selectedMapTileItem =
                    observationTileList.find { it.name.equals(layerID, ignoreCase = true) }
                selectedMapTileItem?.let {
                    loadWmsTilesOnMap()
                }
            }

        }
    }


    protected fun loadWmsTilesOnMap(
        isPlayerActive: Boolean = false,
        tileIndex: Int = 0
    ) {
        printMapLOG("loadTilesOnMaps from NCM Module...")
        setMapBoxZoom()
        var timeStamp = ""
        var timeToShow = ""
        var nwpTimeStamp = ""
        var timeDimensionItem: DimensionItem? = null
        var foreDimensionItem: DimensionItem? = null
        normalTilesTimeList.clear()
        nwpTilesTimeList.clear()
        selectedMapLayerItem?.apply {
            try {
                if (selectedMapTileItem != null) {
                    if (selectedMapTileItem?.dimensions == null) {
                        listener?.onMapError(null)
                        printMapLOG("Error on loadTilesOnMaps 1")
                        return
                    } else {
                        val dimensions = selectedMapTileItem!!.dimensions?.dimensions
                        var defaultTime = ""
                        var defaultNwpTime = ""
                        if (dimensions != null && dimensions.size > 1) {
                            timeDimensionItem =
                                dimensions.find { it.name.equals("RUN", ignoreCase = true) }
                            foreDimensionItem =
                                dimensions.find { it.name.equals("FORECAST", ignoreCase = true) }

                            defaultNwpTime = foreDimensionItem!!.default

                            defaultTime = timeDimensionItem!!.default

                            nwpTilesTimeList.addAll(foreDimensionItem!!.content)
                            for (foreCast in nwpTilesTimeList) {
                                val dateTime =
                                    DateTimeUtils.getForeCastDateTime(defaultTime, foreCast)
                                if (dateTime.isNotEmpty()) normalTilesTimeList.add(dateTime)
                            }
                        } else {
                            if (!dimensions.isNullOrEmpty()) {
                                timeDimensionItem = dimensions[0]
                                normalTilesTimeList.addAll(timeDimensionItem!!.content)
                                defaultTime = timeDimensionItem!!.default
                            }
                        }



                        if (isLayerIsNWP()) {
                            if (tileIndex == 0 && !isPlayerActive) {
                                mapSelectedCurrentIndex = DateTimeUtils.getCurrentActualIndex(
                                    isLayerIsNWP(),
                                    normalTilesTimeList
                                )
                                mapInitialIndex = mapSelectedCurrentIndex
                                loadWmsTilesOnMap(true, mapSelectedCurrentIndex)
                                return
                            } else {
                                mapSelectedCurrentIndex = tileIndex
                            }
                        } else {
                            mapSelectedCurrentIndex = if (tileIndex == 0 && !isPlayerActive) {
                                (normalTilesTimeList.size - 1) - tileIndex
                            } else {
                                tileIndex
                            }
                        }
                        totalTilesCount = normalTilesTimeList.size
                        if (isPlayerActive) {
                            timeToShow = normalTilesTimeList[mapSelectedCurrentIndex]
                            timeStamp =
                                if (isLayerIsNWP()) defaultTime else normalTilesTimeList[mapSelectedCurrentIndex]
                            nwpTimeStamp =
                                if (nwpTilesTimeList.isNullOrEmpty()) "" else nwpTilesTimeList[mapSelectedCurrentIndex]
                        } else {
                            timeStamp = defaultTime
                            timeToShow = defaultTime
                            nwpTimeStamp = defaultNwpTime
                        }
                    }
                }

                if (isLayerIsNWP() && nwpTimeStamp.isNullOrEmpty()) {
                    listener?.onMapError(null)
                    printMapLOG("Error on loadTilesOnMaps 2")
                    return

                }

                val wmsURL = wmsUrl?.ifEmpty { "" }
                val bbParam = if (params.isNullOrEmpty()) "" else params
                val layerID = if (id.isNullOrEmpty()) "" else id
                if (layerID.isNullOrEmpty()) {
                    listener?.onMapError(null)
                    return
                }
                val tileURL = NCMUtility.getTileURL(
                    wmsURL!!,
                    layerID,
                    timeStamp,
                    bbParam,
                    nwpTimeStamp,
                    isLayerIsNWP()
                )

                val customLayerID = "map-tile-normal-$mapSelectedCurrentIndex"

                mapView.getMapAsync { mapboxMap ->
                    mapboxMap.getStyle { mainStyle ->
                        currentStyle = mainStyle
                        removeSource(mainStyle)
                        mainStyle.apply {
                            if (this.isFullyLoaded) {
                                val currentLoadLayer = getRasterLayer(mainStyle, customLayerID)
                                if (currentLoadLayer == null) {
                                    printMapLOG("loadTilesOnMaps : current layer null")

                                    val source = RasterSource(
                                        customLayerID,
                                        TileSet("tileset", tileURL),
                                        256
                                    )
                                    addSource(source)
                                    val rasterLayer = RasterLayer(
                                        customLayerID,
                                        customLayerID
                                    )
                                    rasterLayer.setProperties(
                                        PropertyFactory.rasterOpacity(currentLayerOpacity),
                                        PropertyFactory.fillOpacity(currentLayerOpacity)
                                    )
                                    addLayer(rasterLayer)
                                    currentLayer = rasterLayer
                                } else {
                                    currentLoadLayer.let { el ->
                                        printMapLOG("loadTilesOnMaps : current layer id :" + el.id)

                                        el.setProperties(
                                            PropertyFactory.rasterOpacity(currentLayerOpacity)
                                        )
                                    }
                                    currentLayer = currentLoadLayer
                                }
                                Handler(Looper.getMainLooper()).postDelayed({
                                    val existingLayer = getRasterLayer(mainStyle, currentLayerId)
                                    existingLayer?.let { el ->
                                        if (currentLayer?.id == el.id) {

                                        } else {
                                            printMapLOG("loadTilesOnMaps : existing layer id :" + el.id)
                                            el.setProperties(
                                                PropertyFactory.rasterOpacity(0.0f)
                                            )
                                        }
                                    }
                                    currentLayerId = customLayerID
                                    showBorder?.let {
                                        val isShowBorder = (it == "1")
                                        addBordersOnLayers(this, isShowBorder, true)
                                    }

                                }, 100)


                            }


                        }
                    }
                }

                if (timeToShow.isNotEmpty()) {
                    val timeVal = DateTimeUtils.convertMapPlayDateTime(
                        timeToShow.replace("Z", "", true),
                        NcmConstants.DATE_TIME_FORMAT_MAP,
                        NcmConstants.TIME_FORMAT_24
                    )

                    val dateVal = DateTimeUtils.convertMapPlayDateTime(
                        timeToShow.replace("Z", "", true),
                        NcmConstants.DATE_TIME_FORMAT_MAP,
                        getMapTimerDateFormat()
                    )
                    listener?.onMapDateTimeUpdate(dateVal, timeVal)
                }

//                if (mainActivity is MapsActivity) {
//                    if (isRadars() && PreferencesHelper.isCloudEnabled()) {
//                        if (mapSelectedCurrentIndex < 2) {
//                            callGetCloud1hRadarReport()
//                        }
//                    }
//                }

//
//                if (defaultCenter!!.isEmpty()) {
//                    setMapBoxZoom()
//                } else {
//                    if (!(isPlay && initZoom)) {
//                        if (this@MapBaseFragment is WeatherSliderMapsFragment
//                            || this@MapBaseFragment is SeaSliderMapsFragment
//                        ) {
//                            setMapBoxZoom(isAnimated = false)
//                        } else {
//                            defaultCenter?.let {
//                                val lat = it[1].toDouble()
//                                val lng = it[0].toDouble()
//                                var zoom = 0.0
//
//                                defaultZoom?.let {
//                                    zoom = it.toDouble()
//                                } ?: run {
//                                    zoom = NCMUtility.MAPS_DEFAULT_ZOOM
//                                }
//                                setMapBoxZoom(lat, lng, zoom, true)
//                            }
//
//
//                        }
//                    }
//                }
//
                if (isFirstLoad) {
                    listener?.onMapPlayerCount(totalTilesCount)
                    isFirstLoad = false
                }
                listener?.onMapIndexChange(mapSelectedCurrentIndex)
            } catch (e: Exception) {
                e.printStackTrace()
                listener?.onMapError(e.toString())
                printMapLOG("Exception on loadTilesOnMaps 3")

            }
        }
    }

    protected fun printMapLOG(string: String) {
            Log.d("NcmMapView", string)

    }

    protected fun isLayerIsNWP(): Boolean {
        selectedMapLayerItem?.let {
            it.layerType?.let { layerType ->
                return layerType == NcmConstants.LayerType.NWP
            }
        }
        return false
    }

    protected fun removeSource(loadedStyle: Style?) {
        try {
            loadedStyle?.let { style ->
                val layerInfo = style.getLayer(CALLOUT_LAYER_ID)
                layerInfo?.let {
                    loadedStyle.removeLayer(it)
                }
                val layer = style.getLayer(MARKER_LAYER_ID)
                layer?.let {
                    loadedStyle.removeLayer(it)
                }

                val loadedSource = style.getSource(SOURCE_ID)
                loadedSource?.let {
                    loadedStyle.removeSource(it)
                }
//                removeCloud1H(style)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    protected fun getRasterLayer(style: Style?, customLayerID: String): RasterLayer? {
        if (style == null) return null
        try {
            if (style.isFullyLoaded) {
                return style.getLayer(customLayerID) as RasterLayer?
            }
        } catch (e: Exception) {
            return null
        }
        return null
    }


    protected fun getMapTimerDateFormat(): String {
        return if (true) {
            if (isArabic())
                NcmConstants.DATE_DISPLAY_DAY_MONTH_AR_
            else
                NcmConstants.DATE_DISPLAY_DAY_MONTH
        } else {
            NcmConstants.DATE_DISPLAY_DAY_MONTH_YEAR
        }

    }


    protected fun addBordersOnLayers(
        mainStyle: Style,
        addBorders: Boolean = true,
        addCityNames: Boolean = true
    ) {
        if (addBorders) {
            addRemoveLayersWithID(
                mainStyle,
                "coastlines_black",
                "coastlines_white",
                "country_boundary_black",
                "country_boundary_white"
            )
        }

        if (addCityNames) {
            addRemoveCityNameLayer(
                mainStyle,
                "place_city",
                "place_capital",
                "place_village",
                "place_town",
                "place_other"
            )
        }
    }

    protected fun addRemoveLayersWithID(loadedStyle: Style, vararg ids: String) {
        try {
            for (id in ids) {
                val layerInfo: LineLayer? = loadedStyle.getLayer(id) as LineLayer?
                if (layerInfo == null) {
                    return
                } else {
                    loadedStyle.removeLayer(layerInfo)
                    layerInfo.setProperties(
                        PropertyFactory.lineWidth(1f),
                        PropertyFactory.visibility(Property.VISIBLE)
                    )
                    loadedStyle.addLayer(layerInfo)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    protected fun addRemoveCityNameLayer(loadedStyle: Style, vararg ids: String) {
        try {
            for (id in ids) {
                val layerInfo: SymbolLayer? = loadedStyle.getLayer(id) as SymbolLayer?
                if (layerInfo == null) {
                    return
                } else {
                    loadedStyle.removeLayer(layerInfo)
                    loadedStyle.addLayer(layerInfo)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}