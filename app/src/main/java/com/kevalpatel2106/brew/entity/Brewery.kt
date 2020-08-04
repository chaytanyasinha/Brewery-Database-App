package com.kevalpatel2106.brew.entity

import android.os.Parcelable
import com.kevalpatel2106.brew.utils.DateParcelConverter
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.TypeParceler
import java.util.*

/**
 * Entity to handle the Brewery information.
 */
@Parcelize
@TypeParceler<Date, DateParcelConverter>()
data class Brewery(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val shortAddress: Address,
    val longAddress: Address,
    val phone: String? = null,
    val breweryType: String,
    val websiteUrl: String? = null,
    val updatedAt: Date,
    val tagList: List<String>,
    val latLon: Pair<Latitude, Longitude>?
) : Parcelable
