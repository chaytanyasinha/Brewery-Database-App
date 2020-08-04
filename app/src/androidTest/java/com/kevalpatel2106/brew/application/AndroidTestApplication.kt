package com.kevalpatel2106.brew.application

import com.kevalpatel2106.brew.di.AppComponent
import com.kevalpatel2106.brew.di.AppModule
import com.kevalpatel2106.brew.di.DaggerAppComponent
import com.kevalpatel2106.brew.di.RepositoryModule
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import java.io.IOException
import java.net.HttpURLConnection

class AndroidTestApplication : BreweryApplication() {
    private val apiResponse = """
    [
        {
            "id": 2,
            "name": "Avondale Brewing Co",
            "brewery_type": "micro",
            "street": "201 41st St S",
            "city": "Birmingham",
            "state": "Alabama",
            "postal_code": "35222-1932",
            "country": "United States",
            "longitude": "-86.774322",
            "latitude": "33.524521",
            "phone": "2057775456",
            "website_url": "http://www.avondalebrewing.com",
            "updated_at": "2018-08-23T23:19:57.825Z",
            "tag_list": []
        },
        {
            "id": 44,
            "name": "Trim Tab Brewing",
            "brewery_type": "micro",
            "street": "2721 5th Ave S",
            "city": "Birmingham",
            "state": "Alabama",
            "postal_code": "35233-3401",
            "country": "United States",
            "longitude": "-86.7914000624146",
            "latitude": "33.5128492349817",
            "phone": "2057030536",
            "website_url": "http://www.trimtabbrewing.com",
            "updated_at": "2018-08-23T23:20:31.423Z",
            "tag_list": []
        },
        {
            "id": 46,
            "name": "Yellowhammer Brewery",
            "brewery_type": "micro",
            "street": "2600 Clinton Ave W",
            "city": "Huntsville",
            "state": "Alabama",
            "postal_code": "35805-3046",
            "country": "United States",
            "longitude": "-86.5932014",
            "latitude": "34.7277523",
            "phone": "2569755950",
            "website_url": "http://www.yellowhammerbrewery.com",
            "updated_at": "2018-08-23T23:20:33.102Z",
            "tag_list": []
        },
        {
            "id": 55,
            "name": "Bearpaw River Brewing Co",
            "brewery_type": "micro",
            "street": "4605 E Palmer Wasilla Hwy",
            "city": "Wasilla",
            "state": "Alaska",
            "postal_code": "99654-7679",
            "country": "United States",
            "longitude": "-149.4127103",
            "latitude": "61.5752695",
            "phone": "",
            "website_url": "http://bearpawriverbrewing.com",
            "updated_at": "2018-08-23T23:20:40.743Z",
            "tag_list": []
        },
        {
            "id": 76,
            "name": "King Street Brewing Co",
            "brewery_type": "micro",
            "street": "9050 King Street",
            "city": "Anchorage",
            "state": "Alaska",
            "postal_code": "99515",
            "country": "United States",
            "longitude": "-149.879076042937",
            "latitude": "61.1384893547315",
            "phone": "9073365464",
            "website_url": "http://www.kingstreetbrewing.com",
            "updated_at": "2018-08-23T23:20:57.179Z",
            "tag_list": []
        },
        {
            "id": 94,
            "name": "1912 Brewing",
            "brewery_type": "micro",
            "street": "2045 N Forbes Blvd Ste 105",
            "city": "Tucson",
            "state": "Arizona",
            "postal_code": "85745-1444",
            "country": "United States",
            "longitude": "-110.992750525872",
            "latitude": "32.2467372722906",
            "phone": "5202564851",
            "website_url": "http://www.1912brewing.com",
            "updated_at": "2018-08-23T23:21:11.302Z",
            "tag_list": []
        },
        {
            "id": 98,
            "name": "Bad Water Brewing",
            "brewery_type": "contract",
            "street": "4216 N Brown Ave",
            "city": "Scottsdale",
            "state": "Arizona",
            "postal_code": "85251-3914",
            "country": "United States",
            "longitude": "-111.924474347826",
            "latitude": "33.4972615652174",
            "phone": "5207459175",
            "website_url": "http://www.badwaterbrewing.com",
            "updated_at": "2018-08-23T23:21:15.169Z",
            "tag_list": []
        },
        {
            "id": 104,
            "name": "BJs Restaurant & Brewery - Chandler",
            "brewery_type": "brewpub",
            "street": "3155 W Chandler Blvd",
            "city": "Chandler",
            "state": "Arizona",
            "postal_code": "85226-5175",
            "country": "United States",
            "longitude": "-111.911126",
            "latitude": "33.3053455",
            "phone": "4809170631",
            "website_url": "http://www.bjsrestaurants.com",
            "updated_at": "2018-08-23T23:21:21.165Z",
            "tag_list": []
        }
    ]
    """.trimIndent()

    private var mockWebServer = MockWebServer()
    private lateinit var baseUrl: String

    override fun onCreate() {
        // Start the mock server
        val thread = object : Thread() {
            override fun run() {
                try {
                    mockWebServer.start()
                    baseUrl = mockWebServer.url("/").toString()
                    enqueueResponse()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        thread.start()

        // Wait for the server to start
        Thread.sleep(1000L)
        super.onCreate()
    }

    private fun enqueueResponse() {
        for (x in 0..20) {
            mockWebServer.enqueue(
                MockResponse()
                    .setHeader("Content-type", "application/json")
                    .setBody(apiResponse)
                    .setResponseCode(HttpURLConnection.HTTP_OK)
            )
        }
    }

    override fun getAppComponent(): AppComponent {
        return DaggerAppComponent.builder()
            .repositoryModule(RepositoryModule(this, true, baseUrl))
            .appModule(AppModule(this))
            .build()
    }
}
