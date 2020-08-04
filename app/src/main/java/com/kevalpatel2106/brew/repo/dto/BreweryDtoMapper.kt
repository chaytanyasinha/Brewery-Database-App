package com.kevalpatel2106.brew.repo.dto

import com.kevalpatel2106.brew.entity.Address
import com.kevalpatel2106.brew.entity.Brewery
import com.kevalpatel2106.brew.entity.Latitude
import com.kevalpatel2106.brew.entity.Longitude
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

/**
 * Mapper that maps [BreweryDto] to [Brewery]
 */
interface BreweryDtoMapper {
    fun toEntity(dto: BreweryDto): Brewery
}

class BreweryDtoMapperImpl : BreweryDtoMapper {

    override fun toEntity(dto: BreweryDto): Brewery {
        return Brewery(
            id = dto.id,
            name = dto.name,
            imageUrl = getRandomImageUrl(),
            breweryType = dto.breweryType,
            phone = dto.phone,
            tagList = dto.tagList,
            websiteUrl = dto.websiteUrl,
            shortAddress = shortAddress(dto),
            longAddress = longAddress(dto),
            updatedAt = parseDate(dto.updatedAt),
            latLon = parseLatLong(dto)
        )
    }

    private fun parseDate(dateStr: String): Date {
        return try {
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US).parse(dateStr)
                ?: Date()
        } catch (e: ParseException) {
            Date()
        }
    }

    private fun shortAddress(dto: BreweryDto): Address {
        return StringBuilder().apply {
            append(dto.city)
            append(", ")
            append(dto.country)
        }.toString()
    }

    private fun longAddress(dto: BreweryDto): Address {
        return StringBuilder().apply {
            append(dto.street)
            append(", ")
            append(dto.city)
            append(", ")
            append(dto.state)
            append(", ")
            append(dto.country)
            append(" ")
            append(dto.postalCode)
        }.toString()
    }

    private fun parseLatLong(dto: BreweryDto): Pair<Latitude, Longitude>? {
        val lat = dto.latitude?.toFloatOrNull()
        val lon = dto.longitude?.toFloatOrNull()
        return if (lat != null && lon != null) Pair(lat, lon) else null
    }

    private fun getRandomImageUrl(): String {
        return BREWERY_IMAGE_URL[Random.nextInt(BREWERY_IMAGE_URL.lastIndex)]
    }

    companion object {
        private val BREWERY_IMAGE_URL = listOf(
            "https://p2d7x8x2.stackpathcdn.com/wordpress/wp-content/uploads/2019/10/beer-masters-featured.jpg",
            "http://www.quantumbrewingcompany.co.uk/wp-content/uploads/2019/04/965210.jpg",
            "http://visitfloydva.com/wp-content/uploads/2014/06/Chateau-Morrisette-cellar.main-view.F-from-LM1.jpg",
            "http://ourlifeinthed.com/wp-content/uploads/2015/12/Eco-friendly-breweries-cover-photo-e1435500655842.jpg",
            "https://www.butcherandcatch.co.uk/wp-content/uploads/2018/03/1a.jpg"
        )
    }
}
