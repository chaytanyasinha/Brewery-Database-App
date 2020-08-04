package com.kevalpatel2106.brew.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.kevalpatel2106.brew.entity.Brewery
import com.kevalpatel2106.brew.repo.db.BreweryDao
import com.kevalpatel2106.brew.repo.dto.BreweryDtoMapper
import com.kevalpatel2106.brew.repo.network.NetworkConfig
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Repository to handle [Brewery] paged list and cache database.
 */
interface BreweryRepository {
    fun loadCachedBreweryPagedList(): Flowable<PagingData<Brewery>>
    fun invalidateCache(): Completable
}

class BreweryRepositoryImpl @Inject constructor(
    private val breweryDao: BreweryDao,
    private val mapper: BreweryDtoMapper,
    private val remoteMediator: BreweryListRemoteMediator
) : BreweryRepository {

    override fun loadCachedBreweryPagedList(): Flowable<PagingData<Brewery>> {
        return Pager(
            config = PagingConfig(
                pageSize = NetworkConfig.PER_PAGE_ITEMS,
                enablePlaceholders = false
            ),
            remoteMediator = remoteMediator,
            pagingSourceFactory = { breweryDao.observeAll() }
        ).flowable.map { pagingData -> pagingData.mapSync { mapper.toEntity(it) } }
    }

    override fun invalidateCache() = Completable.fromCallable { breweryDao.deleteAll() }
}

