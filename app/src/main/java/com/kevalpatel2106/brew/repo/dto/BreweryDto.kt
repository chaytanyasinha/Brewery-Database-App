package com.kevalpatel2106.brew.repo.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kevalpatel2106.brew.repo.db.BreweryTableInfo
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * DTO for [Brewery]
 */
@Entity(tableName = BreweryTableInfo.TABLE_NAME)
@JsonClass(generateAdapter = true)
data class BreweryDto(

    @Json(name = "id")
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = BreweryTableInfo.ID)
    val id: Int,

    @ColumnInfo(name = BreweryTableInfo.NAME)
    @Json(name = "name")
    val name: String,

    @ColumnInfo(name = BreweryTableInfo.STREET)
    @Json(name = "street")
    val street: String?,

    @ColumnInfo(name = BreweryTableInfo.CITY)
    @Json(name = "city")
    val city: String?,

    @ColumnInfo(name = BreweryTableInfo.STATE)
    @Json(name = "state")
    val state: String?,

    @ColumnInfo(name = BreweryTableInfo.COUNTRY)
    @Json(name = "country")
    val country: String,

    @ColumnInfo(name = BreweryTableInfo.POSTAL_CODE)
    @Json(name = "postal_code")
    val postalCode: String?,

    @ColumnInfo(name = BreweryTableInfo.PHONE)
    @Json(name = "phone")
    val phone: String? = null,

    @ColumnInfo(name = BreweryTableInfo.BREWERY_TYPE)
    @Json(name = "brewery_type")
    val breweryType: String,

    @ColumnInfo(name = BreweryTableInfo.WEBSITE_URL)
    @Json(name = "website_url")
    val websiteUrl: String? = null,

    @ColumnInfo(name = BreweryTableInfo.UPDATED_AT)
    @Json(name = "updated_at")
    val updatedAt: String,

    @ColumnInfo(name = BreweryTableInfo.TAGS)
    @Json(name = "tag_list")
    val tagList: List<String>,

    @ColumnInfo(name = BreweryTableInfo.LATITUDE)
    @Json(name = "latitude")
    val latitude: String?,

    @ColumnInfo(name = BreweryTableInfo.LONGITUDE)
    @Json(name = "longitude")
    val longitude: String?
)
