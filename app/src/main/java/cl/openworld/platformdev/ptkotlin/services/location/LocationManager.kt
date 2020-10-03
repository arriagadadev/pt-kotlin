package cl.openworld.platformdev.ptkotlin.services.location

import android.Manifest
import android.accounts.AccountsException
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import timber.log.Timber


class LocationManager(private val context: Context) {


    private val locationRequest = LocationRequest.create()?.apply {
        interval = 100 * 30
        fastestInterval = 100
        priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var callback: LocationCallback

    fun startLocationUpdates(locationCallback: LocationCallback) {
        Timber.d("Startup Location")

        callback = locationCallback



        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)


        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            throw AccountsException("Necesitas los permisos para acceder al servicio")
        }

        Timber.d("Permissions Granted")


        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            callback,
            Looper.getMainLooper()
        )
    }


    fun stopLocationService() {
        fusedLocationClient.removeLocationUpdates(callback)
    }


}