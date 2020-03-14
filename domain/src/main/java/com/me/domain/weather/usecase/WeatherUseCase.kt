package com.me.domain.weather.usecase


import com.me.domain.weather.entity.WeatherEntity
import com.me.domain.weather.repository.IWeatherRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single


class WeatherUseCase constructor(
    private val weatherRepository: IWeatherRepository
) {

    fun getWeatherLogs(): Flowable<List<WeatherEntity>> =
        weatherRepository.getWeatherLogs()

    fun getRemoteWeatherLog(cityId: String): Single<WeatherEntity> =
        weatherRepository.getRemoteWeatherLog(cityId)

    fun getCachedWeatherLog(dataCreated: Long): Single<WeatherEntity> =
        weatherRepository.getCachedWeatherLog(dataCreated)

    fun editWeatherLog(WeatherLog: WeatherEntity): Completable =
        weatherRepository.editWeatherLog(WeatherLog)

    fun addWeatherLog(WeatherLog: WeatherEntity): Single<WeatherEntity> =
        weatherRepository.addWeatherLog(WeatherLog)

    fun deleteWeatherLog(dataCreated: Long): Completable =
        weatherRepository.deleteWeatherLog(dataCreated)

    fun clearAllWeatherLog(): Completable =
        weatherRepository.clearAllWeatherLog()


}


