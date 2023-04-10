package com.example.benchmarks

import android.app.Application
import com.example.benchmarks.models.AppComponent
import com.example.benchmarks.models.DaggerAppComponent

class BenchmarksApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }

    companion object {
        private var appComponent: AppComponent? = null
        @JvmStatic
        fun setAppComponent(component: AppComponent?) {
            appComponent = component
        }

        @JvmStatic
       fun getAppComponent(): AppComponent? {
            return appComponent
        }
    }
}