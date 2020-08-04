package com.kevalpatel2106.brew.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kevalpatel2106.brew.breweriesList.BreweriesViewModel
import com.kevalpatel2106.brew.breweryDetail.BreweryDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Dagger module to provide view model dependency.
 */
@Module
internal abstract class ViewModelBindings {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(BreweriesViewModel::class)
    internal abstract fun bindBreweriesViewModel(viewModel: BreweriesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BreweryDetailViewModel::class)
    internal abstract fun bindBreweryDetailViewModel(viewModel: BreweryDetailViewModel): ViewModel
}
