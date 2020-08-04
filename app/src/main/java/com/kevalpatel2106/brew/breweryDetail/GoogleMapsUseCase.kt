package com.kevalpatel2106.brew.breweryDetail

import android.content.Intent
import android.net.Uri
import com.kevalpatel2106.brew.BuildConfig
import com.kevalpatel2106.brew.entity.Latitude
import com.kevalpatel2106.brew.entity.Longitude

/**
 * Use case to handle functions for the Google Map.
 */
object GoogleMapsUseCase {
    const val GOOGLE_MAPS_PACKAGE_NAME = "com.google.android.apps.maps"

    /**
     * Create the url for the static image for given [latLon].
     * Note: You need to set the API KEY to get the google image.
     */
    fun createStaticMapImage(latLon: Pair<Latitude, Longitude>?): String? {
        return if (latLon == null) {
            null
        } else {
            val (lat, lon) = latLon
            StringBuilder()
                .append("https://maps.googleapis.com/maps/api/staticmap")
                .append("?center=$lat,$lon")
                .append("&zoom=15&size=600x300&maptype=roadmap")
                .append("&key=${BuildConfig.GOOGLE_API_KEY}")
                .toString()
        }
    }

    /**
     * Get [Intent] for opening Google Maps on given [latitude] and [longitude].
     */
    fun getGoogleMapsIntent(latitude: Latitude, longitude: Longitude): Intent {
        val intentUri = Uri.parse("geo:$latitude,$longitude")
        return Intent(Intent.ACTION_VIEW, intentUri)
            .apply { setPackage(GOOGLE_MAPS_PACKAGE_NAME) }
    }
}
