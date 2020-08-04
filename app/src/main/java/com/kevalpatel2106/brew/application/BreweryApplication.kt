package com.kevalpatel2106.brew.application

import android.app.Application
import com.kevalpatel2106.brew.di.AppComponent

/**
 * [Application] class for app.
 */
abstract class BreweryApplication : Application() {

    /**
     * Provides [AppComponent] with all modules. In the test you can swap the dependency modules
     * as needed.
     */
    abstract fun getAppComponent(): AppComponent
}
