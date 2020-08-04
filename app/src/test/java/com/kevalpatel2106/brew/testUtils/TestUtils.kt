package com.kevalpatel2106.brew.testUtils

import com.flextrade.kfixture.KFixture
import com.flextrade.kfixture.customisation.IgnoreDefaultArgsConstructorCustomisation
import com.kevalpatel2106.brew.entity.Brewery
import com.kevalpatel2106.brew.repo.dto.BreweryDto
import java.util.*

fun generateTestBrewery(kFixture: KFixture) = Brewery(
    id = kFixture(),
    breweryType = kFixture(),
    phone = kFixture(),
    tagList = listOf(kFixture(), kFixture()),
    websiteUrl = kFixture(),
    longAddress = kFixture(),
    imageUrl = kFixture(),
    name = kFixture(),
    shortAddress = kFixture(),
    updatedAt = Date(),
    latLon = Pair(kFixture.range(0f..180f), kFixture.range(0f..90f))
)

fun generateTestBreweryDto(kFixture: KFixture) = BreweryDto(
    id = kFixture(),
    breweryType = kFixture(),
    phone = kFixture(),
    tagList = listOf(kFixture(), kFixture()),
    websiteUrl = kFixture(),
    name = kFixture(),
    updatedAt = "2018-08-23T23:24:11.758Z",
    street = kFixture(),
    state = kFixture(),
    postalCode = kFixture(),
    country = kFixture(),
    city = kFixture(),
    latitude = kFixture.range(0f..180f).toString(),
    longitude = kFixture.range(0f..90f).toString()
)

fun getKFixture() = KFixture { add(IgnoreDefaultArgsConstructorCustomisation()) }
