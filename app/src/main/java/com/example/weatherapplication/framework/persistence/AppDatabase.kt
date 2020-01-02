package com.example.weatherapplication.framework.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherapplication.framework.persistence.model.Weather

@Database(entities = [Weather::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao

}