package cl.openworld.platformdev.ptkotlin.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Location(val identifier: String, val gps: Gps) : Parcelable

@Parcelize
data class Gps(val latitude: String, val longitude: String) : Parcelable