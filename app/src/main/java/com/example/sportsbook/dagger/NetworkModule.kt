package com.example.sportsbook.dagger

import com.example.sportsbook.network.ApiService
import com.example.sportsbook.network.MyCookieJar
import dagger.Module
import dagger.Provides
import okhttp3.CookieJar
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    @Singleton
    fun providesApiService(): ApiService {
        val httpClient = OkHttpClient()
            .newBuilder()
            .cookieJar(MyCookieJar())
            .build()


        return Retrofit.Builder()
            .baseUrl("https://www.sportsbook.ag")
            .client(httpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}