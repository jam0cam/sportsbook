package com.example.sportsbook.sample

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.sportsbook.MySchedulers
import com.example.sportsbook.main.BaseViewModel
import com.example.sportsbook.withSchedulers
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

class SampleViewModel @Inject constructor(
    private val service: SampleApiService,
    private val schedulers: MySchedulers,
): BaseViewModel() {

    @SuppressLint("CheckResult")
    fun makeApiCall() {

        service.dispatchHydraEvents(getData())
            .withSchedulers(schedulers)
            .subscribe(
                {
                   Log.e("JIA", "success sending!!")
                },
                {
                    Log.e("JIA", "error sending data: ${it.message}")
                }
            )

    }

    private fun getData(): HydraEventSet =  HydraEventSet(
        events = listOf(
            HydraEvent(
                name = "ScreenView",
                eventData = listOf(
                    HydraEventData(
                        eventId = "123456",
                        timestampUtc = "",
                        page = "search page",
                        section = "search section",
                        component = "search component",
                        element = "results button",
                        initiator = "user",
                        interactionType = "tap",
                        screenViewId = "",
                        screenName = "",
                        trackingId = "",
                        sessionId = "",
                        isAuditing = null,
                        attributedPatientId = null,
                        availabilityObject = null,
                        bookingId = null,
                        eventDetails = null,
                        locationId = null,
                        monolithInsuranceCarrierId = null,
                        monolithInsurancePlanId = null,
                        monolithProcedureId = null,
                        monolithProfessionalId = null,
                        monolithProviderId = null,
                        monolithSpecialtyId = null,
                        patientId = null,
                        procedureId = null,
                        specialtyId = null,
                        practiceId = null,
                        providerId = null,
                        searchRequestId = null,
                        searchResultId = null
                    )
                )
            )
        )
    )
}