package com.me.data.weather.model

import com.squareup.moshi.Json


data class Sys(

	   @field:Json(name = "country")
	val country: String? = null,

	   @field:Json(name = "sunrise")
	val sunrise: Int? = null,

	   @field:Json(name = "sunset")
	val sunset: Int? = null,

	   @field:Json(name = "id")
	val id: Int? = null,

	   @field:Json(name = "type")
	val type: Int? = null,

	   @field:Json(name = "message")
	val message: Double? = null
)