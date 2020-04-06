package me.shrikanthravi.india.covid19app

import android.app.Application
import me.shrikanthravi.india.covid19app.di.appModule
import me.shrikanthravi.india.covid19app.di.networkModule
import me.shrikanthravi.india.covid19app.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class covid19app : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@covid19app)
            modules(listOf(appModule, networkModule, viewModelModule))
        }
    }

}