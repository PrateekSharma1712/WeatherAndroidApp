package com.example.weatherapplication.di

import android.app.Application
import com.example.weatherapplication.alarm.FetchWeatherWorker
import com.example.weatherapplication.ui.HomepageActivity
import com.example.weatherapplication.ui.HomepageViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, WeatherApiModule::class, PersistenceModule::class, RepositoryModule::class])
interface AppComponent {
    fun inject(viewModel: HomepageViewModel)
    fun inject(worker: FetchWeatherWorker)
    fun inject(homepageActivity: HomepageActivity)

    fun getApplication(): Application
}