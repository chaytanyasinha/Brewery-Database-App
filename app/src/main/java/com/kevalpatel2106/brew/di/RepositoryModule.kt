package com.kevalpatel2106.brew.di

import android.app.Application
import com.kevalpatel2106.brew.repo.BreweryListRemoteMediator
import com.kevalpatel2106.brew.repo.BreweryRepository
import com.kevalpatel2106.brew.repo.BreweryRepositoryImpl
import com.kevalpatel2106.brew.repo.db.BreweryDao
import com.kevalpatel2106.brew.repo.db.BreweryDatabase
import com.kevalpatel2106.brew.repo.dto.BreweryDtoMapperImpl
import com.kevalpatel2106.brew.repo.network.BreweriesApi
import com.kevalpatel2106.brew.repo.network.NetworkClientProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Dagger module to provide repository dependency.
 */
@Module
class RepositoryModule(
    private val application: Application,
    private val inMemoryDatabase: Boolean,
    private val baseUrl: String
) {

    @Singleton
    @Provides
    fun provideNetworkClientProvider(): NetworkClientProvider {
        return NetworkClientProvider(baseUrl)
    }

    @Provides
    @Singleton
    fun provideBreweriesApi(networkClientProvider: NetworkClientProvider): BreweriesApi {
        return BreweriesApi.create(networkClientProvider.getRetrofitClient())
    }

    @Singleton
    @Provides
    fun provideDatabase(): BreweryDatabase {
        return BreweryDatabase.create(application, inMemoryDatabase)
    }

    @Singleton
    @Provides
    fun provideBreweryDao(db: BreweryDatabase): BreweryDao {
        return db.getBreweryDao()
    }

    @Singleton
    @Provides
    fun provideBreweryRepository(
        dao: BreweryDao,
        remoteMediator: BreweryListRemoteMediator
    ): BreweryRepository {
        return BreweryRepositoryImpl(
            mapper = BreweryDtoMapperImpl(),
            breweryDao = dao,
            remoteMediator = remoteMediator
        )
    }
}
