package com.kevalpatel2106.brew.repo

import androidx.paging.*
import com.kevalpatel2106.brew.repo.db.BreweryDao
import com.kevalpatel2106.brew.repo.dto.BreweryDto
import com.kevalpatel2106.brew.repo.network.BreweriesApi
import com.kevalpatel2106.brew.repo.network.NetworkConfig
import com.kevalpatel2106.brew.testUtils.RxSchedulersOverrideRule
import com.kevalpatel2106.brew.testUtils.generateTestBreweryDto
import com.kevalpatel2106.brew.testUtils.getKFixture
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
@ExperimentalPagingApi
class BreweryListRemoteMediatorTest {

    @Rule
    @JvmField
    val rule = RxSchedulersOverrideRule()

    @Mock
    lateinit var breweryApi: BreweriesApi

    @Mock
    lateinit var dao: BreweryDao

    private lateinit var fakePagingState: PagingState<Int, BreweryDto>
    private val kFixture = getKFixture()
    private lateinit var mediator: BreweryListRemoteMediator

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
        fakePagingState = PagingState(listOf(), null, PagingConfig(pageSize = 0), 0)
        mediator = BreweryListRemoteMediator(breweryApi, dao)
    }

    @Test
    fun `given load type is prepend when load single called check mediator result is success`() {
        // given
        val loadType = LoadType.PREPEND

        // when
        val testSubscriber = mediator.loadSingle(loadType, fakePagingState).test()

        // check
        testSubscriber.assertNoTimeout()
            .assertNoErrors()
            .assertComplete()
            .assertValueAt(0) { it is RemoteMediator.MediatorResult.Success }
            .assertValueAt(0) { (it as RemoteMediator.MediatorResult.Success).endOfPaginationReached }
    }

    @Test
    fun `given no item cached and type refresh when load single called check first page loaded from network`() {
        // given
        val loadType = LoadType.REFRESH
        whenever(dao.getCount())
            .thenReturn(Single.just(kFixture.range(0 until NetworkConfig.PER_PAGE_ITEMS)))
        whenever(breweryApi.getBreweries(anyInt(), any(), any())).thenReturn(Single.just(listOf()))

        // when
        val testSubscriber = mediator.loadSingle(loadType, fakePagingState).test()
        testSubscriber.await()

        // check
        val pageNumberCaptor = argumentCaptor<Int>()
        verify(breweryApi).getBreweries(pageNumberCaptor.capture(), any(), any())
        assertEquals(1, pageNumberCaptor.lastValue)
    }

    @Test
    fun `given first page cached and type refresh when load single called check result success and not loaded from network`() {
        // given
        val loadType = LoadType.REFRESH
        whenever(dao.getCount())
            .thenReturn(Single.just(kFixture.range(NetworkConfig.PER_PAGE_ITEMS..100)))
        whenever(breweryApi.getBreweries(anyInt(), any(), any())).thenReturn(Single.just(listOf()))

        // when
        val testSubscriber = mediator.loadSingle(loadType, fakePagingState).test()
        testSubscriber.await()

        // check
        verify(breweryApi, never()).getBreweries(anyInt(), any(), any())
        testSubscriber.assertNoTimeout()
            .assertComplete()
            .assertNoErrors()
            .assertValueAt(0) { it is RemoteMediator.MediatorResult.Success }
    }

    @Test
    fun `given type append when load single called check result success and loaded from network`() {
        // given
        val loadType = LoadType.APPEND
        whenever(dao.getCount()).thenReturn(Single.just(kFixture()))
        whenever(breweryApi.getBreweries(anyInt(), any(), any())).thenReturn(Single.just(listOf()))

        // when
        val testSubscriber = mediator.loadSingle(loadType, fakePagingState).test()
        testSubscriber.await()

        // check
        verify(breweryApi).getBreweries(anyInt(), any(), any())
        testSubscriber.assertNoTimeout()
            .assertComplete()
            .assertNoErrors()
            .assertValueAt(0) { it is RemoteMediator.MediatorResult.Success }
    }

    @Test
    fun `given zero result from network when load single called check end of page reached`() {
        // given
        val loadType = LoadType.APPEND
        whenever(dao.getCount()).thenReturn(Single.just(kFixture()))
        whenever(breweryApi.getBreweries(anyInt(), any(), any())).thenReturn(Single.just(listOf()))

        // when
        val testSubscriber = mediator.loadSingle(loadType, fakePagingState).test()
        testSubscriber.await()

        // check
        testSubscriber.assertNoTimeout()
            .assertComplete()
            .assertNoErrors()
            .assertValueAt(0) { it is RemoteMediator.MediatorResult.Success }
            .assertValueAt(0) { (it as RemoteMediator.MediatorResult.Success).endOfPaginationReached }
    }

    @Test
    fun `given not-empty result from network when load single called check end of page reached si false`() {
        // given
        val loadType = LoadType.APPEND
        val networkResult =
            listOf(generateTestBreweryDto(kFixture), generateTestBreweryDto(kFixture))
        whenever(dao.getCount()).thenReturn(Single.just(kFixture()))
        whenever(breweryApi.getBreweries(anyInt(), any(), any()))
            .thenReturn(Single.just(networkResult))

        // when
        val testSubscriber = mediator.loadSingle(loadType, fakePagingState).test()
        testSubscriber.await()

        // check
        testSubscriber.assertNoTimeout()
            .assertComplete()
            .assertNoErrors()
            .assertValueAt(0) { it is RemoteMediator.MediatorResult.Success }
            .assertValueAt(0) { !(it as RemoteMediator.MediatorResult.Success).endOfPaginationReached }
    }

    @Test
    fun `given not-empty result from network when load single called check result cached`() {
        // given
        val loadType = LoadType.APPEND
        val networkResult =
            listOf(generateTestBreweryDto(kFixture), generateTestBreweryDto(kFixture))
        whenever(dao.getCount()).thenReturn(Single.just(kFixture()))
        whenever(breweryApi.getBreweries(anyInt(), any(), any()))
            .thenReturn(Single.just(networkResult))

        // when
        val testSubscriber = mediator.loadSingle(loadType, fakePagingState).test()
        testSubscriber.await()

        // check
        val listCaptor = argumentCaptor<List<BreweryDto>>()
        verify(dao).insertAll(listCaptor.capture())
        assertEquals(networkResult.size, listCaptor.lastValue.size)
        assertEquals(networkResult[0], listCaptor.lastValue[0])
        assertEquals(networkResult[1], listCaptor.lastValue[1])
    }

    @Test
    fun `given error getting cached page count when load single called check result type is error`() {
        // given
        val loadType = LoadType.APPEND
        val error = Throwable(kFixture<String>())
        whenever(dao.getCount()).thenReturn(Single.error(error))
        whenever(breweryApi.getBreweries(anyInt(), any(), any())).thenReturn(Single.just(listOf()))

        // when
        val testSubscriber = mediator.loadSingle(loadType, fakePagingState).test()
        testSubscriber.await()

        // check
        testSubscriber.assertNoTimeout()
            .assertComplete()
            .assertNoErrors()
            .assertValueAt(0) { it is RemoteMediator.MediatorResult.Error }
            .assertValueAt(0) { (it as RemoteMediator.MediatorResult.Error).throwable == error }
    }

    @Test
    fun `given error getting page from network when load single called check result type is error`() {
        // given
        val loadType = LoadType.APPEND
        val error = Throwable(kFixture<String>())
        whenever(dao.getCount()).thenReturn(Single.just(kFixture()))
        whenever(breweryApi.getBreweries(anyInt(), any(), any())).thenReturn(Single.error(error))

        // when
        val testSubscriber = mediator.loadSingle(loadType, fakePagingState).test()
        testSubscriber.await()

        // check
        testSubscriber.assertNoTimeout()
            .assertComplete()
            .assertNoErrors()
            .assertValueAt(0) { it is RemoteMediator.MediatorResult.Error }
            .assertValueAt(0) { (it as RemoteMediator.MediatorResult.Error).throwable == error }
    }
}
