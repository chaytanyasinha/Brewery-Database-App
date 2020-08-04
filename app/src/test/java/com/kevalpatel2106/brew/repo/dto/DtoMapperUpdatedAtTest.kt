package com.kevalpatel2106.brew.repo.dto

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.brew.testUtils.generateTestBreweryDto
import com.kevalpatel2106.brew.testUtils.getKFixture
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

@RunWith(Parameterized::class)
class DtoMapperUpdatedAtTest(
    private val inputDateString: String,
    private val outputDate: Date
) {
    private val mapper = BreweryDtoMapperImpl()
    private val kFixture: KFixture = getKFixture()

    @Test
    fun `given dto with lat lon when to entity called check entity latlon`() {
        // given
        val breweryDto = generateTestBreweryDto(kFixture)
            .copy(updatedAt = inputDateString)

        // when
        val entity = mapper.toEntity(breweryDto)

        // check
        assertTrue(abs(outputDate.time - entity.updatedAt.time) < 2000)
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): ArrayList<Array<out Any?>> {
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
            return arrayListOf(
                arrayOf("2018-08-23T23:24:11.758Z", sdf.parse("2018-08-23T23:24:11.758Z")),
                arrayOf("Not parcelable string", Date())
            )
        }
    }
}
