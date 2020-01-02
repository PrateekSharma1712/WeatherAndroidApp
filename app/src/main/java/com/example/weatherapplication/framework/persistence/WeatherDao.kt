package com.example.weatherapplication.framework.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapplication.framework.persistence.model.Weather

@Dao
interface WeatherDao {

    @Query("SELECT * FROM table_weather")
    fun getWeather(): Weather?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: Weather?)
}