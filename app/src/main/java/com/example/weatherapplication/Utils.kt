package com.example.weatherapplication

import java.util.*

class Utils {
    companion object {

        private const val THRESHOLD_AFTER_TIME_IN_MINUTES = 10

        /**
         * return true is last recorded weather time is more than
         * [THRESHOLD_AFTER_TIME_IN_MINUTES] from current time
         * else returns false
         */
        fun shouldFetchLatestWeather(lastWeatherTimeStamp: Long): Boolean {
            val lastWeatherTime = Calendar.getInstance().apply {
                timeInMillis = lastWeatherTimeStamp
            }

            val someTimeAfterLastWeatherTime = lastWeatherTime.apply {
                add(Calendar.MINUTE, THRESHOLD_AFTER_TIME_IN_MINUTES)
            }

            return Calendar.getInstance().after(someTimeAfterLastWeatherTime)
        }
    }
}