package com.example.weatherapplication.ui

import android.location.Geocoder
import android.location.Location
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.weatherapplication.WeatherApplication
import com.example.weatherapplication.alarm.FetchWeatherWorker
import com.example.weatherapplication.framework.WeatherRepository
import com.example.weatherapplication.framework.persistence.WeatherDao
import com.example.weatherapplication.framework.persistence.model.Weather
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomepageViewModel : ViewModel() {

    @Inject
    lateinit var weatherRepository: WeatherRepository
    @Inject
    lateinit var weatherDao: WeatherDao
    @Inject
    lateinit var geoCoder: Geocoder
    @Inject
    lateinit var coroutineScope: CoroutineScope
    @Inject
    lateinit var workManager: WorkManager

    private val _weatherData = MutableLiveData<Weather>()
    val weatherData: MutableLiveData<Weather>
        get() = _weatherData

    private val _apiLoadingStatus = MutableLiveData<ApiLoadingStatus>()
    val apiLoadingStatus: MutableLiveData<ApiLoadingStatus>
        get() = _apiLoadingStatus


    enum class ApiLoadingStatus {
        IS_LOADING, LOADED, UNABLE_TO_LOAD_WEATHER, UNABLE_TO_FETCH_LOCATION
    }

    init {
        WeatherApplication.application.appComponent.inject(this)
    }

    fun fetchWeather(location: Location) {
        _apiLoadingStatus.postValue(ApiLoadingStatus.IS_LOADING)

        if (WeatherApplication.application.isConnectedToInternet()) {
            coroutineScope.launch {
                val weather = weatherRepository.getWeather(location)
                _weatherData.postValue(weather)
                _apiLoadingStatus.postValue(ApiLoadingStatus.LOADED)
                scheduleFutureRecurringWeatherLoad()
            }
        } else {
            coroutineScope.launch {
                val weather = weatherDao.getWeather()
                weather?.let {
                    _weatherData.postValue(it)
                    _apiLoadingStatus.postValue(ApiLoadingStatus.LOADED)
                    return@launch
                }
                _apiLoadingStatus.postValue(ApiLoadingStatus.UNABLE_TO_LOAD_WEATHER)
            }
        }
    }

    /**
     * starts periodic work request and passed to work manager
     * [FetchWeatherWorker]
     */
    private fun scheduleFutureRecurringWeatherLoad() {
        Log.d("View Model", "Scheduled")
        val constraints =
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val request = PeriodicWorkRequestBuilder<FetchWeatherWorker>(
            REPEAT_INTERVAL,
            TimeUnit.MINUTES
        ).setConstraints(constraints).build()
        workManager.enqueue(request)
    }

    fun locationPermissionNotGranted() {
        _apiLoadingStatus.postValue(ApiLoadingStatus.UNABLE_TO_FETCH_LOCATION)
    }

    companion object {
        const val REPEAT_INTERVAL = 120L
    }
}