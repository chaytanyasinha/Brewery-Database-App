package com.kevalpatel2106.brew.breweryDetail

import com.kevalpatel2106.brew.BuildConfig
import com.kevalpatel2106.brew.entity.Latitude
import com.kevalpatel2106.brew.entity.Longitude
import com.kevalpatel2106.brew.testUtils.getKFixture
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CreateMapsImageUrlTest {
    private val kFixture = getKFixture()

    @Test
    fun `given latlon null when creating map image url check url is null`() {
        // given
        val latlon: Pair<Latitude, Longitude>? = null

        // when
        val mapUrl = GoogleMapsUseCase.createStaticMapImage(latlon)

        // check
        assertNull(mapUrl)
    }

    @Test
    fun `given latlon when creating map image url check url contains given lat lon`() {
        // given
        val latitude = kFixture<Latitude>()
        val longitude = kFixture<Longitude>()
        val latlon: Pair<Latitude, Longitude>? = Pair(latitude, longitude)

        // when
        val mapUrl = GoogleMapsUseCase.createStaticMapImage(latlon)

        // check
        assertTrue(mapUrl?.contains(latitude.toString()) == true)
        assertTrue(mapUrl?.contains(longitude.toString()) == true)
    }

    @Test
    fun `given latlon when creating map image url check url contains api key`() {
        // given
        val latitude = kFixture<Latitude>()
        val longitude = kFixture<Longitude>()
        val latlon: Pair<Latitude, Longitude>? = Pair(latitude, longitude)

        // when
        val mapUrl = GoogleMapsUseCase.createStaticMapImage(latlon)

        // check
        assertTrue(mapUrl?.contains(BuildConfig.GOOGLE_API_KEY) == true)
    }
}
