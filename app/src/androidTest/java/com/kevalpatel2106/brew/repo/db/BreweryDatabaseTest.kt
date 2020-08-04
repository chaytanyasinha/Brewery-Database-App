package com.kevalpatel2106.brew.repo.db

import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import com.kevalpatel2106.brew.generateTestBreweryDto
import com.kevalpatel2106.brew.getKFixture
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BreweryDatabaseTest {
    private lateinit var dao: BreweryDao
    private lateinit var db: BreweryDatabase
    private val kFixture = getKFixture()

    @Before
    fun createDb() {
        db = BreweryDatabase.create(
            ApplicationProvider.getApplicationContext(),
            inMemory = true
        )
        dao = db.getBreweryDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun givenListOfDtoToInsert_whenInsertAllCalledOnBlankDb_checkDatabseCount() {
        // given
        val dtos = listOf(generateTestBreweryDto(kFixture), generateTestBreweryDto(kFixture))

        // when
        dao.insertAll(dtos)

        // check
        assertEquals(dtos.size, dao.getCount().blockingGet())
    }

    @Test
    fun givenListOfDtoInserted_whenInsertingAgain_checkConflictStretegyIsReplace() {
        // given
        val dtos = listOf(generateTestBreweryDto(kFixture), generateTestBreweryDto(kFixture))
        dao.insertAll(dtos)

        // when
        dao.insertAll(dtos)

        // check
        assertEquals(dtos.size, dao.getCount().blockingGet())
    }

    @Test
    fun givenDbIsNotEmpty_whenDeleteAllCalled_checkTableIsEmpty() {
        // given
        val dtos = listOf(generateTestBreweryDto(kFixture), generateTestBreweryDto(kFixture))
        dao.insertAll(dtos)

        // when
        dao.deleteAll()

        // check
        assertEquals(0, dao.getCount().blockingGet())
    }

    @Test
    fun givenDbIsNotEmpty_whenInsertAllAfterDeleteAll_checkTable() {
        // given
        val dtosOld = listOf(generateTestBreweryDto(kFixture), generateTestBreweryDto(kFixture))
        dao.insertAll(dtosOld)

        // when
        val dtosNew = listOf(generateTestBreweryDto(kFixture))
        dao.insertAllAfterDeleteAll(dtosNew)

        // check
        assertEquals(dtosNew.size, dao.getCount().blockingGet())
    }
}
