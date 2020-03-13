package com.me.data.weather.model

import com.squareup.moshi.Json


data class Wind(

    @field:Json(name = "deg")
    val deg: Int? = null,

    @field:Json(name = "speed")
    val speed: Double? = null
)