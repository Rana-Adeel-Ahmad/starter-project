package com.ncms.module.mapview

import android.content.Context
import android.os.CountDownTimer
import android.util.AttributeSet
import com.ncms.module.constants.NcmConstants
import com.ncms.module.constants.NcmConstants.DATE_TIME_FORMAT_MAP
import com.ncms.module.constants.NcmConstants.TIME_FORMAT_24
import com.ncms.module.utils.NCMUtility.AWS_WIND
import com.ncms.module.utils.NCMUtility.MAPS_DISPLAY_CLOUD_SATELLITE_TILE
import com.ncms.module.utils.NCMUtility.MAPS_DISPLAY_STATIONS
import com.ncms.module.utils.NCMUtility.getAWSType
import com.ncms.module.utils.datetimeutils.DateTimeUtils

open class NcmMapView(context: Context, attrs: AttributeSet?) : WarningsBaseMapE(context, attrs) {


    fun startMapPlayer() {
        calculateInitialAndEndIndex()
        val interval: Long = if (selectedMapLayerID == MAPS_DISPLAY_STATIONS) 1600 else 450
        if (countDownTimer == null) {
            countDownTimer = object : CountDownTimer((totalTilesCount * interval), interval) {
                override fun onFinish() {
                    countDownTimer?.start()
                }

                override fun onTick(millisUntilFinished: Long) {
                    onPlayerNextTick()
                }
            }.start()
        } else {
            countDownTimer?.start()
        }

    }

    fun stopMapPlayer() {
        countDownTimer?.cancel()
    }


    protected fun onPlayerNextTick() {
        if (mapSelectedCurrentIndex < mapTimerEndIndex) {
            mapSelectedCurrentIndex += 1
        } else {
            mapSelectedCurrentIndex = mapTimerStartIndex
        }

        when (this.selectedMapLayerID) {
            MAPS_DISPLAY_STATIONS -> {
                setAwsMarkers(
                    mapSelectedCurrentIndex,
                    true,
                    isZoomable = false,
                    isAWSWind = getAWSType() == AWS_WIND
                )
            }

            MAPS_DISPLAY_CLOUD_SATELLITE_TILE -> {
                loadCloudMapLayer(isUpdateSlider = false)
            }

            else -> {
                loadWmsTilesOnMap(true, mapSelectedCurrentIndex)
            }
        }
        listener?.onMapIndexChange(mapSelectedCurrentIndex)
//        cloud1hSettingsOnNextPrevious()
    }

    protected fun calculateInitialAndEndIndex() {
        val difference = totalTilesCount - mapSelectedCurrentIndex
        if (difference < itemsToShowForPlayer) {
            mapTimerStartIndex = totalTilesCount - itemsToShowForPlayer
            mapTimerEndIndex = totalTilesCount - 1
        } else {
            mapTimerStartIndex = mapSelectedCurrentIndex
            mapTimerEndIndex = mapSelectedCurrentIndex + itemsToShowForPlayer - 1
        }
        mapSelectedCurrentIndex = mapTimerStartIndex

    }

    fun changeMapPlayerIndex(progress: Int) {
        when (this.selectedMapLayerID) {
            MAPS_DISPLAY_STATIONS -> {
                mapSelectedCurrentIndex = progress
                if (mapSelectedCurrentIndex < totalTilesCount) {
                    setAwsMarkers(
                        mapSelectedCurrentIndex,
                        isZoomable = false,
                        isAWSWind = getAWSType() == AWS_WIND,
                        isUpdateSlider = false
                    )
                }
            }

            MAPS_DISPLAY_CLOUD_SATELLITE_TILE -> {
                mapSelectedCurrentIndex = progress
                if (mapSelectedCurrentIndex < totalTilesCount) {
                    loadCloudMapLayer(
                        true,
                        isUpdateSlider = false
                    )
                }
            }

            else -> {
                if (isLayerIsNWP()) {
                    mapSelectedCurrentIndex = progress
                    if (mapSelectedCurrentIndex < totalTilesCount) {
                        loadWmsTilesOnMap(true, mapSelectedCurrentIndex)

                    }
                } else {
                    mapSelectedCurrentIndex = progress
                    if (mapSelectedCurrentIndex < totalTilesCount) {
                        loadWmsTilesOnMap(true, mapSelectedCurrentIndex)
                    }
                }
            }
        }

    }

    fun changePlayerTimeIndex(progress: Int) {
        try {
            when (this.selectedMapLayerID) {
                MAPS_DISPLAY_STATIONS -> {
                    setUpMarkersWithInfoWindow(progress, false, isMapboxCall = false)
                }

                MAPS_DISPLAY_CLOUD_SATELLITE_TILE -> {
                    if (satelliteTimeStamps.size > progress) {

                        val actualTime = satelliteTimeStamps[progress]
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
                        listener?.onMapDateTimeUpdate(dateVal, timeVal)
                    }
                }

                else -> {
                    if (normalTilesTimeList.size > progress) {
                        val timeToShow = normalTilesTimeList[progress]
                        if (timeToShow.isNotEmpty()) {
                            val timeVal = DateTimeUtils.convertMapPlayDateTime(
                                timeToShow.replace("Z", "", true),
                                DATE_TIME_FORMAT_MAP,
                                TIME_FORMAT_24
                            )
                            val dateVal = DateTimeUtils.convertMapPlayDateTime(
                                timeToShow.replace("Z", "", true),
                                DATE_TIME_FORMAT_MAP,
                                getMapTimerDateFormat()
                            )
                            listener?.onMapDateTimeUpdate(dateVal, timeVal)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun moveToNextIndex() {
        if (mapSelectedCurrentIndex < totalTilesCount) {
            mapSelectedCurrentIndex += 1
            changeMapPlayerIndex(mapSelectedCurrentIndex)
            listener?.onMapIndexChange(mapSelectedCurrentIndex)
        }
    }

    fun moveToPreviousIndex() {
        if (mapSelectedCurrentIndex > 0) {
            mapSelectedCurrentIndex -= 1
            changeMapPlayerIndex(mapSelectedCurrentIndex)
            listener?.onMapIndexChange(mapSelectedCurrentIndex)
        }
    }

}