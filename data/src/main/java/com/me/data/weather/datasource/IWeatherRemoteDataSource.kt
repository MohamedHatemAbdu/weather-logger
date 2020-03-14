package com.me.data.weather.datasource

import com.me.domain.weather.entity.WeatherEntity
import io.reactivex.Single


interface IWeatherRemoteDataSource {

    fun getWeatherLog(cityId: String): Single<WeatherEntity>
}