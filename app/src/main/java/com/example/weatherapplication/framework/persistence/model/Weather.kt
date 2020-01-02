package com.example.weatherapplication.framework.persistence.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_weather")
data class Weather(
    @PrimaryKey val id: Long? = 1,
    val latitude: Double? = 0.0,
    val longitude: Double? = 0.0,
    val locationName: String? = "",
    val temperature: Double? = 0.0,
    val feelsLikeTemperature: Double? = 0.0,
    val maxTemperature: Double? = 0.0,
    val minTemperature: Double? = 0.0,
    val temperatureTime: Int? = 0,
    val humidity: Int? = 0,
    val pressure: Int? = 0,
    val windSpeed: Double? = 0.0,
    val type: String? = "",
    val description: String? = "",
    val icon: String? = ""
)