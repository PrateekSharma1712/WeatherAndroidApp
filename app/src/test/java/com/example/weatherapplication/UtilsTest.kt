package com.example.weatherapplication

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class UtilsTest {

    companion object {
        const val THRESHOLD_AFTER_TIME_IN_MINUTES = 10L
    }

    @Test
    fun shouldFetchLatestWeatherWhenTimeIsAfterCertainThreshold() {
        val lastWeatherTimeStamp = Calendar.getInstance().apply {
            timeInMillis -= THRESHOLD_AFTER_TIME_IN_MINUTES*65*1000
        }.timeInMillis

        val expected = true

        val actual = Utils.shouldFetchLatestWeather(lastWeatherTimeStamp)

        assertEquals(expected, actual)
    }

    @Test
    fun shouldNotFetchLatestWeatherWhenTimeIsBeforeCertainThreshold() {
        val lastWeatherTimeStamp = Calendar.getInstance().apply {
            timeInMillis -= THRESHOLD_AFTER_TIME_IN_MINUTES*30*1000
        }.timeInMillis

        val expected = false

        val actual = Utils.shouldFetchLatestWeather(lastWeatherTimeStamp)

        assertEquals(expected, actual)
    }
}