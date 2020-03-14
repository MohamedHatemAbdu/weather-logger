package com.me.data.weather.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.me.domain.weather.entity.WeatherEntity
import java.util.*

@Entity(tableName = "weatherData")
data class WeatherData(
    val name: String = "",
    val country: String = "",
    val description: String = "",
    val temp: Double = 0.0,
    val dt: String = "",
    @PrimaryKey(autoGenerate = true) val dateCreated: Long = Date().time
)

fun WeatherData.mapToDomain(): WeatherEntity =
    WeatherEntity(name, country, description, temp, dt, dateCreated)

fun List<WeatherData>.mapToDomain(): List<WeatherEntity> = map { it.mapToDomain() }

fun WeatherEntity.mapToData(): WeatherData =
    WeatherData(
        name,
        country,
        description,
        temp,
        dt,
        dateCreated
    )

fun List<WeatherEntity>.mapToData(): List<WeatherData> = map { it.mapToData() }
