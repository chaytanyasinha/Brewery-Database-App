package com.kevalpatel2106.brew.breweryDetail

import android.content.Intent
import android.net.Uri
import com.kevalpatel2106.brew.breweryDetail.GoogleMapsUseCase.GOOGLE_MAPS_PACKAGE_NAME
import com.kevalpatel2106.brew.entity.Latitude
import com.kevalpatel2106.brew.entity.Longitude
import com.kevalpatel2106.brew.testUtils.getKFixture
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CreateMapsIntentTest {
    private val kFixture = getKFixture()

    @Test
    fun `given latlon when creating map intent check intent action`() {
        // given
        val latitude = kFixture<Latitude>()
        val longitude = kFixture<Longitude>()

        // when
        val mapIntent = GoogleMapsUseCase.getGoogleMapsIntent(latitude, longitude)

        // check
        assertEquals(Intent.ACTION_VIEW, mapIntent.action)
    }

    @Test
    fun `given latlon when creating map intent check intent data`() {
        // given
        val latitude = kFixture<Latitude>()
        val longitude = kFixture<Longitude>()

        // when
        val mapIntent = GoogleMapsUseCase.getGoogleMapsIntent(latitude, longitude)

        // check
        assertEquals( Uri.parse("geo:$latitude,$longitude"), mapIntent.data)
    }

    @Test
    fun `given latlon when creating map intent check package name`() {
        // given
        val latitude = kFixture<Latitude>()
        val longitude = kFixture<Longitude>()

        // when
        val mapIntent = GoogleMapsUseCase.getGoogleMapsIntent(latitude, longitude)

        // check
        assertEquals(GOOGLE_MAPS_PACKAGE_NAME, mapIntent.`package`)
    }
}
