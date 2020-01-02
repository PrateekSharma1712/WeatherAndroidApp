package com.example.weatherapplication.alarm

import android.content.Context
import android.location.Location
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.weatherapplication.WeatherApplication
import com.example.weatherapplication.framework.WeatherRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

/***
 * Worker that runs saves weather from user's current location in background
 */
class FetchWeatherWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    @Inject
    lateinit var weatherRepository: WeatherRepository
    @Inject
    lateinit var coroutineScope: CoroutineScope

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(appContext)

    init {
        WeatherApplication.application.appComponent.inject(this)
    }

    override fun doWork(): Result {
        Log.d("Worker", "doWork")
        fetchLastKnownLocation()

        return Result.success()
    }

    private fun fetchLastKnownLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let {
                coroutineScope.launch {
                    weatherRepository.getWeather(it)
                }
            }
        }
    }
}