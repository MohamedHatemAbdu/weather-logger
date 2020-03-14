package com.me.data.weather.datasource.local


import com.me.data.base.AppDatabase
import com.me.data.weather.model.mapToData
import com.me.data.weather.model.mapToDomain
import com.me.data.weather.datasource.IWeatherCacheDataSource
import com.me.data.weather.datasource.local.dao.IWeatherDao
import com.me.domain.weather.entity.WeatherEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single


class WeatherCacheDataSourceImpl(private val database: AppDatabase) : IWeatherCacheDataSource {

    private val dao: IWeatherDao = database.getWeatherDao()

    override fun getWeatherLogs(): Flowable<List<WeatherEntity>> =
        dao.getAllWeatherLogs().map { it.mapToDomain() }

    override fun setWeatherLogs(weatherLogList: List<WeatherEntity>): Flowable<List<WeatherEntity>> {
        dao.clear()
        dao.saveAllWeatherLogs(weatherLogList.mapToData())
        return getWeatherLogs()
    }

    override fun getWeatherLog(dateCreated: Long): Single<WeatherEntity> =
        dao.getWeatherLog(dateCreated).map {
            it.mapToDomain()
        }

    //TODO : to refactor dao.saveWeatherLog(weatherLog.mapToData()): make it asyncrounous
    override fun addWeatherLog(weatherLog: WeatherEntity): Single<WeatherEntity> {
        val dateCreated = weatherLog.dateCreated
        dao.saveWeatherLog(weatherLog.mapToData())
        return dao.getWeatherLog(dateCreated).map { it.mapToDomain() }
    }

    override fun editWeatherLog(weatherLog: WeatherEntity): Completable =
        dao.updateWeatherLog(weatherLog.mapToData())

    override fun deleteWeatherLog(dateCreated: Long): Completable =
        dao.deleteWeatherLog(dateCreated)

    override fun clearAllWeatherLog(): Completable =
        dao.clear()


}