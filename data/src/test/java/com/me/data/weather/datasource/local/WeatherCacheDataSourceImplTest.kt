package com.me.data.weather.datasource.local

import com.me.data.base.AppDatabase
import com.me.data.weather.datasource.local.dao.IWeatherDao
import com.me.data.weather.model.mapToDomain
import com.me.data.weatherLogData


import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class WeatherCacheDataSourceImplTest {

    private lateinit var dataSource: WeatherCacheDataSourceImpl

    private var mockedDatabase: AppDatabase = mock(AppDatabase::class.java)
    private var mockedWeatherDao: IWeatherDao = mock(IWeatherDao::class.java)

    private val dateCreated = weatherLogData.dateCreated

    private val cacheItem = weatherLogData.copy(name = "cached")
    private val cacheList = listOf(cacheItem)


    private val throwable = Throwable()

    @Before
    fun setUp() {
        `when`(mockedDatabase.getWeatherDao()).thenReturn(mockedWeatherDao)
        dataSource = WeatherCacheDataSourceImpl(mockedDatabase)
    }

    @Test
    fun `get all weatherLogs cache success`() {
        // given
        `when`(mockedWeatherDao.getAllWeatherLogs()).thenReturn(Flowable.just(cacheList))

        // when
        val test = dataSource.getWeatherLogs().test()

        // then
        verify(mockedWeatherDao).getAllWeatherLogs()
        test.assertValue(cacheList.mapToDomain())
    }

    @Test
    fun `get all weatherLogs cache fail`() {
        // given
        `when`(mockedWeatherDao.getAllWeatherLogs()).thenReturn(Flowable.error(throwable))

        // when
        val test = dataSource.getWeatherLogs().test()

        // then
        verify(mockedWeatherDao).getAllWeatherLogs()
        test.assertError(throwable)
    }

    @Test
    fun `set all weatherLogs cache success`() {
        // given
        `when`(mockedWeatherDao.saveAllWeatherLogs(cacheList)).thenReturn(listOf())
        `when`(mockedWeatherDao.getAllWeatherLogs()).thenReturn(Flowable.just(cacheList))

        // when
        val test = dataSource.setWeatherLogs(cacheList.mapToDomain()).test()

        // then
        // then
        verify(mockedWeatherDao).saveAllWeatherLogs(cacheList)
        verify(mockedWeatherDao).getAllWeatherLogs()

        test.assertValue(cacheList.mapToDomain())
        test.assertNoErrors()

    }

    @Test
    fun `set all weatherLogs cache fail`() {
        // given
        `when`(mockedWeatherDao.saveAllWeatherLogs(cacheList)).thenReturn(listOf())
        `when`(mockedWeatherDao.getAllWeatherLogs()).thenReturn(Flowable.error(throwable))

        // when
        val test = dataSource.setWeatherLogs(cacheList.mapToDomain()).test()

        // then
        // then
        verify(mockedWeatherDao).saveAllWeatherLogs(cacheList)
        verify(mockedWeatherDao).getAllWeatherLogs()

        test.assertError(throwable)
    }

    @Test
    fun `get weatherLog by date cache success`() {
        // given
        `when`(mockedWeatherDao.getWeatherLog(dateCreated)).thenReturn(Single.just(cacheItem))

        // when
        val test = dataSource.getWeatherLog(dateCreated).test()

        // then
        verify(mockedWeatherDao).getWeatherLog(dateCreated)
        test.assertValue(cacheItem.mapToDomain())
    }

    @Test
    fun `get weatherLog by date cache fail`() {
        // given
        `when`(mockedWeatherDao.getWeatherLog(dateCreated)).thenReturn(Single.error(throwable))

        // when
        val test = dataSource.getWeatherLog(dateCreated).test()

        // then
        verify(mockedWeatherDao).getWeatherLog(dateCreated)
        test.assertError(throwable)
    }



    @Test
    fun `edit weatherLog cache success`() {
        // given
        `when`(mockedWeatherDao.updateWeatherLog(cacheItem)).thenReturn(Completable.complete())

        // when
        val test = dataSource.editWeatherLog(cacheItem.mapToDomain()).test()

        // then
        verify(mockedWeatherDao).updateWeatherLog(cacheItem)
        test.assertComplete()
        test.assertNoErrors()
    }

    @Test
    fun `edit weatherLog cache fail`() {
        // given
        `when`(mockedWeatherDao.updateWeatherLog(cacheItem)).thenReturn(Completable.error(throwable))

        // when
        val test = dataSource.editWeatherLog(cacheItem.mapToDomain()).test()

        // then

        verify(mockedWeatherDao).updateWeatherLog(cacheItem)
        test.assertError(throwable)
    }


    @Test
    fun `add weatherLog cache success`() {
        // given
        `when`(mockedWeatherDao.saveWeatherLog(cacheItem)).thenReturn(1)
        `when`(mockedWeatherDao.getWeatherLog(cacheItem.dateCreated)).thenReturn(
            Single.just(
                cacheItem
            )
        )

        // when
        val test = dataSource.addWeatherLog(cacheItem.mapToDomain()).test()

        // then
        verify(mockedWeatherDao).saveWeatherLog(cacheItem)
        verify(mockedWeatherDao).getWeatherLog(cacheItem.dateCreated)

        test.assertComplete()
        test.assertNoErrors()
    }

    @Test
    fun `add weatherLog cache fail`() {
        // given
        `when`(mockedWeatherDao.saveWeatherLog(cacheItem)).thenReturn(0)
        `when`(mockedWeatherDao.getWeatherLog(cacheItem.dateCreated)).thenReturn(
            Single.error(
                throwable
            )
        )


        // when
        val test = dataSource.addWeatherLog(cacheItem.mapToDomain()).test()

        // then
        verify(mockedWeatherDao).saveWeatherLog(cacheItem)
        verify(mockedWeatherDao).getWeatherLog(cacheItem.dateCreated)

        test.assertError(throwable)
    }





    @Test
    fun `delete weatherLog cache success`() {
        // given
        `when`(mockedWeatherDao.deleteWeatherLog(dateCreated)).thenReturn(Completable.complete())

        // when
        val test = dataSource.deleteWeatherLog(dateCreated).test()

        // then
        verify(mockedWeatherDao).deleteWeatherLog(dateCreated)
        test.assertComplete()
        test.assertNoErrors()
    }

    @Test
    fun `delete weatherLog cache fail`() {
        // given
        `when`(mockedWeatherDao.deleteWeatherLog(dateCreated)).thenReturn(
            Completable.error(
                throwable
            )
        )

        // when
        val test = dataSource.deleteWeatherLog(dateCreated).test()

        // then
        verify(mockedWeatherDao).deleteWeatherLog(dateCreated)
        test.assertError(throwable)
    }

}