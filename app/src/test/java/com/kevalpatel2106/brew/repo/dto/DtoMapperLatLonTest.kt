package com.kevalpatel2106.brew.repo.dto

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.brew.entity.Latitude
import com.kevalpatel2106.brew.entity.Longitude
import com.kevalpatel2106.brew.testUtils.generateTestBrewery
import com.kevalpatel2106.brew.testUtils.generateTestBreweryDto
import com.kevalpatel2106.brew.testUtils.getKFixture
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.util.*

@RunWith(Parameterized::class)
class DtoMapperLatLonTest(
    private val inputLat: String?,
    private val inputLon: String?,
    private val outputLatLon: Pair<Latitude, Longitude>?
) {
    private val mapper = BreweryDtoMapperImpl()
    private val kFixture: KFixture = getKFixture()

    @Test
    fun `given dto with lat lon when to entity called check entity latlon`() {
        // given
        val breweryDto = generateTestBreweryDto(kFixture)
            .copy(latitude = inputLat, longitude = inputLon)

        // when
        val entity = mapper.toEntity(breweryDto)

        // check
        assertEquals(outputLatLon?.first, entity.latLon?.first)
        assertEquals(outputLatLon?.second, entity.latLon?.second)
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): ArrayList<Array<out Any?>> {
            return arrayListOf(
                arrayOf("42.653", "42.653", Pair(42.653F, 42.653F)),
                arrayOf("42.653", null, null),
                arrayOf(null, "42.653", null),
                arrayOf("ABCD", "ABCD", null)
            )
        }
    }
}
