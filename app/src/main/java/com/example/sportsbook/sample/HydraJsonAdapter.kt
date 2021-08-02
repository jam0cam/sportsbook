package com.example.sportsbook.sample

import android.util.Log
import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import javax.inject.Singleton


@Singleton
class HydraJsonAdapter {

    val adapter = Moshi.Builder()
        .add(
            PolymorphicJsonAdapterFactory.of(HydraData::class.java, "type")
                .withSubtype(HydraEventData::class.java, HydraDataType.ACTION.value)
                .withSubtype(HydraScreenViewData::class.java, HydraDataType.SCREEN_VIEW.value)
        )
        .addLast(KotlinJsonAdapterFactory())
        .build()
        .adapter(HydraData::class.java)

    @ToJson
    fun toJson(data: HydraData): String {
        return adapter.toJson(data)
    }

    @FromJson
    fun fromJson(json: String): HydraData? {
        try {
            return adapter.fromJson(json)
        } catch (e: Exception) {
            Log.e("JIA", "${e.message}")
        }
        return null
    }

    companion object {
        const val TAG = "HydraJsonAdapter"
    }
}
