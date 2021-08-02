package com.example.sportsbook.dagger

import com.example.sportsbook.network.ApiService
import com.example.sportsbook.network.MyCookieJar
import com.example.sportsbook.sample.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.CookieJar
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
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

    @Provides
    @Singleton
    fun providesSampleService(): SampleApiService {
//        val moshi = Moshi.Builder()
//            .add(HydraJsonAdapter())
//            .addLast(KotlinJsonAdapterFactory())
//            .build()



        val moshi2 = Moshi.Builder()
            .add(
                PolymorphicJsonAdapterFactory.of(HydraData::class.java, "type")
                    .withSubtype(HydraEventData::class.java, HydraDataType.ACTION.value)
                    .withSubtype(HydraScreenViewData::class.java, HydraDataType.SCREEN_VIEW.value)
            )
            .addLast(KotlinJsonAdapterFactory())
            .build()



        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.zocdoc.com")
            .addConverterFactory(MoshiConverterFactory.create(moshi2))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        return retrofit.create(SampleApiService::class.java)
    }
}