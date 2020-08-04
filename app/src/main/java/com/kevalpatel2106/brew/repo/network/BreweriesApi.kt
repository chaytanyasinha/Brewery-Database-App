package com.kevalpatel2106.brew.repo.network

import com.kevalpatel2106.brew.repo.dto.BreweryDto
import com.kevalpatel2106.brew.repo.network.NetworkConfig.PER_PAGE_ITEMS
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit network API
 */
interface BreweriesApi {

    @GET("breweries")
    fun getBreweries(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = PER_PAGE_ITEMS,
        @Query("sort") sort: String = "+name"
    ): Single<List<BreweryDto>>

    companion object {
        fun create(retrofit: Retrofit): BreweriesApi = retrofit.create(BreweriesApi::class.java)
    }
}
