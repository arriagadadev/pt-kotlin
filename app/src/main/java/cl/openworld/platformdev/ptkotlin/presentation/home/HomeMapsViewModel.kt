package cl.openworld.platformdev.ptkotlin.presentation.home


import android.accounts.AccountsException
import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import cl.openworld.platformdev.ptkotlin.data.models.Gps
import cl.openworld.platformdev.ptkotlin.data.models.Profile
import cl.openworld.platformdev.ptkotlin.domain.repositories.UserRepository
import cl.openworld.platformdev.ptkotlin.services.location.LocationManager
import cl.openworld.platformdev.ptkotlin.services.network.UserApi
import cl.openworld.platformdev.ptkotlin.services.uid.UidManager
import com.google.android.gms.location.LocationCallback
import timber.log.Timber
import cl.openworld.platformdev.ptkotlin.data.models.Location as LocationModel

class HomeMapsViewModel(application: Application) : AndroidViewModel(application) {

    private val locationServices = LocationManager(application)

    private val repository = UserRepository(UserApi.retrofitService)

    private val uidService = UidManager(application)

    var profile: Profile? = null


    var locationStatus = MutableLiveData<Boolean>(false)

    private var uid: String? = null


    fun startLocationService(locationCallback: LocationCallback): Boolean? {
        try {
            locationServices.startLocationUpdates(locationCallback)
            locationStatus.value = true
            return false
        } catch (e: Exception) {
            Timber.d(e)

            when (e) {
                is AccountsException -> {
                    return true
                }
            }
            locationServices.stopLocationService()
            return null
        }

    }

    fun stopLocationService() {
        try {
            locationServices.stopLocationService()
            locationStatus.value = false
        } catch (e: Exception) {
            Timber.d(e)
        }

    }

    fun getUid() {
        try {
            Timber.d("Getting uid")
            uid = uidService.getDeviceId()
            Timber.d(uid)
        } catch (e: Exception) {
            Timber.d(e)
        }

    }

    suspend fun sendLocation(location: Location) {
        try {
            if (uid == null) {
                getUid()

            }


            val gps = Gps(location.latitude.toString(), location.longitude.toString())
            val location = LocationModel(uid!!, gps)

            repository.sendLocation(location, "${profile?.access_token}")


        } catch (e: Exception) {

            Timber.d(e)

        }

    }
}