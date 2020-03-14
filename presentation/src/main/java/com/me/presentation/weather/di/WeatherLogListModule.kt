package com.me.presentation.weather.di


import com.me.presentation.weather.view.adapter.WeatherLogListAdapter
import dagger.Module
import dagger.Provides


@Module
class WeatherLogListModule {

    @Provides
    fun provideWeatherLogListAdapter(): WeatherLogListAdapter {
        return WeatherLogListAdapter()
    }


}