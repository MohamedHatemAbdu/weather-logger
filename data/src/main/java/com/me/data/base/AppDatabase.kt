package com.me.data.base

import androidx.room.Database
import androidx.room.RoomDatabase
import com.me.data.weather.model.WeatherData
import com.me.data.weather.datasource.local.dao.IWeatherDao

@Database(entities = [WeatherData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getWeatherDao(): IWeatherDao
}