package cl.openworld.platformdev.ptkotlin.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Profile(
    val message: String?,
    val access_token: String?,
    val user: User?
) : Parcelable

@Parcelize
data class User(
    val created_at: String?,
    val email: String?,
    val email_verified_at: String?,
    val id: Int?,
    val name: String?,
    val organization_id: Int?,
    val updated_at: String?
) : Parcelable