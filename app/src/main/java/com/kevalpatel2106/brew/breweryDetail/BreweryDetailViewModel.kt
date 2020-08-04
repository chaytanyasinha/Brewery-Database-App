package com.kevalpatel2106.brew.breweryDetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kevalpatel2106.brew.breweryDetail.BreweryDetailSingleEvent.CloseDetail
import com.kevalpatel2106.brew.breweryDetail.BreweryDetailSingleEvent.OpenLocationOnMap
import com.kevalpatel2106.brew.entity.Brewery
import com.kevalpatel2106.brew.utils.SingleLiveData
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * [AndroidViewModel] for [BreweryDetailFragment].
 */
class BreweryDetailViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {
    private var breweryInfo: Brewery? = null
    private var compositeDisposable = CompositeDisposable()

    private var _detailViewState = MutableLiveData<BreweryDetailViewState>()
    val detailViewState: LiveData<BreweryDetailViewState> = _detailViewState

    private var _singleViewEvent = SingleLiveData<BreweryDetailSingleEvent>()
    val singleViewEvent: LiveData<BreweryDetailSingleEvent> = _singleViewEvent

    /**
     * Set [Brewery] detail to show.
     */
    fun onBreweryDetailSet(brewery: Brewery) {
        breweryInfo = brewery
        _detailViewState.value = BreweryDetailViewState(
            name = brewery.name,
            imageUrl = brewery.imageUrl,
            longAddress = brewery.longAddress,
            websiteUrl = brewery.websiteUrl,
            typeToShow = brewery.breweryType.capitalize(),
            phone = brewery.phone,
            mapsImageUrl = GoogleMapsUseCase.createStaticMapImage(brewery.latLon)
        )
    }

    /**
     * Address card clicked.
     */
    fun onAddressCardClicked() {
        breweryInfo?.latLon?.let { (lat, lon) ->
            _singleViewEvent.value = OpenLocationOnMap(lat, lon)
        }
    }

    /**
     * Close button or back clicked.
     */
    fun onCloseButtonClicked() {
        _singleViewEvent.value = CloseDetail
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
