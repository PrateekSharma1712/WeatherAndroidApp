package com.example.weatherapplication.framework.network.services

import com.example.weatherapplication.framework.network.model.OpenWeatherMapDTO
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val WEATHER_ICON_URL = "http://openweathermap.org/img/wn/"

interface WeatherApiService {

    @GET("weather")
    fun getWeatherAsync(@Query("lat") latitude: Double, @Query("lon") longitude: Double) : Deferred<Response<OpenWeatherMapDTO>>
}