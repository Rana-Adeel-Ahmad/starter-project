package com.ncms.module.callbacks

import com.ncms.module.models.maps.observiatons.ObservationResult

interface NCMMapListener {
    fun onLoadingStart()
    fun onLoadingStop()
    fun onMapError(string: String?)
    fun onMapDateTimeUpdate(date: String?, time: String?)
    fun onMapIndexChange(currentIndex: Int)
    fun onMapPlayerCount(totalCount: Int)
    fun onMapAwsListReceived(list: ArrayList<ObservationResult>)
}