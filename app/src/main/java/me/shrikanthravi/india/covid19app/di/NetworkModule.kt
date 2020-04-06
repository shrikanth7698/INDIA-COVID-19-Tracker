package me.shrikanthravi.india.covid19app.di

import me.shrikanthravi.india.covid19app.BuildConfig
import me.shrikanthravi.india.covid19app.data.retrofit.CustomAppRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { provideRetrofit() }
    single { CustomAppRepository(get()) }
}

fun provideRetrofit(): Retrofit {
    return Retrofit.Builder().baseUrl(BuildConfig.CUSTOM_BASE_URL).client(provideOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideOkHttpClient(): OkHttpClient {
    val builder = OkHttpClient()
        .newBuilder()
    if(BuildConfig.DEBUG){
        val requestInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        builder.addNetworkInterceptor(requestInterceptor)
    }
    return  builder.build()
}