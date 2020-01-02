package com.example.weatherapplication

import android.location.Geocoder
import android.location.Location
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
@LargeTest
class AddressSingleLineTest {

    @Test
    fun singleLineAddressContainsComma() {
        val location = Location("")
        location.apply {
            latitude = 18.594594594594593
            longitude = 73.76030888904222
        }
        val geoCoder = Geocoder(ApplicationProvider.getApplicationContext(), Locale.getDefault())
        val actual = location.toSingleLineAddress(geoCoder).contains(",")
        val expected = true

        assertEquals(expected, actual)
    }
}
