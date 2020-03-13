package com.me.data.weather.model

import com.squareup.moshi.Json


data class WeatherItem(

	   @field:Json(name = "icon")
	val icon: String? = null,

	   @field:Json(name = "description")
	val description: String? = null,

	   @field:Json(name = "main")
	val main: String? = null,

	   @field:Json(name = "id")
	val id: Int? = null
)