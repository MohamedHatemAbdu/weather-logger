package com.me.presentation.weather.model

import androidx.databinding.ObservableField
import com.me.domain.weather.entity.WeatherEntity
import java.util.*

data class WeatherModel(
    val name: ObservableField<String> = ObservableField(""),
    val country: ObservableField<String> = ObservableField(""),
    val description: ObservableField<String> = ObservableField(""),
    val temp: ObservableField<Double> = ObservableField(0.0),
    val dt: ObservableField<String> = ObservableField(""),
    val dateCreated: ObservableField<Long> = ObservableField(Date().time)
)


fun WeatherEntity.mapToPresentation(): WeatherModel =
    WeatherModel(
        ObservableField(name),
        ObservableField(country),
        ObservableField(description),
        ObservableField(temp),
        ObservableField(dt),
        ObservableField(dateCreated)
    )

fun List<WeatherEntity>.mapToPresentation(): List<WeatherModel> = map { it.mapToPresentation() }
