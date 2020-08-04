package com.kevalpatel2106.brew.breweriesList

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kevalpatel2106.brew.R
import com.kevalpatel2106.brew.breweriesList.BreweryListSingleEvents.RefreshAdapter
import com.kevalpatel2106.brew.breweriesList.BreweryListSingleEvents.ShowUserMessage
import com.kevalpatel2106.brew.repo.BreweryRepository
import com.kevalpatel2106.brew.testUtils.RxSchedulersOverrideRule
import com.kevalpatel2106.brew.testUtils.getKFixture
import com.kevalpatel2106.brew.testUtils.getOrAwaitValue
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import io.reactivex.Flowable
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class BreweriesViewModelTest {

    @Rule
    @JvmField
    val rule1 = RxSchedulersOverrideRule()

    @Rule
    @JvmField
    val rule2 = InstantTaskExecutorRule()

    @Mock
    lateinit var repository: BreweryRepository

    private val kFixture = getKFixture()
    private lateinit var model: BreweriesViewModel

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
        whenever(repository.loadCachedBreweryPagedList()).thenReturn(Flowable.never())
        model = BreweriesViewModel(repository)
    }

    @Test
    fun `given invalidate cache success when force refresh called check refresh adapter called`() {
        // given
        whenever(repository.invalidateCache()).thenReturn(Completable.complete())

        // when
        model.onForceRefresh()

        // check
        verify(repository).invalidateCache()
        assertEquals(RefreshAdapter, model.singleEvent.getOrAwaitValue())
    }

    @Test
    fun `given invalidate cache error when force refresh called check refresh adapter called`() {
        // given
        val error = Throwable(kFixture<String>())
        whenever(repository.invalidateCache()).thenReturn(Completable.error(error))

        // when
        model.onForceRefresh()

        // check
        assertEquals(
            ShowUserMessage(R.string.error_refreshing_list),
            model.singleEvent.getOrAwaitValue()
        )
    }
}
