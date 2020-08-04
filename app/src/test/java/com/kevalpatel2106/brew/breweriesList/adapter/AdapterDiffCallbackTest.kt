package com.kevalpatel2106.brew.breweriesList.adapter

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.brew.entity.Brewery
import com.kevalpatel2106.brew.testUtils.generateTestBrewery
import com.kevalpatel2106.brew.testUtils.getKFixture
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.util.*

@RunWith(Enclosed::class)
class AdapterDiffCallbackTest {

    @RunWith(Parameterized::class)
    internal class AreItemsTheSameTest(
        private val input1: Brewery,
        private val input2: Brewery,
        private val areItemsSame: Boolean
    ) {

        companion object {
            private val kFixture: KFixture = getKFixture()
            private val brewery = generateTestBrewery(kFixture)
            private val breweryDifferentId = brewery.copy(id = kFixture())
            private val breweryDifferentName = brewery.copy(name = kFixture())

            @JvmStatic
            @Parameterized.Parameters
            fun data(): ArrayList<Array<out Any>> {
                return arrayListOf(
                    arrayOf(brewery, breweryDifferentId, false),
                    arrayOf(brewery, breweryDifferentName, true)
                )
            }
        }

        @Test
        fun `check items are same or not`() {
            assertEquals(
                areItemsSame,
                AdapterDiffCallback.areItemsTheSame(input1, input2)
            )
        }
    }

    @RunWith(Parameterized::class)
    internal class AreContentsTheSameTest(
        private val input1: Brewery,
        private val input2: Brewery,
        private val areContentsTheSame: Boolean
    ) {

        companion object {
            private val kFixture: KFixture = getKFixture()
            private val brewery = generateTestBrewery(kFixture)
            private val breweryDifferentId = brewery.copy(id = kFixture())
            private val breweryDifferentName = brewery.copy(name = kFixture())

            @JvmStatic
            @Parameterized.Parameters
            fun data(): ArrayList<Array<out Any>> {
                return arrayListOf(
                    arrayOf(brewery, brewery, true),
                    arrayOf(brewery, breweryDifferentId, false),
                    arrayOf(brewery, breweryDifferentName, false)
                )
            }
        }

        @Test
        fun `check item content are same or not`() {
            assertEquals(
                areContentsTheSame,
                AdapterDiffCallback.areContentsTheSame(input1, input2)
            )
        }
    }
}


