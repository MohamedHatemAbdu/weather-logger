package com.me.data.weather.model

import com.squareup.moshi.Json


data class Coord(

           @field:Json(name = "lon")
        val lon: Double? = null,

           @field:Json(name = "lat")
        val lat: Double? = null
)