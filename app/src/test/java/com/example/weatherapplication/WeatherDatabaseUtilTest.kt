package com.example.weatherapplication

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.example.weatherapplication.framework.persistence.AppDatabase
import com.example.weatherapplication.framework.persistence.WeatherDao
import com.example.weatherapplication.framework.persistence.model.Weather
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.IOException

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [21])
class WeatherDatabaseUnitTest {

    private lateinit var weatherDao: WeatherDao
    private lateinit var appDatabase: AppDatabase

    @Before
    fun createDB() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        appDatabase = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).allowMainThreadQueries().build()
        weatherDao = appDatabase.weatherDao()
    }

    @Test
    @Throws(Exception::class)
    fun testInsertAndRetrieveWeather() {
        val weather = Weather(
            id = 1, latitude = 18.594594594594593, longitude = 73.76030888904222,
            locationName = "Wakad, Pune",
            temperature = 29.0,
            feelsLikeTemperature = 30.0,
            maxTemperature = 29.0,
            minTemperature = 29.0,
            humidity = 50,
            pressure = 30,
            windSpeed = 70.0,
            type = "Sunny",
            description = "Description",
            icon = "10n",
            temperatureTime = 1577860030
        )

        runBlocking {
            print("in run")
            weatherDao.insertWeather(weather)
            print("out run")
        }

        val weatherEntryFromDB = weatherDao.getWeather()
        assertEquals(weatherEntryFromDB, weather)
    }

    @After
    @Throws(IOException::class)
    fun closeDB() {
        appDatabase.close()
    }

}
