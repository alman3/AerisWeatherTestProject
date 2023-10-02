/*
It has been around 7 years since I really used Android Studio in any serious way (
and even then, I wasn't an expert by any means).
I've never used the Kotlin language nor the MapBox and AerisWeather APIs before, and I have not
used many web-like interfaces before, either.
It also didn't help that I was sick during much of this time.

Within the time I spent on this assignment, I was not able to get the all of code to the point
that it worked properly, but I was able to get some functionality, and I was able
to include code snippets for some of the basic functionality I think is needed (but can't
verify the ideas actually work).

I spent a lot of time trying to get dependencies, imports,
and gradle implementations to work. It also took me a while to learn that some settings
disallow the use of other settings (such as the fact that databinding and view binding
cannot be used at the same time). I have very limited experience with Android and
modern Java / Kotlin style includes and dependencies, so much of my time was spent just
working through that.

I was able to get the unlock codes to work for MapBox, and got a
basic map displaying for MapBox. The MapBox site provided an example for heatmaps,
and I got a variation of the example to compile, but the heatmap itself doesn't appear.
I modified this example to what I *think* should mostly rework it to use AerisWeather data, but
again, I can't test it since the heatmap doesn't appear to begin with, so it probably isn't quite
right.
--------------------------------------

Design decisions:

I chose Mapbox over GoogleMaps because of the heatmaps, and because I wanted to use something more different than I used before (having used the Google Ads API before).

I used Kotlin instead of Java, because I never used it before, and was curious. Its also been
long enough since I used Java, that newer versions would almost be like a using new language for
me anyway.

I started with one of the standard Android Studio templates
--------------------------------------

Examples that I referenced:

https://docs.mapbox.com/android/maps/guides/install/

https://docs.mapbox.com/android/maps/examples/heatmap-layer/

https://www.section.io/engineering-education/how-to-use-databinding-in-android-using-kotlin/

https://www.section.io/engineering-education/implementing-mvvm-architecture-in-android-using-kotlin/
 */



package com.example.mapstest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.extension.style.expressions.dsl.generated.interpolate
import com.mapbox.maps.extension.style.layers.addLayerAbove
import com.mapbox.maps.extension.style.layers.addLayerBelow
import com.mapbox.maps.extension.style.layers.generated.CircleLayer
import com.mapbox.maps.extension.style.layers.generated.HeatmapLayer
import com.mapbox.maps.extension.style.layers.generated.circleLayer
import com.mapbox.maps.extension.style.layers.generated.heatmapLayer
import com.mapbox.maps.extension.style.layers.properties.generated.ProjectionName
import com.mapbox.maps.extension.style.projection.generated.projection
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.style

var mapView: MapView? = null

class MainActivity : AppCompatActivity() {
    private lateinit var mapboxMap: MapboxMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mapView = findViewById(R.id.mapView)
        //The style doesn't seem to be applied here, which basically
        // makes the rest of the program not work, besides the basic
        // map:
        mapView?.getMapboxMap()?.apply {
            loadStyle(
                style(Style.DARK) {
                    +projection(ProjectionName.GLOBE)
                }
            ) { style ->
                addRuntimeLayers(style)
            }
        }
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

    private fun addRuntimeLayers(style: Style) {
        //Create sources (Note: This layer selection is not implemented, as so acts as a placeholder
        // for future work. The eventual intention would be to add layer checkboxes to the map UI,
        // and set these boolean values using that)

        //if(StormHeatmapLayer) {
            style.addSource(createHeatMapSource())
            //Add the heatmap for storm data
            style.addLayerAbove(createHeatmapLayer(), "waterway-label")
        //}
        //if(StormColoredPointsLayer) {
            style.addSource(createColoredPointsSource())
            //Add the colored point map for storm data
            style.addLayerBelow(createCircleLayer(), HEATMAP_LAYER_ID)
        //}
        //if(RadarLayer) {
            style.addSource(createRadarSource())
            //Add the colored point map for radar data
            style.addLayerBelow(createCircleLayer(), HEATMAP_LAYER_ID)
        //}
        //if(AlertsLayer) {
            style.addSource(createAlertsSource())
            //Add the colored point map for alerts data
            style.addLayerBelow(createCircleLayer(), HEATMAP_LAYER_ID)
        //}
    }

    private fun createHeatMapSource(): GeoJsonSource {
        //Probably needs a different call than geoJsonSource() since this will not be json data,
        //but I didn't have enough time to find the appropriate call here
        return geoJsonSource(AERISWEATHER_STORM_REPORTS_SOURCE_URL + "/from?p=-48hours/") {
            url(AERISWEATHER_STORM_REPORTS_SOURCE_URL + "/from?p=-48hours/")}

   }
    private fun createColoredPointsSource(): GeoJsonSource {
        //I'm not sure if these are relative coordinates, or if we need xyz variables to access the correct range?
        //I wasn't able to test to verify. This one will be a json source, so geoJsonSource()
        //should be appropriate
        return geoJsonSource(AERISWEATHER_STORM_REPORTS_SOURCE_URL + "/:endpoint/within?p=43.765,-97.128,41.89,-95.091/from?p=-48hours/format=geojson") {
            url(AERISWEATHER_STORM_REPORTS_SOURCE_URL + "/:endpoint/within?p=43.765,-97.128,41.89,-95.091/from?p=-48hours/format=geojson")}
    }
    private fun createRadarSource(): GeoJsonSource {
        //Probably needs a different call than geoJsonSource() since this will not be json data,
        //but I didn't have enough time to find the appropriate call here
        return geoJsonSource(AERISWEATHER_BASE_SOURCE_URL + "radar/:endpoint/within?p=43.765,-97.128,41.89,-95.091/from?p=-48hours") {
            url(AERISWEATHER_BASE_SOURCE_URL + "radar/:endpoint/within?p=43.765,-97.128,41.89,-95.091/from?p=-48hours")}

    }private fun createAlertsSource(): GeoJsonSource {
        //Probably needs a different call than geoJsonSource() since this will not be json data,
        //but I didn't have enough time to find the appropriate call here
        return geoJsonSource(AERISWEATHER_BASE_SOURCE_URL + "alerts/:endpoint/within?p=43.765,-97.128,41.89,-95.091/from?p=-48hours") {
            url(AERISWEATHER_BASE_SOURCE_URL + "alerts/:endpoint/within?p=43.765,-97.128,41.89,-95.091/from?p=-48hours")}

    }

    //Basically the heatmap function from the mapbox example, but with storm data passed in,
    //and storm ID labels
    private fun createHeatmapLayer(): HeatmapLayer {
        return heatmapLayer(
            HEATMAP_LAYER_ID,
            STORM_SOURCE_ID
        ) {
            maxZoom(9.0)
            sourceLayer(HEATMAP_LAYER_SOURCE)
// Begin color ramp at 0-stop with a 0-transparancy color
// to create a blur-like effect.
            heatmapColor(
                interpolate {
                    linear()
                    heatmapDensity()
                    stop {
                        literal(0)
                        rgba(33.0, 102.0, 172.0, 0.0)
                    }
                    stop {
                        literal(0.2)
                        rgb(103.0, 169.0, 207.0)
                    }
                    stop {
                        literal(0.4)
                        rgb(209.0, 229.0, 240.0)
                    }
                    stop {
                        literal(0.6)
                        rgb(253.0, 219.0, 240.0)
                    }
                    stop {
                        literal(0.8)
                        rgb(239.0, 138.0, 98.0)
                    }
                    stop {
                        literal(1)
                        rgb(178.0, 24.0, 43.0)
                    }
                }
            )
// Increase the heatmap weight based on frequency and property magnitude
            heatmapWeight(
                interpolate {
                    linear()
                    get { literal("mag") }
                    stop {
                        literal(0)
                        literal(0)
                    }
                    stop {
                        literal(6)
                        literal(1)
                    }
                }
            )
// Increase the heatmap color weight weight by zoom level
// heatmap-intensity is a multiplier on top of heatmap-weight
            heatmapIntensity(
                interpolate {
                    linear()
                    zoom()
                    stop {
                        literal(0)
                        literal(1)
                    }
                    stop {
                        literal(9)
                        literal(3)
                    }
                }
            )
// Adjust the heatmap radius by zoom level
            heatmapRadius(
                interpolate {
                    linear()
                    zoom()
                    stop {
                        literal(0)
                        literal(2)
                    }
                    stop {
                        literal(9)
                        literal(20)
                    }
                }
            )
// Transition from heatmap to circle layer by zoom level
            heatmapOpacity(
                interpolate {
                    linear()
                    zoom()
                    stop {
                        literal(7)
                        literal(1)
                    }
                    stop {
                        literal(9)
                        literal(0)
                    }
                }
            )
        }
    }

    //Basically the colored points (CircleLayer) function from the mapbox example, but with storm/
    // radar/alerts data passed in, and storm ID labels (other labels would need to be added as
    // future work)
    private fun createCircleLayer(): CircleLayer {
        return circleLayer(
            CIRCLE_LAYER_ID,
            STORM_SOURCE_ID
        ) {
            circleRadius(
                interpolate {
                    linear()
                    zoom()
                    stop {
                        literal(7)
                        interpolate {
                            linear()
                            get { literal("mag") }
                            stop {
                                literal(1)
                                literal(1)
                            }
                            stop {
                                literal(6)
                                literal(4)
                            }
                        }
                    }
                    stop {
                        literal(16)
                        interpolate {
                            linear()
                            get { literal("mag") }
                            stop {
                                literal(1)
                                literal(5)
                            }
                            stop {
                                literal(6)
                                literal(50)
                            }
                        }
                    }
                }
            )
            circleColor(
                interpolate {
                    linear()
                    get { literal("mag") }
                    stop {
                        literal(1)
                        rgba(33.0, 102.0, 172.0, 0.0)
                    }
                    stop {
                        literal(2)
                        rgb(102.0, 169.0, 207.0)
                    }
                    stop {
                        literal(3)
                        rgb(209.0, 229.0, 240.0)
                    }
                    stop {
                        literal(4)
                        rgb(253.0, 219.0, 199.0)
                    }
                    stop {
                        literal(5)
                        rgb(239.0, 138.0, 98.0)
                    }
                    stop {
                        literal(6)
                        rgb(178.0, 24.0, 43.0)
                    }
                }
            )
            circleOpacity(
                interpolate {
                    linear()
                    zoom()
                    stop {
                        literal(7)
                        literal(0)
                    }
                    stop {
                        literal(8)
                        literal(1)
                    }
                }
            )
            circleStrokeColor("white")
            circleStrokeWidth(0.1)
        }
    }

    companion object {
        //With user Id and secret (could be moved to private values, but since these were assigned
        // on the assignment, it doesn't seem like its bad for them to be public here)
        private const val AERISWEATHER_BASE_SOURCE_URL =
            "https://maps.aerisapi.com/aerisweather_id_property/aerisweather_secret_property/"
        //Should be something more like this (with values from gradle). I couldn't get this to compile:
        //private const val AERISWEATHER_BASE_SOURCE_URL =
        //            "https://maps.aerisapi.com/" + aerisweather_id_property  + "/" + aerisweather_secret_property +"/"
        //With user Id and secret
        private const val AERISWEATHER_STORM_REPORTS_SOURCE_URL =
            "https://api.aerisapi.com/stormreports/aerisweather_id_property/aerisweather_secret_property/"
        //Should be something more like this (with values from gradle). I couldn't get this to compile:
        //private const val AERISWEATHER_STORM_REPORTS_SOURCE_URL =
        //            "https://maps.aerisapi.com/stormreports/" + aerisweather_id_property  + "/" + aerisweather_secret_property +"/"
        private const val STORM_SOURCE_ID = "Storms"
        private const val HEATMAP_LAYER_ID = "storm-heat"
        private const val HEATMAP_LAYER_SOURCE = "StormReport"
        private const val CIRCLE_LAYER_ID = "storms-circle"
    }
}