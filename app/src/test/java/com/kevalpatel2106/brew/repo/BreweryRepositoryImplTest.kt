package com.kevalpatel2106.brew.repo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kevalpatel2106.brew.repo.db.BreweryDao
import com.kevalpatel2106.brew.repo.dto.BreweryDtoMapper
import com.kevalpatel2106.brew.repo.network.BreweriesApi
import com.kevalpatel2106.brew.testUtils.RxSchedulersOverrideRule
import com.kevalpatel2106.brew.testUtils.getKFixture
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class BreweryRepositoryImplTest {

    @Rule
    @JvmField
    val rule = RxSchedulersOverrideRule()

    @Mock
    private lateinit var mapper: BreweryDtoMapper

    @Mock
    private lateinit var breweryDao: BreweryDao

    @Mock
    private lateinit var breweriesApi: BreweriesApi

    private lateinit var repository: BreweryRepositoryImpl

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
        repository = BreweryRepositoryImpl(
            breweryDao = breweryDao,
            mapper = mapper,
            remoteMediator = BreweryListRemoteMediator(breweriesApi, breweryDao)
        )
    }

    @Test
    fun `when invalidate cache check database clears`() {
        // when
        val testObserver = repository.invalidateCache().test()
        testObserver.await()

        // check
        testObserver.assertComplete().assertNoTimeout()
        verify(breweryDao).deleteAll()
    }
}
