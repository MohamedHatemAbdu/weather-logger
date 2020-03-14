package com.me.data.weather.datasource.local.dao

import androidx.room.*
import com.me.data.weather.model.WeatherData
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface IWeatherDao {

    @Query("Select * from weatherData")
    fun getAllWeatherLogs(): Flowable<List<WeatherData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllWeatherLogs(wetherLogs: List<WeatherData>)

    @Query("Select * from weatherData where dateCreated = :aDateCreated")
    fun getWeatherLog(aDateCreated: Long): Single<WeatherData>

    @Update
    fun updateWeatherLog(post: WeatherData): Completable

    //TODO : to refactor saveWeatherLog(weatherLog.mapToData()): make it asyncrounous
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveWeatherLog(weatherData: WeatherData)

    @Query("DELETE  FROM weatherData WHERE dateCreated = :aDateCreated ")
    fun deleteWeatherLog(aDateCreated: Long): Completable

    @Query("DELETE FROM weatherData")
    fun clear(): Completable
}