package me.shrikanthravi.india.covid19app.di

import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {

    single {
        Gson()
    }

}