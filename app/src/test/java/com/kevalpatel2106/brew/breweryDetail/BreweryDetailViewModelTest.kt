package com.kevalpatel2106.brew.breweryDetail

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kevalpatel2106.brew.breweryDetail.BreweryDetailSingleEvent.CloseDetail
import com.kevalpatel2106.brew.breweryDetail.BreweryDetailSingleEvent.OpenLocationOnMap
import com.kevalpatel2106.brew.testUtils.generateTestBrewery
import com.kevalpatel2106.brew.testUtils.getKFixture
import com.kevalpatel2106.brew.testUtils.getOrAwaitValue
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class BreweryDetailViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var application: Application

    private val kFixture = getKFixture()
    private lateinit var viewModel: BreweryDetailViewModel

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
        viewModel = BreweryDetailViewModel(application)
    }

    @Test
    fun `given brewery detail when brewery detail set check name detail view state`() {
        // given
        val brewery = generateTestBrewery(kFixture)

        // when
        viewModel.onBreweryDetailSet(brewery)

        // check
        assertEquals(brewery.name, viewModel.detailViewState.getOrAwaitValue().name)
    }

    @Test
    fun `given brewery detail when brewery detail set check image url detail view state`() {
        // given
        val brewery = generateTestBrewery(kFixture)

        // when
        viewModel.onBreweryDetailSet(brewery)

        // check
        assertEquals(brewery.imageUrl, viewModel.detailViewState.getOrAwaitValue().imageUrl)
    }

    @Test
    fun `given brewery detail when brewery detail set check address detail view state`() {
        // given
        val brewery = generateTestBrewery(kFixture)

        // when
        viewModel.onBreweryDetailSet(brewery)

        // check
        assertEquals(brewery.longAddress, viewModel.detailViewState.getOrAwaitValue().longAddress)
    }

    @Test
    fun `given brewery detail when brewery detail set check website detail view state`() {
        // given
        val brewery = generateTestBrewery(kFixture)

        // when
        viewModel.onBreweryDetailSet(brewery)

        // check
        assertEquals(brewery.websiteUrl, viewModel.detailViewState.getOrAwaitValue().websiteUrl)
    }

    @Test
    fun `given brewery detail when brewery detail set check brewery type detail view state`() {
        // given
        val brewery = generateTestBrewery(kFixture)

        // when
        viewModel.onBreweryDetailSet(brewery)

        // check
        assertEquals(
            brewery.breweryType.capitalize(),
            viewModel.detailViewState.getOrAwaitValue().typeToShow
        )
    }

    @Test
    fun `given brewery detail when brewery detail set check phone detail view state`() {
        // given
        val brewery = generateTestBrewery(kFixture)

        // when
        viewModel.onBreweryDetailSet(brewery)

        // check
        assertEquals(brewery.phone, viewModel.detailViewState.getOrAwaitValue().phone)
    }

    @Test
    fun `given brewery detail when brewery detail set check map url detail view state`() {
        // given
        val brewery = generateTestBrewery(kFixture)

        // when
        viewModel.onBreweryDetailSet(brewery)

        // check
        assertNotNull(viewModel.detailViewState.getOrAwaitValue().mapsImageUrl)
        assertTrue(
            viewModel.detailViewState.getOrAwaitValue().mapsImageUrl
                ?.contains(brewery.latLon?.first.toString()) == true
        )
        assertTrue(
            viewModel.detailViewState.getOrAwaitValue().mapsImageUrl
                ?.contains(brewery.latLon?.second.toString()) == true
        )
    }

    @Test
    fun `given brewery has no lat lon when address card clicked check no event trigger`() {
        // given
        val brewery = generateTestBrewery(kFixture).copy(latLon = null)
        viewModel.onBreweryDetailSet(brewery)

        // when
        viewModel.onAddressCardClicked()

        // check
        assertNull(viewModel.singleViewEvent.value)
    }

    @Test
    fun `given brewery has lat lon when address card clicked check google map opens`() {
        // given
        val brewery = generateTestBrewery(kFixture)
        viewModel.onBreweryDetailSet(brewery)

        // when
        viewModel.onAddressCardClicked()

        // check
        assertEquals(
            OpenLocationOnMap(brewery.latLon!!.first, brewery.latLon!!.second),
            viewModel.singleViewEvent.getOrAwaitValue()
        )
    }

    @Test
    fun `when close button clicked check detail screen closes`(){
        // when
        viewModel.onCloseButtonClicked()

        // check
        assertEquals(CloseDetail, viewModel.singleViewEvent.getOrAwaitValue())
    }
}
