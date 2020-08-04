package com.kevalpatel2106.brew.repo.network

import com.flextrade.kfixture.KFixture
import com.flextrade.kfixture.customisation.IgnoreDefaultArgsConstructorCustomisation
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class NetworkClientProviderTest {
    private val kFixture = KFixture { add(IgnoreDefaultArgsConstructorCustomisation()) }
    private val testBaseUrl = "http://${kFixture<String>()}/"

    @Test
    fun checkBaseUrl() {
        val retrofit = NetworkClientProvider(testBaseUrl)
            .getRetrofitClient()
        assertEquals(testBaseUrl, retrofit.baseUrl().toString())
    }

    @Test
    fun checkCallAdapterFactories() {
        val callAdapterFactories = NetworkClientProvider(testBaseUrl)
            .getRetrofitClient()
            .callAdapterFactories()
        assertNotNull(callAdapterFactories.find { it is RxJava2CallAdapterFactory })
    }

    @Test
    fun checkConverterFactory() {
        val converterFactories = NetworkClientProvider(testBaseUrl)
            .getRetrofitClient()
            .converterFactories()
        assertNotNull(converterFactories.find { it is MoshiConverterFactory })
    }
}
