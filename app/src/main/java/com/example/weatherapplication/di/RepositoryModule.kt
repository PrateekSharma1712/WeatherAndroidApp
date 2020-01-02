package com.example.weatherapplication.di

import android.location.Geocoder
import com.example.weatherapplication.framework.WeatherRepository
import com.example.weatherapplication.framework.network.services.WeatherApiService
import com.example.weatherapplication.framework.persistence.AppSharedPreference
import com.example.weatherapplication.framework.persistence.WeatherDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun providerRepository(
        weatherDao: WeatherDao,
        weatherApiService: WeatherApiService,
        appSharedPreference: AppSharedPreference,
        geoCoder: Geocoder
    ) = WeatherRepository(
        weatherDao,
        weatherApiService,
        appSharedPreference,
        geoCoder
    )
}