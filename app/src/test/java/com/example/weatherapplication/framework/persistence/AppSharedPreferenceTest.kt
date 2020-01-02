package com.example.weatherapplication.framework.persistence

import android.content.Context
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.lessThanOrEqualTo
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import java.util.*

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [21])
class AppSharedPreferenceTest {

    lateinit var appSharedPreference: AppSharedPreference

    @Before
    fun setup() {
        val context = RuntimeEnvironment.systemContext
        appSharedPreference =
            AppSharedPreference(
                context.getSharedPreferences("WeatherApp", Context.MODE_PRIVATE)
            )
    }

    @Test
    fun shouldPutTimestampTest() {
        appSharedPreference.putTimeStamp()
        assertThat(appSharedPreference.getTimeStamp(), lessThanOrEqualTo(Calendar.getInstance().timeInMillis))
    }
}