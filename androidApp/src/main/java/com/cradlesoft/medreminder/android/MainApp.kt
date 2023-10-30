package com.cradlesoft.medreminder.android

import android.app.Application
import com.cradlesoft.medreminder.android.di.androidModule
import com.cradlesoft.medreminder.di.prescriptionsDataSourceModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApp: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApp)
            androidLogger()
            modules(androidModule + prescriptionsDataSourceModule)
        }
    }
}