package cl.openworld.platformdev.ptkotlin.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import cl.openworld.platformdev.ptkotlin.R
import cl.openworld.platformdev.ptkotlin.databinding.FragmentHomeMapsBinding
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_home_maps.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeMapsFragment : Fragment() {

    private val viewModel: HomeMapsViewModel by lazy {
        ViewModelProvider(this).get(HomeMapsViewModel::class.java)
    }

    private lateinit var gMap: GoogleMap
    private lateinit var locationCallback: LocationCallback

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)


    private val safeArgs: HomeMapsFragmentArgs by navArgs()

    private val callback = OnMapReadyCallback { googleMap ->
        gMap = googleMap
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        locationCallback = getCallback()
        viewModel.getUid()

        viewModel.profile = safeArgs.profile


        val binding = FragmentHomeMapsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.floatingActionButton.setOnClickListener {

            if (viewModel.locationStatus) {
                it.floatingActionButton.setImageResource(R.drawable.ic_baseline_play_arrow_24)
                viewModel.stopLocationService()
            } else {

                it.floatingActionButton.setImageResource(R.drawable.ic_baseline_pause_24)
                viewModel.startLocationService(locationCallback)
            }
        }


        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

    }

    private fun getCallback(): LocationCallback {

        return object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations) {
                    gMap.clear()


                    val marker: MarkerOptions = MarkerOptions().position(
                        LatLng(
                            location.latitude,
                            location.longitude
                        )
                    ).title(viewModel.profile?.user?.name)
                    gMap.addMarker(marker)
                    gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.position, 17F))
                    uiScope.launch {
                        viewModel.sendLocation(location)
                    }
                }
            }
        }

    }
}