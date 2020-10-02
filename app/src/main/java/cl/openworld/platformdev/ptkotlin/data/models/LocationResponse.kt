package cl.openworld.platformdev.ptkotlin.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LocationResponse(val message: String?, val status: String?) : Parcelable