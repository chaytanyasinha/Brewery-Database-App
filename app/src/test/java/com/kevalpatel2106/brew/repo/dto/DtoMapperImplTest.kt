package com.kevalpatel2106.brew.repo.dto

import com.kevalpatel2106.brew.testUtils.generateTestBreweryDto
import com.kevalpatel2106.brew.testUtils.getKFixture
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DtoMapperImplTest {
    private val kFixture = getKFixture()
    private val mapper = BreweryDtoMapperImpl()

    @Test
    fun `given dto with id when to entity called check brewery id`() {
        // given
        val breweryDto = generateTestBreweryDto(kFixture)

        // when
        val entity = mapper.toEntity(breweryDto)

        // check
        assertEquals(breweryDto.id, entity.id)
    }

    @Test
    fun `given dto with name when to entity called check brewery name`() {
        // given
        val breweryDto = generateTestBreweryDto(kFixture)

        // when
        val entity = mapper.toEntity(breweryDto)

        // check
        assertEquals(breweryDto.name, entity.name)
    }

    @Test
    fun `given dto when to entity called check brewery image url added`() {
        // given
        val breweryDto = generateTestBreweryDto(kFixture)

        // when
        val entity = mapper.toEntity(breweryDto)

        // check
        assertTrue(entity.imageUrl.contains("https://"))
    }

    @Test
    fun `given dto with type when to entity called check brewery type`() {
        // given
        val breweryDto = generateTestBreweryDto(kFixture)

        // when
        val entity = mapper.toEntity(breweryDto)

        // check
        assertEquals(breweryDto.breweryType, entity.breweryType)
    }

    @Test
    fun `given dto with phone when to entity called check brewery phone`() {
        // given
        val breweryDto = generateTestBreweryDto(kFixture)

        // when
        val entity = mapper.toEntity(breweryDto)

        // check
        assertEquals(breweryDto.phone, entity.phone)
    }

    @Test
    fun `given dto with tag list when to entity called check brewery tag list`() {
        // given
        val breweryDto = generateTestBreweryDto(kFixture)

        // when
        val entity = mapper.toEntity(breweryDto)

        // check
        assertEquals(breweryDto.tagList.size, entity.tagList.size)
    }

    @Test
    fun `given dto with website url when to entity called check brewery website url`() {
        // given
        val breweryDto = generateTestBreweryDto(kFixture)

        // when
        val entity = mapper.toEntity(breweryDto)

        // check
        assertEquals(breweryDto.websiteUrl, entity.websiteUrl)
    }

    @Test
    fun `given dto with address field when to entity called check brewery long address`() {
        // given
        val breweryDto = generateTestBreweryDto(kFixture)

        // when
        val entity = mapper.toEntity(breweryDto)

        // check
        assertTrue(entity.longAddress.contains(breweryDto.street.toString()))
        assertTrue(entity.longAddress.contains(breweryDto.city.toString()))
        assertTrue(entity.longAddress.contains(breweryDto.state.toString()))
        assertTrue(entity.longAddress.contains(breweryDto.country))
        assertTrue(entity.longAddress.contains(breweryDto.postalCode.toString()))
    }

    @Test
    fun `given dto with address field when to entity called check brewery short address`() {
        // given
        val breweryDto = generateTestBreweryDto(kFixture)

        // when
        val entity = mapper.toEntity(breweryDto)

        // check
        assertTrue(entity.shortAddress.contains(breweryDto.city.toString()))
        assertTrue(entity.shortAddress.contains(breweryDto.country))
    }
}
