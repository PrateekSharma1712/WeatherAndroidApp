package com.example.weatherapplication.di

import android.app.Application
import android.location.Geocoder
import androidx.work.WorkManager
import com.example.weatherapplication.WeatherApplication
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.util.*
import javax.inject.Singleton

@Module
class AppModule(private val app: WeatherApplication) {

    @Provides
    @Singleton
    fun provideContext(): Application = app

    @Provides
    @Singleton
    fun provideGeoCoder(): Geocoder = Geocoder(app, Locale.getDefault())

    @Provides
    @Singleton
    fun provideWorkManager(): WorkManager = WorkManager.getInstance(app)

    @Provides
    @Singleton
    fun provideIOCoroutineScope(): CoroutineScope = CoroutineScope(Dispatchers.IO)

}