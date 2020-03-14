package com.me.domain.weather.entity

import java.util.*

data class WeatherEntity(
    val name: String = "",
    val country: String = "",
    val description: String = "",
    val temp: Double = 0.0,
    val dt: String = "",
    val dateCreated: Long = Date().time
)