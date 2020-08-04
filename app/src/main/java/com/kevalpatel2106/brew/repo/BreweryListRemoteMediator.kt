package com.kevalpatel2106.brew.repo

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.paging.rxjava2.RxRemoteMediator
import com.kevalpatel2106.brew.repo.db.BreweryDao
import com.kevalpatel2106.brew.repo.dto.BreweryDto
import com.kevalpatel2106.brew.repo.network.BreweriesApi
import com.kevalpatel2106.brew.repo.network.NetworkConfig
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import kotlin.math.floor

/**
 * [RxRemoteMediator] for the list of [BreweryDto]. This is to handle loading the page from network
 * when loaded the database cached item.
 *
 * @see [RemoteMediator]
 */
@OptIn(ExperimentalPagingApi::class)
class BreweryListRemoteMediator @Inject constructor(
    private val breweriesApi: BreweriesApi,
    private val breweryDao: BreweryDao
) : RxRemoteMediator<Int, BreweryDto>() {

    override fun loadSingle(
        loadType: LoadType,
        state: PagingState<Int, BreweryDto>
    ): Single<MediatorResult> {
        if (loadType == LoadType.PREPEND) {
            // We don't support prepend
            return Single.just(MediatorResult.Success(endOfPaginationReached = true))
        }

        return getLastCachedPageCount()
            .flatMap { page ->
                if (page > 0 && loadType == LoadType.REFRESH) {
                    // While refreshing if the data is already cached, we don't want to go over network
                    Single.just(MediatorResult.Success(endOfPaginationReached = false))
                } else {
                    breweriesApi.getBreweries(page + 1)
                        .doOnSuccess { dtos -> breweryDao.insertAll(dtos) }
                        .map { dtos -> MediatorResult.Success(dtos.isEmpty()) as MediatorResult }
                }
            }
            .subscribeOn(Schedulers.io())
            .onErrorResumeNext {
                it.printStackTrace()
                Single.just(MediatorResult.Error(it) as MediatorResult)
            }
    }

    private fun getLastCachedPageCount(): Single<Int> {
        return breweryDao.getCount()
            .map { count -> floor((count / NetworkConfig.PER_PAGE_ITEMS).toDouble()).toInt() }
    }
}
