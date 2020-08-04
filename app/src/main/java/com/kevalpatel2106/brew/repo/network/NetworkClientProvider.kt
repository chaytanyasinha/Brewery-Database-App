package com.kevalpatel2106.brew.repo.network

import com.kevalpatel2106.brew.BuildConfig
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * [Retrofit] client provider for given [baseUrl].
 */
class NetworkClientProvider(private val baseUrl: String) {

    private val okHttpClient = OkHttpClient.Builder()
        .apply {
            readTimeout(1, TimeUnit.MINUTES)
            connectTimeout(1, TimeUnit.MINUTES)
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor()
                    .apply { level = HttpLoggingInterceptor.Level.BODY })
            }
        }
        .build()

    private val moshi = Moshi.Builder().build()

    fun getRetrofitClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }
}
