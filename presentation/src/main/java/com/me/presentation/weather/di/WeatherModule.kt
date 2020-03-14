package com.me.presentation.weather.di


import com.me.data.base.AppDatabase
import com.me.data.base.ServiceGenerator
import com.me.data.weather.datasource.IWeatherCacheDataSource
import com.me.data.weather.datasource.IWeatherRemoteDataSource
import com.me.data.weather.datasource.local.WeatherCacheDataSourceImpl
import com.me.data.weather.datasource.remote.WeatherRemoteDataSourceImpl
import com.me.data.weather.datasource.remote.api.IWeatherApi
import com.me.data.weather.repository.WeatherRepositoryImpl
import com.me.domain.weather.repository.IWeatherRepository
import com.me.domain.weather.usecase.WeatherUseCase
import com.me.presentation.BuildConfig
import com.me.presentation.weather.view.viewmodel.WeatherLogViewModel
import dagger.Module
import dagger.Provides


@Module
class WeatherModule {


    @Provides
    fun provideWeatherLogServiceAPIs(): IWeatherApi =
        ServiceGenerator.createService(IWeatherApi::class.java, BuildConfig.DEBUG)
    

    @Provides
    fun provideWeatherLogRemoteDataSource(
        weatherApi: IWeatherApi
    ): IWeatherRemoteDataSource =
        WeatherRemoteDataSourceImpl(weatherApi)


    @Provides
    fun provideWeatherLogCacheDataSource(
        appDatabase: AppDatabase
    ): IWeatherCacheDataSource =
        WeatherCacheDataSourceImpl(appDatabase)


    @Provides
    fun provideWeatherLogRepository(
        weatherRemoteDataSource: IWeatherRemoteDataSource, weatherCacheDataSource: IWeatherCacheDataSource
    ): IWeatherRepository =
        WeatherRepositoryImpl(
            weatherCacheDataSource,
            weatherRemoteDataSource
        )


    @Provides
    fun provideWeatherLogUseCase(weatherRepository: IWeatherRepository): WeatherUseCase =
        WeatherUseCase(weatherRepository)

    @Provides
    fun provideWeatherLogListViewModel(
        weatherUseCase: WeatherUseCase
    ): WeatherLogViewModel =
        WeatherLogViewModel(
            weatherUseCase
        )


}