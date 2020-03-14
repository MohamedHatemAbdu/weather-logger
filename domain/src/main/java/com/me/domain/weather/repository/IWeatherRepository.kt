package com.me.domain.weather.repository

import com.me.domain.weather.entity.WeatherEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single


interface IWeatherRepository {

    fun getWeatherLogs(): Flowable<List<WeatherEntity>>

    fun getRemoteWeatherLog(cityId: String): Single<WeatherEntity>

    fun getCachedWeatherLog(dateCreated: Long): Single<WeatherEntity>

    fun editWeatherLog(weatherLog: WeatherEntity): Completable

    fun addWeatherLog(weatherLog: WeatherEntity): Single<WeatherEntity>

    fun deleteWeatherLog(dateCreated: Long): Completable

    fun clearAllWeatherLog(): Completable

}