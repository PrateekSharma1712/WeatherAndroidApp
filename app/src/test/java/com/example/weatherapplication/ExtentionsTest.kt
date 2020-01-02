package com.example.weatherapplication

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [21])
class ExtentionsTest {

    @Test
    fun temperatureContainsDegreeCelsius() {
        val conversion = 29.0.toDegreeCelsiusFormat()
        val actual = conversion.contains(0x00B0.toChar()) && conversion.contains("c")
        val expected = true
        assertEquals(expected, actual)
    }

    @Test
    fun temperatureDoesNotContainsDegreeFahrenheit() {
        val conversion = 29.0.toDegreeCelsiusFormat()
        val actual = conversion.contains(0x00B0.toChar()) && conversion.contains("f")
        val expected = false
        assertEquals(expected, actual)
    }

    @Test
    fun windSpeedContainsMPS() {
        val conversion = 3.0.toSpeedFormatInMPS()
        val actual = conversion.contains("m/s")
        val expected = true
        assertEquals(expected, actual)
    }

    @Test
    fun windSpeedContainsKMPH() {
        val conversion = 3.0.toSpeedFormatInMPS()
        val actual = conversion.contains("km/h")
        val expected = false
        assertEquals(expected, actual)
    }

    @Test
    fun numberHasPercentageSignAtEnd() {
        val conversion = 3.0.toSpeedFormatInMPS()
        val actual = conversion.subSequence(conversion.length - 3, conversion.length) == "m/s"
        val expected = true
        assertEquals(expected, actual)
    }

    @Test
    fun numberHasPercentageSignAtBeginning() {
        val conversion = 3.0.toSpeedFormatInMPS()
        val actual = conversion.subSequence(0, 4) == "km/h"
        val expected = false
        assertEquals(expected, actual)
    }
}