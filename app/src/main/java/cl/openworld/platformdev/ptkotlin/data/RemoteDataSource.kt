package cl.openworld.platformdev.ptkotlin.data

import cl.openworld.platformdev.ptkotlin.data.models.Location
import cl.openworld.platformdev.ptkotlin.data.models.LocationResponse
import cl.openworld.platformdev.ptkotlin.data.models.Profile
import cl.openworld.platformdev.ptkotlin.data.models.UserRequest
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface RemoteDataSource {

    @Headers(
        "Content-Type: application/json",
        "Accept: application/json",
    )
    @POST("login")
    fun login(
        @Body userRequest: UserRequest
    ): Deferred<Profile>


    @Headers(
        "Content-Type: application/json",
        "Accept: application/json",
    )
    @POST("pt-kotlin")
    fun sendLocation(
        @Body location: Location, @Header("Authorization") token: String
    ): Deferred<LocationResponse>
}