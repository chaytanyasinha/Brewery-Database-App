package com.kevalpatel2106.brew.di

import android.app.Application
import com.kevalpatel2106.brew.breweriesList.BreweriesListFragment
import com.kevalpatel2106.brew.breweryDetail.BreweryDetailFragment
import com.kevalpatel2106.brew.repo.BreweryRepository
import com.kevalpatel2106.brew.repo.db.BreweryDao
import com.kevalpatel2106.brew.repo.db.BreweryDatabase
import com.kevalpatel2106.brew.repo.network.BreweriesApi
import com.kevalpatel2106.brew.repo.network.NetworkClientProvider
import dagger.Component
import javax.inject.Singleton

/**
 * Dagger component for the application.
 */
@Singleton
@Component(modules = [AppModule::class, RepositoryModule::class, ViewModelBindings::class])
interface AppComponent {
    fun provideApplication(): Application
    fun provideNetworkClientProvider(): NetworkClientProvider
    fun provideBreweryDatabase(): BreweryDatabase
    fun provideBreweriesApi(): BreweriesApi
    fun provideBreweryDao(): BreweryDao
    fun provideBreweryRepository(): BreweryRepository

    fun inject(breweryListFragment: BreweriesListFragment)
    fun inject(breweryDetailFragment: BreweryDetailFragment)
}
