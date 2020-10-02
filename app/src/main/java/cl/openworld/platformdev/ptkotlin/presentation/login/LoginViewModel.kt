package cl.openworld.platformdev.ptkotlin.presentation.login

import androidx.lifecycle.ViewModel
import cl.openworld.platformdev.ptkotlin.data.models.Profile
import cl.openworld.platformdev.ptkotlin.data.models.UserRequest
import cl.openworld.platformdev.ptkotlin.domain.repositories.UserRepository
import cl.openworld.platformdev.ptkotlin.services.network.UserApi
import timber.log.Timber

class LoginViewModel : ViewModel() {

    private val repository = UserRepository(UserApi.retrofitService)

    lateinit var userProfile: Profile

    suspend fun doLogin(username: String, password: String): Boolean {

        return try {

            userProfile = repository.doLogin(
                UserRequest(username, password)
            )
            if (userProfile.message != null) return false

            true
        } catch (e: Exception) {
            Timber.d(e.message)
            false
        }


    }
}