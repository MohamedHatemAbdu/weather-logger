package com.me.data.weather.model

import androidx.room.Entity
import com.squareup.moshi.Json


data class WeatherResponse(

    @field:Json(name = "dt")
    val dt: Int? = null,

    @field:Json(name = "coord")
    val coord: Coord? = null,

    @field:Json(name = "visibility")
    val visibility: Int? = null,

    @field:Json(name = "weather")
    val weather: List<WeatherItem?>? = null,

    @field:Json(name = "name")
    val name: String? = null,

    @field:Json(name = "cod")
    val cod: Int? = null,

    @field:Json(name = "main")
    val main: Main? = null,

    @field:Json(name = "clouds")
    val clouds: Clouds? = null,

    @field:Json(name = "id")
    val id: Int? = null,

    @field:Json(name = "sys")
    val sys: Sys? = null,

    @field:Json(name = "base")
    val base: String? = null,

    @field:Json(name = "wind")
    val wind: Wind? = null
)

fun WeatherResponse.mapToWeatherData(): WeatherData =
    WeatherData(
        name ?: "",
        sys?.country ?: "",
        weather?.get(0)?.description ?: "",
        main?.temp ?: 0.0,
        dt.toString()
    )

