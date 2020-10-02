package cl.openworld.platformdev.ptkotlin.presentation.home


import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
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

    val repository = UserRepository(UserApi.retrofitService)

    private val uidService = UidManager(application)

    var profile: Profile? = null


    var locationStatus: Boolean = false

    var uid: String? = null


    fun startLocationService(locationCallback: LocationCallback) {
        try {
            locationServices.startLocationUpdates(locationCallback)
            locationStatus = true
        } catch (e: Exception) {
            Timber.d(e)
        }

    }

    fun stopLocationService() {
        try {
            locationServices.stopLocationService()
            locationStatus = false
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
            if (uid == null) getUid()

            val gps = Gps("Decimal(${location.latitude})", "Decimal(${location.longitude})")
            val location = LocationModel(uid!!, gps)

            repository.sendLocation(location, "${profile?.access_token}")


        } catch (e: Exception) {
            Timber.d(e)
        }

    }
}