package com.kevalpatel2106.brew

import android.app.Application
import android.app.Instrumentation
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.kevalpatel2106.brew.application.AndroidTestApplication

@Suppress("unused")
class UiTestsRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        // Use the test application class that can inject mock dependencies using dagger.
        return Instrumentation.newApplication(AndroidTestApplication::class.java, context)
    }
}
