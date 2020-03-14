package com.me.data.weather.repository

import com.me.data.weather.datasource.IWeatherCacheDataSource
import com.me.data.weather.datasource.IWeatherRemoteDataSource
import com.me.domain.weather.entity.WeatherEntity
import com.me.domain.weather.repository.IWeatherRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

class WeatherRepositoryImpl(
    val weatherCacheDataSource: IWeatherCacheDataSource,
    val weatherRemoteDataSource: IWeatherRemoteDataSource
) : IWeatherRepository {

    override fun getWeatherLogs(): Flowable<List<WeatherEntity>> =
        weatherCacheDataSource.getWeatherLogs()


    override fun getRemoteWeatherLog(cityId: String): Single<WeatherEntity> =
        weatherRemoteDataSource.getWeatherLog(cityId)
            .flatMap {
                weatherCacheDataSource.addWeatherLog(it)
            }

    override fun getCachedWeatherLog(dateCreated: Long): Single<WeatherEntity> =
        weatherCacheDataSource.getWeatherLog(dateCreated)


    override fun editWeatherLog(weatherLog: WeatherEntity): Completable =
        weatherCacheDataSource.editWeatherLog(weatherLog)


    override fun addWeatherLog(weatherLog: WeatherEntity): Single<WeatherEntity> =
        weatherCacheDataSource.addWeatherLog(weatherLog)


    override fun deleteWeatherLog(dateCreated: Long): Completable =
        weatherCacheDataSource.deleteWeatherLog(dateCreated)

    override fun clearAllWeatherLog(): Completable =
        weatherCacheDataSource.clearAllWeatherLog()


}