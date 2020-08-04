package com.kevalpatel2106.brew.di

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Dagger module to provide application dependency.
 */
@Module
class AppModule(private val application: Application) {

    @Singleton
    @Provides
    fun provideApplication(): Application = application
}
