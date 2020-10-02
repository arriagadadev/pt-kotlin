package cl.openworld.platformdev.ptkotlin.services.location

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import timber.log.Timber


class LocationManager(private val context: Context) {


    private val locationPermissionRequest = 1

    private val locationPermission = "android.permission.ACCESS_FINE_LOCATION"

    private val locationRequest = LocationRequest.create()?.apply {
        interval = 1000 * 30
        fastestInterval = 2000
        priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var callback: LocationCallback

    fun startLocationUpdates(locationCallback: LocationCallback) {
        Timber.d("Startup Location")
        if (ContextCompat.checkSelfPermission(
                context,
                locationPermission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                context as Activity,
                arrayOf(locationPermission),
                locationPermissionRequest
            )
            Timber.d("Request permition")
            return
        }
        callback = locationCallback



        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)


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