package com.kevalpatel2106.brew.repo.db

import com.flextrade.kfixture.KFixture
import com.flextrade.kfixture.customisation.IgnoreDefaultArgsConstructorCustomisation
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DbTypeFloatingListConversationTest {
    private val kFixture = KFixture { add(IgnoreDefaultArgsConstructorCustomisation()) }

    @Test
    fun checkToFloatListFromString() {
        val stringList = listOf<String>(kFixture(), kFixture(), kFixture(), kFixture())
        val commaString = stringList.joinToString(",")

        DbTypeConverter.toStringList(commaString).forEachIndexed { index, value ->
            assertEquals(stringList[index], value)
        }
    }

    @Test
    fun checkToCommaSeparatedListFromFloatList() {
        val stringList = listOf<String>(kFixture(), kFixture(), kFixture(), kFixture())
        val commaString = stringList.joinToString(",")

        assertEquals(commaString, DbTypeConverter.toCommaSeparatedList(stringList))
    }

}
