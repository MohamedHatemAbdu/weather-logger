package com.me.data.weather.model

import com.squareup.moshi.Json


data class Clouds(

    @field:Json(name = "all")
    val all: Int? = null
)