package com.me.data.weather.datasource.remote.api

import com.me.data.weather.model.WeatherResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface IWeatherApi {

    @GET("/data/2.5/weather")
    fun getWeatherLog(@Query("id") cityId: String): Single<WeatherResponse>

}
