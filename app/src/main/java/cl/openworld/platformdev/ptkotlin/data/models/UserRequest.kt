package cl.openworld.platformdev.ptkotlin.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserRequest(
    val email: String,
    val password: String
) : Parcelable