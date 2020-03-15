package com.me.data

import com.me.data.weather.model.*
import com.me.domain.weather.entity.WeatherEntity

val weatherLogData = WeatherData(
    "WetherLog",
    "Latvia",
    "Weather log",
    30.0,
    "123",
    123
)

val weatherLogEntity = WeatherEntity(
    "WetherLog",
    "Latvia",
    "Weather log",
    30.0,
    "123",
    123
)
//fun WeatherResponse.mapToWeatherData(): WeatherData =
//    WeatherData(
//        name ?: "",
//        sys?.country ?: "",
//        weather?.get(0)?.description ?: "",
//        main?.temp ?: 0.0,
//        dt.toString()
//    )

val weatherResponse = WeatherResponse(
    123,
    null,
    null,
    listOf(WeatherItem(description = "Weather log")),
    "WetherLog",
    123,
    main = Main(temp = 30.0),
    sys = Sys(country = "Latvia")
)




