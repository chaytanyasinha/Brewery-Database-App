package com.kevalpatel2106.brew.application

import com.kevalpatel2106.brew.di.AppComponent
import com.kevalpatel2106.brew.di.AppModule
import com.kevalpatel2106.brew.di.DaggerAppComponent
import com.kevalpatel2106.brew.di.RepositoryModule
import com.kevalpatel2106.brew.repo.network.NetworkConfig

class MainApplication : BreweryApplication() {
    override fun getAppComponent(): AppComponent {
        return DaggerAppComponent.builder()
            .appModule(AppModule(this@MainApplication))
            .repositoryModule(RepositoryModule(this@MainApplication, false, NetworkConfig.BASE_URL))
            .build()
    }
}
