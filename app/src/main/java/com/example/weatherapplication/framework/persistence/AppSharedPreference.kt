package com.example.weatherapplication.framework.persistence

import android.content.SharedPreferences
import java.util.*
import javax.inject.Inject

open class AppSharedPreference @Inject constructor(private var sharedPreference: SharedPreferences) {

    companion object {
        const val LAST_WEATHER_FETCH_TIMESTAMP = "LAST_WEATHER_FETCH_TIMESTAMP"
    }

    /**
     * put current time in millis from [Calendar.getInstance] in shared preference
     */
    fun putTimeStamp() {
        sharedPreference.edit()
            .putLong(
                LAST_WEATHER_FETCH_TIMESTAMP,
                Calendar.getInstance().timeInMillis
            ).apply()
    }

    /**
     * get the time in milliseconds from shared preference
     */
    fun getTimeStamp(): Long = sharedPreference.getLong(LAST_WEATHER_FETCH_TIMESTAMP, 0)

}