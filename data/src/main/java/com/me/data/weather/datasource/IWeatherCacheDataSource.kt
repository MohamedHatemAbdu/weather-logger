package com.me.data.weather.datasource

import com.me.domain.weather.entity.WeatherEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface IWeatherCacheDataSource {

    fun getWeatherLogs(): Flowable<List<WeatherEntity>>

    fun setWeatherLogs(weatherLogList: List<WeatherEntity>): Flowable<List<WeatherEntity>>

    fun getWeatherLog(dateCreated: Long): Single<WeatherEntity>

    fun addWeatherLog(weatherLog: WeatherEntity): Single<WeatherEntity>

    fun editWeatherLog(weatherLog: WeatherEntity): Completable

    fun deleteWeatherLog(dateCreated: Long): Completable

    fun clearAllWeatherLog(): Completable


}