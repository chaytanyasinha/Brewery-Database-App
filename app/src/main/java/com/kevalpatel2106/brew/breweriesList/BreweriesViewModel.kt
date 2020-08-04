package com.kevalpatel2106.brew.breweriesList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import com.kevalpatel2106.brew.R
import com.kevalpatel2106.brew.breweriesList.BreweryListSingleEvents.RefreshAdapter
import com.kevalpatel2106.brew.breweriesList.BreweryListSingleEvents.ShowUserMessage
import com.kevalpatel2106.brew.entity.Brewery
import com.kevalpatel2106.brew.repo.BreweryRepository
import com.kevalpatel2106.brew.utils.SingleLiveData
import com.kevalpatel2106.brew.utils.addTo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * [ViewModel] for [BreweriesListFragment]
 */
class BreweriesViewModel @Inject constructor(
    private val breweryRepository: BreweryRepository
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _pageData = MediatorLiveData<PagingData<Brewery>>()
    val pageData: LiveData<PagingData<Brewery>> = _pageData

    private val _singleEvent = SingleLiveData<BreweryListSingleEvents>()
    val singleEvent: LiveData<BreweryListSingleEvents> = _singleEvent

    init {
        breweryRepository.loadCachedBreweryPagedList()
            .cachedIn(viewModelScope)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ _pageData.value = it }, { it.printStackTrace() })
            .addTo(compositeDisposable)
    }

    fun onForceRefresh() {
        breweryRepository.invalidateCache()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _singleEvent.value = RefreshAdapter
            }, {
                _singleEvent.value = ShowUserMessage(R.string.error_refreshing_list)
            })
            .addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
