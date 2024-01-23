package com.ncms.module.mapview

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import com.mapbox.mapboxsdk.style.layers.PropertyFactory
import com.mapbox.mapboxsdk.style.layers.RasterLayer
import com.mapbox.mapboxsdk.style.sources.RasterSource
import com.mapbox.mapboxsdk.style.sources.TileSet
import com.ncms.module.constants.NcmConstants.DATE_TIME_FORMAT_MAP
import com.ncms.module.constants.NcmConstants.TIMESTAMP_FORMAT_SAT
import com.ncms.module.constants.NcmConstants.TIME_FORMAT_24
import com.ncms.module.models.maps.time.TimeStampResponse
import com.ncms.module.network.OnNCMResponse
import com.ncms.module.repositry.NcmRepository
import com.ncms.module.utils.NCMUtility
import com.ncms.module.utils.NCMUtility.MAPS_DISPLAY_CLOUD_SATELLITE_TILE
import com.ncms.module.utils.datetimeutils.DateTimeUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class CloudBaseMapC(context: Context, attrs: AttributeSet?) : AWSBaseMapB(context, attrs) {

    protected var satelliteTimeStamps: ArrayList<String> = ArrayList()

    fun loadCloudMapLayer(layerID: String) {
        this.selectedMapLayerID = layerID
        listener?.onLoadingStart()
        val timeStamp = "6h"
        val url = NCMUtility.getAPIAppsBaseUrl() + "mtb/$timeStamp/satellite_global/timesteps"
        CoroutineScope(Dispatchers.IO).launch {
            val data = NcmRepository().callGetSatTimeStamps(url)
            CoroutineScope(Dispatchers.Main).launch {
                when (data) {
                    is OnNCMResponse.Success<TimeStampResponse> -> {
                        listener?.onLoadingStop()
                        val response = data.value
                        response.result?.let {
                            satelliteTimeStamps.clear()
                            satelliteTimeStamps.addAll(it)
                            mapSelectedCurrentIndex = satelliteTimeStamps.size - 1
                            mapInitialIndex = mapSelectedCurrentIndex
                            loadCloudMapLayer()
                        }

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


    fun loadCloudMapLayer(
        isPlay: Boolean = false,
        initZoom: Boolean = true,
        isUpdateSlider: Boolean = true,
    ) {
        if (satelliteTimeStamps.isNullOrEmpty()) {
            listener?.onMapError(null)
        } else {
            try {
                if (satelliteTimeStamps.isNotEmpty()) {
                    totalTilesCount = satelliteTimeStamps.size
                    val actualTime = satelliteTimeStamps[mapSelectedCurrentIndex]
                    var timeStamp = DateTimeUtils.convertMapPlayDateTime(
                        actualTime,
                        DATE_TIME_FORMAT_MAP,
                        TIMESTAMP_FORMAT_SAT,
                        false
                    )
                    val tileURL =
                        NCMUtility.getMapURL() + "/mtb/satellite_global/" + timeStamp + "/{z}/{x}/{y}.webp"

                    val customLayerID = "$MAPS_DISPLAY_CLOUD_SATELLITE_TILE-$mapSelectedCurrentIndex"

                    mapView.getMapAsync {
                        mapboxMap = it

                        it.getStyle { mainStyle ->
                            if (mainStyle.isFullyLoaded) {
                                currentStyle = mainStyle
                                mainStyle.apply {
                                    val currentLoadLayer = getRasterLayer(mainStyle, customLayerID)
                                    if (currentLoadLayer == null) {
                                        val source = RasterSource(
                                            customLayerID,
                                            TileSet("tileset", tileURL),
                                            512
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
                                            el.setProperties(
                                                PropertyFactory.rasterOpacity(1.0f)
                                            )
                                        }
                                        currentLayer = currentLoadLayer
                                    }
                                    Handler(Looper.getMainLooper()).postDelayed({
                                        val existingLayer =
                                            getRasterLayer(mainStyle, currentLayerId)
                                        existingLayer?.let { el ->
                                            el.setProperties(
                                                PropertyFactory.rasterOpacity(0.01f)
                                            )
                                        }
                                        currentLayerId = customLayerID
                                        addBordersOnLayers(mainStyle)
                                    }, 100)

                                }
                            }
                        }
                    }

                    if (actualTime.isNotEmpty()) {
                        val timeVal = DateTimeUtils.convertMapPlayDateTime(
                            actualTime,
                            DATE_TIME_FORMAT_MAP,
                            TIME_FORMAT_24
                        )
                        val dateVal = DateTimeUtils.convertMapPlayDateTime(
                            actualTime,
                            DATE_TIME_FORMAT_MAP,
                            getMapTimerDateFormat()
                        )
                        listener?.onMapDateTimeUpdate(dateVal , timeVal)
//                        onUpdateDateTimeForCloudSat(timeVal, dateVal)

                    }
                }
                totalTilesCount = satelliteTimeStamps.size
                if (isUpdateSlider) listener?.onMapPlayerCount(totalTilesCount)
            } catch (e: Exception) {
                e.printStackTrace()
                listener?.onMapError(e.toString())
            }
        }
    }

}