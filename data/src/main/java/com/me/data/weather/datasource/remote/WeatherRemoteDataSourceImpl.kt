package com.me.data.weather.datasource.remote


import com.me.data.weather.model.mapToDomain
import com.me.data.weather.datasource.IWeatherRemoteDataSource
import com.me.data.weather.datasource.remote.api.IWeatherApi
import com.me.data.weather.model.mapToWeatherData
import com.me.domain.weather.entity.WeatherEntity
import io.reactivex.Single


class WeatherRemoteDataSourceImpl constructor(private val api: IWeatherApi) :
    IWeatherRemoteDataSource {

    override fun getWeatherLog(cityId: String): Single<WeatherEntity> =
        api.getWeatherLog(cityId).map {
            it.mapToWeatherData().mapToDomain()
        }


}