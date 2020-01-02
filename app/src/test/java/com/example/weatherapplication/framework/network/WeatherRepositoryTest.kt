package com.example.weatherapplication.framework.network

import android.content.Context
import android.location.Geocoder
import android.location.Location
import com.example.weatherapplication.framework.WeatherRepository
import com.example.weatherapplication.framework.network.model.OpenWeatherMapDTO
import com.example.weatherapplication.framework.network.services.WeatherApiService
import com.example.weatherapplication.framework.persistence.AppSharedPreference
import com.example.weatherapplication.framework.persistence.WeatherDao
import com.example.weatherapplication.framework.persistence.model.Weather
import com.nhaarman.mockito_kotlin.verify
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import retrofit2.Response


@RunWith(RobolectricTestRunner::class)
@Config(sdk = [21])
class WeatherRepositoryTest {

    @get:Rule
    var mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var weatherApiService: WeatherApiService

    @Mock
    lateinit var weatherDao: WeatherDao

    lateinit var sharedPreference: AppSharedPreference

    @Mock
    lateinit var geoCoder: Geocoder

    lateinit var weatherRepository: WeatherRepository

    @Mock
    lateinit var location: Location

    private lateinit var weather: Weather

    @Mock
    lateinit var openWeatherMapDTO: OpenWeatherMapDTO

    lateinit var openWeatherMapDTOResponse: Response<OpenWeatherMapDTO>

    @Before
    fun setup() {
        val context = RuntimeEnvironment.systemContext
        sharedPreference =
            AppSharedPreference(context.getSharedPreferences("WeatherApp", Context.MODE_PRIVATE))
        MockitoAnnotations.initMocks(this)
        location.apply {
            latitude = 18.594594594594593
            longitude = 73.76030888904222
        }
        weatherRepository =
            WeatherRepository(
                weatherDao,
                weatherApiService,
                sharedPreference,
                geoCoder
            )

        weather = Weather()

        openWeatherMapDTOResponse = Response.success(openWeatherMapDTO)

        Mockito.`when`(
            weatherApiService.getWeatherAsync(
                location.latitude,
                location.longitude
            )
        ).thenReturn(
            GlobalScope.async { openWeatherMapDTOResponse }
        )

        runBlocking {
            Mockito.`when`(weatherDao.insertWeather(weather)).then {}
        }
    }

    @Test
    fun testFetchWeather() {
        println("start")

        runBlocking {
            println("in coroutine")
            weatherRepository.getWeather(location)
        }

        verify(weatherApiService).getWeatherAsync(location.latitude, location.longitude)
    }


}