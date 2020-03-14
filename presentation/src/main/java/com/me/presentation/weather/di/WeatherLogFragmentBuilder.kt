package com.me.presentation.weather.di

import com.me.presentation.weather.view.fragment.WeatherLogDetailsFragment
import com.me.presentation.weather.view.fragment.WeatherLogListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class WeatherLogFragmentBuilder {

    @ContributesAndroidInjector(modules = [WeatherModule::class, WeatherLogListModule::class])
    abstract fun bindWeatherLogListFragment(): WeatherLogListFragment

    @ContributesAndroidInjector(modules = [WeatherModule::class])
    abstract fun bindPostDetailFragment(): WeatherLogDetailsFragment

}

