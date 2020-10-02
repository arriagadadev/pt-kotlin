package cl.openworld.platformdev.ptkotlin.domain.repositories

import cl.openworld.platformdev.ptkotlin.data.RemoteDataSource
import cl.openworld.platformdev.ptkotlin.data.models.Location
import cl.openworld.platformdev.ptkotlin.data.models.LocationResponse
import cl.openworld.platformdev.ptkotlin.data.models.Profile
import cl.openworld.platformdev.ptkotlin.data.models.UserRequest
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class UserRepository(private val remoteData: RemoteDataSource) {

    suspend fun doLogin(userRequest: UserRequest): Profile {

        return coroutineScope {
            val deferred = async { remoteData.login(userRequest).await() }
            deferred.await()
        }
    }

    suspend fun sendLocation(location: Location, auth: String): LocationResponse {

        return coroutineScope {
            val deferred = async { remoteData.sendLocation(location, "Bearer $auth").await() }
            deferred.await()
        }
    }

}