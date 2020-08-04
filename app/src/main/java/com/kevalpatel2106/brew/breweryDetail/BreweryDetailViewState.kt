package com.kevalpatel2106.brew.breweryDetail

import com.kevalpatel2106.brew.entity.Address

/**
 * View state for [BreweryDetailFragment] UI.
 */
data class BreweryDetailViewState(
    val name: String,
    val imageUrl: String,
    val longAddress: Address,
    val phone: String? = null,
    val websiteUrl: String? = null,
    val typeToShow: String,
    val mapsImageUrl: String?
)
