package com.example.weatherapplication.ui

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.weatherapplication.*
import com.example.weatherapplication.framework.network.services.WEATHER_ICON_URL
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_homepage.*
import kotlinx.android.synthetic.main.view_weather_meta_data.view.*


class HomepageActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var homepageViewModel: HomepageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_homepage)

        (application as WeatherApplication).appComponent.inject(this)

        homepageViewModel = ViewModelProviders.of(this).get(HomepageViewModel::class.java)

        checkForLocationPermission()
        initializeObservers()
    }

    /**
     * attaches observers to [homepageViewModel.weatherData] and
     * [homepageViewModel.apiLoadingStatus] in view model, so that UI is
     * automatically updated when value in live data changes
     */
    private fun initializeObservers() {
        homepageViewModel.weatherData.observe(this, Observer {
            val iconURL = WEATHER_ICON_URL + it.icon + "@2x.png"
            Glide.with(this@HomepageActivity).load(iconURL).into(weatherImageView)
            locationNameTextView.text = it.locationName
            weatherTypeTextView.text = it.type
            actualTemperatureTextView.text = it.temperature?.toDegreeCelsiusFormat()
            minTemperatureView.value.text = it.minTemperature?.toDegreeCelsiusFormat()
            maxTemperatureView.value.text = it.maxTemperature?.toDegreeCelsiusFormat()
            windView.value.text = it.windSpeed?.toSpeedFormatInMPS()
            humidityView.value.text = it.humidity?.toPercentageFormat()
        })

        homepageViewModel.apiLoadingStatus.observe(this, Observer {
            when (it) {
                HomepageViewModel.ApiLoadingStatus.IS_LOADING -> {
                    loaderFrameLayout.animate().alpha(1f).setDuration(500).start()
                }
                HomepageViewModel.ApiLoadingStatus.LOADED -> {
                    loaderFrameLayout.animate().alpha(0f).setDuration(500).start()
                }
                HomepageViewModel.ApiLoadingStatus.UNABLE_TO_FETCH_LOCATION -> {
                    showSnackBar(R.string.unable_to_fetch_location)
                    progressBar.visibility = View.GONE
                    message.text = getString(R.string.no_location_permission_granted)
                }
                else -> {
                    progressBar.visibility = View.GONE
                    message.text = getString(R.string.connect_to_internet)
                }
            }
        })
    }

    /**
     * checks for location permission if granted or not, when app is launched
     * if granted - fetch last known location to system
     * it not granted - ask user for location permission
     */
    private fun checkForLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                ),
                PERMISSION_REQUEST_LOCATION
            )
        } else {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            fetchLastKnownLocation()
        }
    }

    /**
     * fetch user's last known location
     * if location null - notify user
     * it not null - ask [HomepageViewModel] to [HomepageViewModel.fetchWeather]
     */
    private fun fetchLastKnownLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                Log.d("Location", location.latitude.toString() + location.longitude.toString())
                homepageViewModel.fetchWeather(location)
            } else {
                showSnackBar(R.string.unable_to_fetch_location)
            }
        }
    }

    private fun showSnackBar(messageStringResource: Int) {
        Snackbar.make(mainLayout, messageStringResource, Snackbar.LENGTH_SHORT)
            .show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_LOCATION ->
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
                    fetchLastKnownLocation()
                } else {
                    homepageViewModel.locationPermissionNotGranted()
                }
        }
    }


    companion object {
        const val PERMISSION_REQUEST_LOCATION = 1000
    }
}
