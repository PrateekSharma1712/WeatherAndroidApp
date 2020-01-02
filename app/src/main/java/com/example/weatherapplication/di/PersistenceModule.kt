package com.example.weatherapplication.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.weatherapplication.framework.persistence.AppDatabase
import com.example.weatherapplication.framework.persistence.AppSharedPreference
import com.example.weatherapplication.framework.persistence.WeatherDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PersistenceModule {

    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java, "weather-app-database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideWeatherDao(appDatabase: AppDatabase): WeatherDao = appDatabase.weatherDao()

    @Provides
    @Singleton
    fun provideSharedPreference(application: Application): SharedPreferences =
        application.getSharedPreferences("WeatherApp", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideAppSharedPreference(sharedPreference: SharedPreferences): AppSharedPreference =
        AppSharedPreference(
            sharedPreference
        )
}