package com.example.sportsbook.sample

import android.os.Parcelable
import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
class HydraEvent (
    @Json(name = "BusinessUnit") val businessUnit: String = "PatientAppHydra",
    @Json(name = "Version") val version: String = "1.0.0",
    @Json(name = "Name") val name: String,
    @Json(name = "EventData") val eventData: List<HydraData>
)

sealed class HydraData(@Transient var type: HydraDataType? = null)

@Keep
enum class HydraDataType(val value: String) {
    ACTION("Action"),
    SCREEN_VIEW("ScreenView"),
    KEY_EVENT("KeyEvent");
}

@Keep
@JsonClass(generateAdapter = true)
data class HydraEventData(
    @Json(name = "EventId") val eventId: String,
    @Json(name = "TimestampUtc") val timestampUtc: String,
    @Json(name = "Page") val page: String,
    @Json(name = "Section") val section: String,
    @Json(name = "Component") val component: String,
    @Json(name = "Element") val element: String,
    @Json(name = "Initiator") val initiator: String,
    @Json(name = "InteractionType") val interactionType: String,
    @Json(name = "ScreenViewId") val screenViewId: String,
    @Json(name = "ScreenName") val screenName: String,
    @Json(name = "TrackingId") val trackingId: String,
    @Json(name = "SessionId") val sessionId: String,
    @Json(name = "IsAuditing") val isAuditing: Boolean? = null,

    //optional
    @Json(name = "AttributedPatientId") val attributedPatientId: Int? = null,
    @Json(name = "AvailabilityObject") val availabilityObject: String? = null,
    @Json(name = "BookingId") val bookingId: String? = null,
    @Json(name = "EventDetails") val eventDetails: String? = null,
    @Json(name = "LocationId") val locationId: String? = null,
    @Json(name = "MonolithInsuranceCarrierId") val monolithInsuranceCarrierId: Int? = null,
    @Json(name = "MonolithInsurancePlanId") val monolithInsurancePlanId: Int? = null,
    @Json(name = "MonolithProcedureId") val monolithProcedureId: Int? = null,
    @Json(name = "MonolithProfessionalId") val monolithProfessionalId: String? = null,
    @Json(name = "MonolithProviderId") val monolithProviderId: Int? = null,
    @Json(name = "MonolithSpecialtyId") val monolithSpecialtyId: Int? = null,
    @Json(name = "PatientId") val patientId: Int? = null,
    @Json(name = "ProcedureId") val procedureId: String? = null,
    @Json(name = "SpecialtyId") val specialtyId: String? = null,
    @Json(name = "PracticeId") val practiceId: String? = null,
    @Json(name = "ProviderId") val providerId: String? = null,
    @Json(name = "SearchRequestId") val searchRequestId: String? = null,
    @Json(name = "SearchResultId") val searchResultId: String? = null
) : HydraData(HydraDataType.ACTION) {

    companion object {
        const val TABLE_NAME = "hydra_event_data"
    }
}


@Keep
@JsonClass(generateAdapter = true)
data class HydraScreenViewData(
    //required fields
    @Json(name = "EventId") val eventId: String,
    @Json(name = "TimestampUtc") val timestampUtc: String,
    @Json(name = "Name") val name: String,
    @Json(name = "ScreenViewId") val screenViewId: String,
    @Json(name = "ScreenName") val screenName: String,
    @Json(name = "ScreenCategory") val screenCategory: String,
    @Json(name = "SessionId") val sessionId: String,
    @Json(name = "TrackingId") val trackingId: String,
    @Json(name = "IsAuditing") val isAuditing: Boolean? = null,

    //optional fields
    @Json(name = "PreviousScreenViewId") val previousScreenViewId: String? = null,
    @Json(name = "PreviousScreenName") val previousScreenName: String? = null,
    @Json(name = "PreviousScreenCategory") val previousScreenCategory: String? = null,
    @Json(name = "PatientId") val patientId: Int? = null,
    @Json(name = "Age") val age: Int? = null,
    @Json(name = "Gender") val gender: String? = null,
    @Json(name = "ZipCode") val zipCode: String? = null,
    @Json(name = "IsUsingBiometrics") val isUsingBiometrics: Boolean? = null,
    @Json(name = "IsUsingPasscode") val isUsingPasscode: Boolean? = null,
    @Json(name = "IsPushNotificationEnabled") val isPushNotificationEnabled: Boolean? = null,
    @Json(name = "IsWellnessPushEnabled") val isWellnessPushEnabled: Boolean? = null,
    @Json(name = "IsWellnessEmailEnabled") val isWellnessEmailEnabled: Boolean? = null,
    @Json(name = "HasPhoneNumber") val hasPhoneNumber: Boolean? = null,
    @Json(name = "HasInsuranceCard") val hasInsuranceCard: Boolean? = null,
    @Json(name = "SavedDoctorIdList") val savedDoctorIdList: String? = null,
    @Json(name = "MedicalTeamIdList") val medicalTeamIdList: String? = null,
    @Json(name = "CompletedWellGuideTagList") val completedWellGuideTagList: String? = null,
) : HydraData(HydraDataType.SCREEN_VIEW) {

    companion object {
        const val TABLE_NAME = "hydra_screen_view_data"
    }
}
