package com.example.sportsbook.sample

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * analogous to [AnalyticEventSet]
 */
@Keep
data class HydraEventSet(
    @Json(name = "Events") val events: List<HydraEvent>
)