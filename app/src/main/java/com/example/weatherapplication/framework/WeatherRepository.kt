package com.example.weatherapplication.framework

import android.location.Geocoder
import android.location.Location
import com.example.weatherapplication.Utils
import com.example.weatherapplication.framework.network.model.OpenWeatherMapDTO
import com.example.weatherapplication.framework.network.services.WeatherApiService
import com.example.weatherapplication.framework.persistence.AppSharedPreference
import com.example.weatherapplication.framework.persistence.WeatherDao
import com.example.weatherapplication.framework.persistence.model.Weather
import com.example.weatherapplication.toSingleLineAddress
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherDao: WeatherDao,
    private val weatherApiService: WeatherApiService,
    private val appSharedPreference: AppSharedPreference,
    private val geoCoder: Geocoder
) {

    /**
     * checks if [Weather] should be fetched from [WeatherApiService] or
     * [WeatherDao]
     * Once weather received, saves to local database
     */
    suspend fun getWeather(location: Location): Weather? {
        val lastWeatherTimeStamp = appSharedPreference.getTimeStamp()

        return if (Utils.shouldFetchLatestWeather(lastWeatherTimeStamp)) {
            val weatherResponseDeferred =
                weatherApiService.getWeatherAsync(location.latitude, location.longitude).await()

            if (weatherResponseDeferred.isSuccessful) {
                val weather = parseWeatherResponse(weatherResponseDeferred.body(), location)
                weatherDao.insertWeather(weather)
                saveLastFetchedTimeStamp()
                weather
            } else {
                null
            }
        } else {
            weatherDao.getWeather()
        }
    }

    /**
     * parses weather network response [OpenWeatherMapDTO] to [Weather]
     * because, [OpenWeatherMapDTO] has extra fields which are irrelevant to show in
     * UI
     */
    private fun parseWeatherResponse(
        openWeatherMapDTO: OpenWeatherMapDTO?,
        location: Location
    ): Weather? {
        return openWeatherMapDTO?.let {
            Weather(
                id = 1, latitude = it.coord?.lat, longitude = it.coord?.lon,
                locationName = location.toSingleLineAddress(geoCoder),
                temperature = it.main?.temp ?: 0.0,
                feelsLikeTemperature = it.main?.feelsLike ?: 0.0,
                temperatureTime = it.dt,
                maxTemperature = it.main?.tempMax ?: 0.0,
                minTemperature = it.main?.tempMin ?: 0.0,
                humidity = it.main?.humidity ?: 0,
                pressure = it.main?.pressure ?: 0,
                windSpeed = it.wind?.speed ?: 0.0,
                type = it.weather?.get(0)?.main ?: "",
                description = it.weather?.get(0)?.description ?: "",
                icon = it.weather?.get(0)?.icon ?: ""
            )
        }
    }

    private fun saveLastFetchedTimeStamp() {
        appSharedPreference.putTimeStamp()
    }
}