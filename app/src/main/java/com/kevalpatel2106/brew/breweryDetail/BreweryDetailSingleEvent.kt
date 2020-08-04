package com.kevalpatel2106.brew.breweryDetail

import com.kevalpatel2106.brew.entity.Latitude
import com.kevalpatel2106.brew.entity.Longitude

/**
 * Single view event for [BreweryDetailFragment].
 */
sealed class BreweryDetailSingleEvent {
    data class OpenLocationOnMap(val latitude: Latitude, val longitude: Longitude) :
        BreweryDetailSingleEvent()

    object CloseDetail : BreweryDetailSingleEvent()
}
