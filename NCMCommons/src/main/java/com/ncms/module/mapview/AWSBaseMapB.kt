package com.ncms.module.mapview

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.annotations.BubbleLayout
import com.mapbox.mapboxsdk.annotations.IconFactory
import com.mapbox.mapboxsdk.annotations.Marker
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.expressions.Expression
import com.mapbox.mapboxsdk.style.layers.Property
import com.mapbox.mapboxsdk.style.layers.PropertyFactory
import com.mapbox.mapboxsdk.style.layers.SymbolLayer
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import com.ncms.module.R
import com.ncms.module.constants.NcmConstants
import com.ncms.module.ext.isArabic
import com.ncms.module.models.maps.observiatons.Observation
import com.ncms.module.models.maps.observiatons.ObservationResponse
import com.ncms.module.models.maps.observiatons.ObservationResult
import com.ncms.module.network.OnNCMResponse
import com.ncms.module.repositry.NcmRepository
import com.ncms.module.utils.NCMUtility
import com.ncms.module.utils.datetimeutils.DateTimeUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import kotlin.math.roundToInt

open class AWSBaseMapB(context: Context, attrs: AttributeSet?) : WMSBaseMapA(context, attrs) {

    protected var awsFeatureCollection: FeatureCollection? = null
    protected var singleFeatureCollection: FeatureCollection? = null
    protected var awsCurrentTimeStamp = ""
    protected var isAWSNotFetched = true
    protected var isIconOverlap = false
    protected val awsStationsList = ArrayList<ObservationResult>()
    protected var awsInitialDelay: Long = 300
    protected var selectedMarker: Marker? = null
    protected var selectedStationID: String = ""
    protected var selectedStationObservation: ObservationResult? = null
    protected var source: GeoJsonSource? = null


    fun loadAwsStationsData(layerID: String) {
        this.selectedMapLayerID = layerID
        listener?.onLoadingStart()
        this.isFirstLoad = true
        CoroutineScope(Dispatchers.IO).launch {
            val data = NcmRepository().callGetAWSObservations()
            CoroutineScope(Dispatchers.Main).launch {
                when (data) {
                    is OnNCMResponse.Success<ObservationResponse> -> {
                        listener?.onLoadingStop()
                        val response = data.value
                        onAWSResponse(response)
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


    private fun onAWSResponse(response: ObservationResponse) {
        try {
            mapSelectedCurrentIndex = 0
            isAWSNotFetched = false
            awsStationsList.clear()
            awsStationsList.addAll(response.observationResult)
            for (i in 0 until awsStationsList.size) {
                val stationID =
                    if (selectedStationID.isNullOrEmpty()) NcmConstants.DEFAULT_STATION_ID.toString() else selectedStationID
                if (awsStationsList[i].id == stationID) {
                    val item = awsStationsList[i]
                    awsStationsList.removeAt(i)
                    awsStationsList.add(0, item)
                }
            }
//            if (isRefreshFromBackground) {
//                setAwsMarkersOnRefresh(awsStationsList)
//            } else {
            setAwsMarkers(isUpdateSlider = true)
//            }
//            updateStationList(awsStationsList)
            listener?.onMapAwsListReceived(awsStationsList)
        } catch (e: Exception) {
            e.printStackTrace()
            listener?.onMapError(e.toString())
        }
    }

    fun setAwsMarkers(
        index: Int = mapSelectedCurrentIndex,
        isReverse: Boolean = false,
        isAWSWind: Boolean = isWindSelected(),
        isZoomable: Boolean = true,
        stationID: String = "",
        isUpdateSlider: Boolean = false
    ) {
        Handler(Looper.getMainLooper()).postDelayed({
            try {
                awsInitialDelay = 60
                totalTilesCount = getMaxObservationSize(awsStationsList, stationID)
                if (isUpdateSlider) {
//                    if (isFullMap()) {
                    if (true) {
                        mapSelectedCurrentIndex = totalTilesCount - 1
                        mapInitialIndex = mapSelectedCurrentIndex
                    }
                    listener?.onMapPlayerCount(totalTilesCount)
//                    onUpdateSliderCount(totalTilesCount)
                } else {
                    mapSelectedCurrentIndex = index
                }


                mapView.getMapAsync { mapboxMap ->
                    mapboxMap.getStyle { mainStyle ->
                        if (mainStyle.isFullyLoaded) {
                            currentStyle = mainStyle
                            setUpMarkersWithInfoWindow(
                                mapSelectedCurrentIndex,
                                isReverse,
                                isAWSWind,
                                isZoomable,
                                stationID
                            )
                        }

                    }
                }


            } catch (ex: Exception) {
                listener?.onMapError(ex.toString())
                ex.printStackTrace()
            }
        }, awsInitialDelay)

    }

    protected fun setUpMarkersWithInfoWindow(
        awsIndex: Int,
        isReverse: Boolean,
        isAWSWind: Boolean = false,
        isZoomable: Boolean = true,
        stationID: String = "",
        isMapboxCall: Boolean = true
    ) {
        try {
            val stationFinalList: ArrayList<ObservationResult> = ArrayList()
            if (NCMUtility.getAWSType() == NCMUtility.AWS_VISIBILITY) {
                stationFinalList.addAll(getAwsVisibilityList(this.awsStationsList))
            } else {
                stationFinalList.addAll(this.awsStationsList)
            }
            for (i in 0 until stationFinalList.size) {
                if (stationFinalList[i].id == stationID) {
                    val item = stationFinalList[i]
                    stationFinalList.removeAt(i)
                    stationFinalList.add(0, item)
                }
            }
            val symbolLayerIconFeatureList: MutableList<Feature> = ArrayList()
            val latLngArrayList = ArrayList<LatLng>()
            var zoomedItem: ObservationResult? = null

            for (i in stationFinalList.indices) {
                val awsStation = stationFinalList[i]
                val lat = awsStation.lat.toDouble()
                val lng = awsStation.lng.toDouble()
                if (awsStation.id == stationID) {
                    zoomedItem = awsStation
                }
                latLngArrayList.add(LatLng(lat, lng))
                val name = NCMUtility.getRespectiveLanguageValueIfNotNull(
                    context,
                    awsStation.nameEn,
                    awsStation.nameAr
                )
                var observationList = if (true) {
                    awsStation.observations.asReversed()
                } else {
                    if (isReverse) awsStation.observations.asReversed() else awsStation.observations
                }
                var observationModel: Observation? = null

                if (i == 0) {
                    observationModel = observationList[awsIndex]
                    awsCurrentTimeStamp = observationModel.time
                } else {
                    observationModel = observationList.find { it.time == awsCurrentTimeStamp }
                    if (observationModel == null) {
                        val subString = awsCurrentTimeStamp.subSequence(0, 13).toString()
                        observationModel = observationList.find { it.time.contains(subString) }
                    }
                    if (observationModel == null) {
                        for (observation in observationList) {
                            if (isExistInOneHourTimeStamp(
                                    awsStation.nameEn,
                                    awsCurrentTimeStamp,
                                    observation.time
                                )
                            ) {
                                observationModel = observation
                                break
                            }
                        }
                    }
                }
                if (observationModel == null) {
                    continue
                }
                if (awsStation.id == stationID) {
                    awsCurrentTimeStamp = observationModel.time
                }
                if (isMapboxCall) {
                    val value = getAwsValueFromModel(observationModel)
                    if (!value.isNullOrEmpty()) {
                        val windDir = getWindDirFromModel(observationModel)
                        val valAdded: String =
                            if (NCMUtility.getAWSType() == NCMUtility.AWS_TEMP
                                || NCMUtility.getAWSType() == NCMUtility.AWS_DEW_POINT
                                || NCMUtility.getAWSType() == NCMUtility.AWS_VISIBILITY
                            )
                                NCMUtility.round(value, 1).toString()
                            else
                                NCMUtility.getRoundValue(value).toString()

                        val feature = Feature.fromGeometry(Point.fromLngLat(lng, lat))
                        feature.addStringProperty(PROPERTY_NAME, name)
                        feature.addStringProperty(PROPERTY_VALUE, valAdded.replace(".0", ""))
                        feature.addNumberProperty(PROPERTY_ICON_VALUE, windDir)
                        feature.addBooleanProperty(PROPERTY_SELECTED, false)
                        feature.addStringProperty(
                            PROPERTY_COLOR_VALUE,
                            NCMUtility.getColor(valAdded.toDouble())
                        )
                        symbolLayerIconFeatureList.add(feature)
                    }
                }
            }

            awsFeatureCollection = FeatureCollection.fromFeatures(symbolLayerIconFeatureList)
            for (singleFeature in awsFeatureCollection!!.features()!!) {
                singleFeature.addBooleanProperty(
                    PROPERTY_SELECTED,
                    false
                )
            }

            this.mapboxMap?.getStyle { style ->
                removeSource(style)
                setupSource(style)
                setUpImage(style, isAWSWind)
                setUpMarkerLayer(style, isAWSWind)
                setUpInfoWindowLayer(style)
            }
            if (isMapboxCall) {
                awsFeatureCollection = FeatureCollection.fromFeatures(symbolLayerIconFeatureList)
                for (singleFeature in awsFeatureCollection!!.features()!!) {
                    singleFeature.addBooleanProperty(
                        PROPERTY_SELECTED,
                        false
                    )
                }

                mapView.getMapAsync { mapboxMap ->
                    mapboxMap.getStyle { mainStyle ->
                        currentStyle = mainStyle
                        removeSource(mainStyle)
                        mainStyle.apply {
                            if (this.isFullyLoaded) {
                                removeSource(this)
                                setupSource(this)
                                setUpImage(this, isAWSWind)
                                setUpMarkerLayer(this, isAWSWind)
                                setUpInfoWindowLayer(this)

                            }
                        }
                    }
                }


                GenerateViewIconTask(activity!!).execute(awsFeatureCollection)

                if (selectedStationObservation != null) {
                    if (isZoomable) {
                        setMapBoxZoom(
                            selectedStationObservation!!.lat.toDouble(),
                            selectedStationObservation!!.lng.toDouble(),
                            NCMUtility.MAPS_EXTRA_ZOOMED
                        )
                    }
                    addAwsMarker(
                        selectedStationObservation!!.lat.toDouble(),
                        selectedStationObservation!!.lng.toDouble(),
                    )
                } else {
                    if (zoomedItem == null) {
                        if (isZoomable) setMapBoxZoom()
                    } else {
                        if (isZoomable) {
                            setMapBoxZoom(
                                zoomedItem.lat.toDouble(),
                                zoomedItem.lng.toDouble(),
                                NCMUtility.MAPS_EXTRA_ZOOMED
                            )
                        }
                    }
                }
            }
            if (awsCurrentTimeStamp.isNotEmpty()) {
                val timeVal = DateTimeUtils.convertMapPlayDateTime(
                    awsCurrentTimeStamp,
                    NcmConstants.DATE_TIME_FORMAT_MAP,
                    NcmConstants.TIME_FORMAT_24
                )
                val dateVal = DateTimeUtils.convertMapPlayDateTime(
                    awsCurrentTimeStamp,
                    NcmConstants.DATE_TIME_FORMAT_MAP,
                    getMapTimerDateFormat()
                )
                listener?.onMapDateTimeUpdate(dateVal, timeVal)
//                updateAWSUIComponents(getAwsSelectedText(), timeVal, dateVal)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            listener?.onMapError(e.toString())

        }
    }


    private fun setupSource(loadedStyle: Style) {
        source = GeoJsonSource(SOURCE_ID, awsFeatureCollection)
        loadedStyle.addSource(source!!)
    }

    private fun setUpMarkerLayer(loadStyle: Style, isAWS: Boolean) {
        val finalLayer = getWithIconLayer()
        loadStyle.addLayer(finalLayer)
    }

    private fun getWithIconLayer(): SymbolLayer {
        return SymbolLayer(MARKER_LAYER_ID, SOURCE_ID).withProperties(
            PropertyFactory.textField(getAWSValue()),
            PropertyFactory.backgroundColor(
                ContextCompat.getColor(
                    activity?.baseContext!!,
                    R.color.colorAppBlue
                )
            ),
            PropertyFactory.textSize(14f),
            PropertyFactory.textColor(getTextColorExpression()),
            PropertyFactory.textAllowOverlap(false),
            PropertyFactory.iconOffset(arrayOf(-10f, -10f)),
            PropertyFactory.iconImage(MARKER_IMAGE_ID),
            PropertyFactory.textHaloColor(
                ContextCompat.getColor(
                    activity?.baseContext!!,
                    R.color.colorBlack
                )
            ),
            PropertyFactory.textHaloWidth(0.5f),
            PropertyFactory.iconAllowOverlap(false),
            PropertyFactory.iconIgnorePlacement(false),
            PropertyFactory.iconRotate(getIconExpression())
        )
    }

    private fun setUpInfoWindowLayer(loadedStyle: Style) {
        val iconLayer = SymbolLayer(CALLOUT_LAYER_ID, SOURCE_ID).withProperties(
            PropertyFactory.textField(getAWSValue()),
            PropertyFactory.backgroundColor(
                ContextCompat.getColor(
                    activity!!,
                    R.color.colorAppBlue
                )
            ),
            PropertyFactory.textSize(14f),
            PropertyFactory.textColor(getTextColorExpression()),
            PropertyFactory.textAllowOverlap(false),
            PropertyFactory.iconOffset(arrayOf(-9f, -9f)),
            PropertyFactory.iconImage("{name}"),
            PropertyFactory.textHaloColor(
                ContextCompat.getColor(
                    activity!!,
                    R.color.colorBlack
                )
            ),
            PropertyFactory.textHaloWidth(0.5f),
            PropertyFactory.iconAllowOverlap(false),
            PropertyFactory.iconIgnorePlacement(false),
            PropertyFactory.iconOffset(arrayOf(-2f, -2f)),
            PropertyFactory.iconAnchor(Property.ICON_ANCHOR_BOTTOM),
        ).withFilter(Expression.eq(Expression.get(PROPERTY_SELECTED), Expression.literal(true)))
        loadedStyle.addLayer(iconLayer)
    }


    private fun getAwsVisibilityList(awsList: ArrayList<ObservationResult>): ArrayList<ObservationResult> {
        return try {
            val list = awsList.filter { it.observations[0].visibility != null }
            list as ArrayList<ObservationResult>
        } catch (e: Exception) {
            e.printStackTrace()
            ArrayList()
        }
    }


    private fun isWindSelected(): Boolean {
        return NCMUtility.getAWSType() == NCMUtility.AWS_WIND
    }

    private fun setUpImage(@NonNull loadedStyle: Style, isAWSWind: Boolean) {
        loadedStyle.addImage(MARKER_IMAGE_ID, getWindBitmap(isAWSWind)!!)
    }

    private fun getAwsValueFromModel(o: Observation): String? {
        try {
            var result: String? = ""
            when {
                NCMUtility.getAWSType() == NCMUtility.AWS_TEMP -> {
                    result = NCMUtility.getTemperatureRoundValue(o.temp!!)
                }

                NCMUtility.getAWSType() == NCMUtility.AWS_SOLAR -> {
                    result = o.radiation.toString()
                }

                NCMUtility.getAWSType() == NCMUtility.AWS_AIR_PRESSURE -> {
                    result = o.pressure.toString()
                }

                NCMUtility.getAWSType() == NCMUtility.AWS_HUMIDITY -> {
                    result = try {
                        o.humidity!!.toDouble().roundToInt().toString()
                    } catch (ex: Exception) {
                        o.humidity.toString()
                    }
                }

                NCMUtility.getAWSType() == NCMUtility.AWS_WIND -> {
                    o.windSpeed.let {
                        result = NCMUtility.getWindValue(o.windSpeed!!.toDouble(), 0)
                    }
                }

                NCMUtility.getAWSType() == NCMUtility.AWS_DEW_POINT -> {
                    result = if (o.dewpoint == null) {
                        null
                    } else {
                        NCMUtility.getWindValue(o.dewpoint?.toDouble(), 1)
                    }
                }

                NCMUtility.getAWSType() == NCMUtility.AWS_VISIBILITY -> {
                    var visibilityValue = 0.0
                    visibilityValue = if (o.visibility == null) {
                        0.0
                    } else {
                        o.visibility?.toDouble()!! / 1000
                    }
                    result = NCMUtility.getWindValue(visibilityValue, 1)
                }

                else -> result = o.temp.toString()
            }
            return if (result.isNullOrEmpty()) null else result
        } catch (e: Exception) {
            return null
        }
    }

    private fun getWindDirFromModel(o: Observation): Float {
        return try {
            if (o.windDir == null)
                0.0F
            else
                o.windDir!!.toFloat()
        } catch (e: Exception) {
            0.0F
        }
    }

    private fun isExistInOneHourTimeStamp(
        name: String,
        currentTime: String,
        itemTime: String
    ): Boolean {
        val currentTimeStamp =
            DateTimeUtils.getDateTimeStamp(currentTime, NcmConstants.DATE_TIME_FORMAT_MAP)
        val otherTimeStamp =
            DateTimeUtils.getDateTimeStamp(itemTime, NcmConstants.DATE_TIME_FORMAT_MAP)

        val difference: Long = currentTimeStamp - otherTimeStamp
        val days = (difference / (1000 * 60 * 60 * 24)).toInt()
        var hours = ((difference - 1000 * 60 * 60 * 24 * days) / (1000 * 60 * 60)).toInt()
        hours = if (hours < 0) -hours else hours
        val min =
            ((difference - 1000 * 60 * 60 * 24 * days - 1000 * 60 * 60 * hours).toInt() / (1000 * 60))
        return if (hours < 1) true else hours < 2 && min < 1
    }

    private fun getWindBitmap(isAWSWind: Boolean): Bitmap? {
        val color = if (isAWSWind) Color.RED else Color.TRANSPARENT
        val bitmap = BitmapFactory.decodeResource(this.resources, R.drawable.ic_wind_arrow)
        val paint = Paint()
        paint.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)
        val bitmapResult =
            Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmapResult)
        canvas.drawBitmap(bitmap, 0f, 0f, paint)
        if (bitmapResult == null) return bitmap
        return bitmapResult
        return null
    }

    private fun getAWSValue(): Expression? {
        val ex: Expression = Expression.get(PROPERTY_VALUE)
        when {
            NCMUtility.getAWSType() == NCMUtility.AWS_TEMP -> {
                return Expression.toString(Expression.concat(ex, Expression.literal("°")))
            }

            NCMUtility.getAWSType() == NCMUtility.AWS_HUMIDITY -> {
                return Expression.toString(Expression.concat(ex, Expression.literal("%")))
            }

            NCMUtility.getAWSType() == NCMUtility.AWS_WIND -> {
                return Expression.toString(ex)
            }

            NCMUtility.getAWSType() == NCMUtility.AWS_SOLAR -> {
                return Expression.toString(ex)
            }

            NCMUtility.getAWSType() == NCMUtility.AWS_AIR_PRESSURE -> {
                return Expression.toString(ex)
            }

            NCMUtility.getAWSType() == NCMUtility.AWS_DEW_POINT -> {
                return Expression.toString(ex)
            }

            NCMUtility.getAWSType() == NCMUtility.AWS_VISIBILITY -> {
                val str = if (isArabic()) " كم " else " km"
                return Expression.toString(Expression.concat(ex, Expression.literal(str)))
            }

            else -> return Expression.toString(ex)
        }
    }


    private fun getMaxObservationSize(list: ArrayList<ObservationResult>, stationID: String): Int {
        var observationModel: ObservationResult? = null
        observationModel = if (stationID.isNullOrEmpty()) {
            list.maxByOrNull { it.observations.size }
        } else {
            list.find { it.id == stationID }
        }
        if (observationModel == null) return 0
        if (observationModel.observations.isEmpty() == null) return 0
        return observationModel.observations.size
    }

    open fun getTextColorExpression(): Expression {
        val ex: Expression = Expression.get(PROPERTY_COLOR_VALUE)
        return Expression.toColor(ex)
    }

    private fun getIconExpression(): Expression {
        val ex: Expression = Expression.get(PROPERTY_ICON_VALUE)
        return Expression.toNumber(ex)

    }

    @SuppressLint("StaticFieldLeak")
    private inner class GenerateViewIconTask @JvmOverloads internal constructor(
        activity: Activity,
        refreshSource: Boolean = false
    ) :
        AsyncTask<FeatureCollection?, Void?, HashMap<String, Bitmap>?>() {
        private val viewMap = HashMap<String, View>()
        private val activityRef: WeakReference<Activity> = WeakReference(activity)
        private val refreshSource: Boolean = refreshSource

        override fun doInBackground(vararg params: FeatureCollection?): HashMap<String, Bitmap>? {
            val activity = activityRef.get()
            return if (activity != null) {
                val imagesMap = HashMap<String, Bitmap>()
                val inflater = LayoutInflater.from(activity)
                val featureCollection = params[0]
                for (feature in featureCollection!!.features()!!) {
                    val bubbleLayout = inflater.inflate(
                        R.layout.symbol_layer_info_window_layout_callout,
                        null
                    ) as BubbleLayout
                    val name =
                        feature.getStringProperty(PROPERTY_NAME)
                    val titleTextView =
                        bubbleLayout.findViewById<TextView>(R.id.info_window_title)
                    titleTextView.text = name
                    val measureSpec =
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
                    bubbleLayout.measure(measureSpec, measureSpec)
                    val measuredWidth = bubbleLayout.measuredWidth.toFloat()
                    bubbleLayout.arrowPosition = measuredWidth / 2 - 5
                    val bitmap = SymbolGenerator.generate(bubbleLayout)
                    imagesMap[name] = bitmap
                    viewMap[name] = bubbleLayout
                }
                imagesMap
            } else {
                null
            }
        }

        override fun onPostExecute(bitmapHashMap: HashMap<String, Bitmap>?) {
            super.onPostExecute(bitmapHashMap)
            val activity = activityRef.get()
            if (activity != null && bitmapHashMap != null) {
                setImageGenResults(bitmapHashMap)
//                if (refreshSource) {
//                    if (NCMUtility.getMapLayerSelectedID() == NCMUtility.MAPS_DISPLAY_SINGLE_MARKER)
//                        refreshSourceSingleMarkers() else
//                        refreshSource()
//                }
            }
        }

    }

    /**
     * Utility class to generate Bitmaps for Symbol.
     */
    private object SymbolGenerator {
        /**
         * Generate a Bitmap from an Android SDK View.
         *
         * @param view the View to be drawn to a Bitmap
         * @return the generated bitmap
         */
        fun generate(view: View): Bitmap {
            val measureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            view.measure(measureSpec, measureSpec)
            val measuredWidth = view.measuredWidth
            val measuredHeight = view.measuredHeight
            view.layout(0, 0, measuredWidth, measuredHeight)
            val bitmap =
                Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888)
            bitmap.eraseColor(Color.TRANSPARENT)
            val canvas = Canvas(bitmap)
            view.draw(canvas)
            return bitmap
        }
    }

    private fun addAwsMarker(lat: Double, lng: Double, markerID: Int = R.drawable.ic_marker_aws) {
        try {
            removeAwsMarker()
            val markerOption = MarkerOptions().position(LatLng(lat, lng))
            val icon =
                IconFactory.getInstance(activity!!)
                    .fromBitmap(getBitmap(markerID))
            markerOption.icon = icon
            selectedMarker = markerOption.marker
            mapboxMap?.addMarker(markerOption)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getBitmap(drawableRes: Int): Bitmap {
        val drawable = ContextCompat.getDrawable(activity!!, drawableRes)!!
        val canvas = Canvas()
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        canvas.setBitmap(bitmap)
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        drawable.draw(canvas)
        return bitmap
    }

    fun refreshSourceSingleMarkers() {
        if (source != null && singleFeatureCollection != null) {
            source?.setGeoJson(singleFeatureCollection)
        }
    }

    fun setImageGenResults(imageMap: HashMap<String, Bitmap>) {
        mapboxMap?.getStyle { style: Style ->
            style.addImages(imageMap)
        }
    }

    fun refreshSource() {
        if (source != null && awsFeatureCollection != null) {
            source!!.setGeoJson(awsFeatureCollection)
        }
    }

    private fun removeAwsMarker() {
        try {
            if (selectedMarker == null) return
            mapboxMap?.removeMarker(selectedMarker!!)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}