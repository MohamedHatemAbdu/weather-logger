package com.me.data.weather.repository

import com.me.data.ConstValues.RIGA_LATVIA_CITY_ID
import com.me.data.weather.datasource.local.WeatherCacheDataSourceImpl
import com.me.data.weather.datasource.remote.WeatherRemoteDataSourceImpl
import com.me.data.weatherLogData
import com.me.data.weatherLogEntity

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class WeatherRepositoryImplTest {

    private lateinit var repository: WeatherRepositoryImpl

    private val mockCacheDataSource: WeatherCacheDataSourceImpl = mock(WeatherCacheDataSourceImpl::class.java)
    private val mockRemoteDataSource: WeatherRemoteDataSourceImpl = mock(WeatherRemoteDataSourceImpl::class.java)

    private val dateCreated = weatherLogData.dateCreated

    private val cacheItem = weatherLogEntity.copy(name = "cache")
    private val remoteItem = weatherLogEntity.copy(name = "remote")

    private val cacheList = listOf(cacheItem)


    private val throwable = Throwable()

    @Before
    fun setUp() {
        repository = WeatherRepositoryImpl(mockCacheDataSource, mockRemoteDataSource)
    }


    @Test
    fun `get all weatherLogs cache success`() {
        // given
        `when`(mockCacheDataSource.getWeatherLogs()).thenReturn(Flowable.just(cacheList))

        // when
        val test = mockCacheDataSource.getWeatherLogs().test()

        // then
        verify(mockCacheDataSource).getWeatherLogs()
        test.assertValue(cacheList)
    }

    @Test
    fun `get all weatherLogs cache fail`() {
        // given
        `when`(mockCacheDataSource.getWeatherLogs()).thenReturn(Flowable.error(throwable))

        // when
        val test = mockCacheDataSource.getWeatherLogs().test()

        // then
        verify(mockCacheDataSource).getWeatherLogs()
        test.assertError(throwable)
    }

    @Test
    fun `get weather log remote success`() {
        // given
        `when`(mockRemoteDataSource.getWeatherLog(RIGA_LATVIA_CITY_ID)).thenReturn(
            Single.just(
                remoteItem
            )
        )

        `when`(mockCacheDataSource.addWeatherLog(remoteItem)).thenReturn(
            Single.just(
                remoteItem
            )
        )

        // when
        val test = repository.getRemoteWeatherLog(RIGA_LATVIA_CITY_ID).test()

        // then
        verify(mockRemoteDataSource).getWeatherLog(RIGA_LATVIA_CITY_ID)
        test.assertValue(remoteItem)
    }

    @Test
    fun `get weather log remote fail`() {

        // given
        `when`(mockRemoteDataSource.getWeatherLog(RIGA_LATVIA_CITY_ID))
            .thenReturn(Single.error(throwable))

        // when
        val test = repository.getRemoteWeatherLog(RIGA_LATVIA_CITY_ID).test()

        // then
        verify(mockRemoteDataSource).getWeatherLog(RIGA_LATVIA_CITY_ID)
        test.assertError(throwable)
    }

    @Test
    fun `get weather log cache success`() {
        // given
        `when`(mockCacheDataSource.getWeatherLog(dateCreated)).thenReturn(Single.just(cacheItem))

        // when
        val test = repository.getCachedWeatherLog(dateCreated).test()

        // then
        verify(mockCacheDataSource).getWeatherLog(dateCreated)
        test.assertValue(cacheItem)
    }

    @Test
    fun `get weather log cache fail`() {

        // given
        `when`(mockCacheDataSource.getWeatherLog(dateCreated)).thenReturn(
            Single.error(
                throwable
            )
        )

        // when
        val test = repository.getCachedWeatherLog(dateCreated).test()

        // then
        verify(mockCacheDataSource).getWeatherLog(dateCreated)
        test.assertError(throwable)
    }

    @Test
    fun `edit weather log cache success`() {
        // given
        `when`(mockCacheDataSource.editWeatherLog(cacheItem)).thenReturn(Completable.complete())

        // when
        val test = repository.editWeatherLog(cacheItem).test()

        // then
        verify(mockCacheDataSource).editWeatherLog(cacheItem)
        test.assertComplete()
    }

    @Test
    fun `edit weather log cache fail`() {

        // given
        `when`(mockCacheDataSource.editWeatherLog(cacheItem)).thenReturn(
            Completable.error(
                throwable
            )
        )

        // when
        val test = repository.editWeatherLog(cacheItem).test()

        // then
        verify(mockCacheDataSource).editWeatherLog(cacheItem)
        test.assertError(throwable)
    }

    @Test
    fun `add weather log cache success`() {
        // given
        `when`(mockCacheDataSource.addWeatherLog(cacheItem)).thenReturn(Single.just(cacheItem))

        // when
        val test = repository.addWeatherLog(cacheItem).test()

        // then
        verify(mockCacheDataSource).addWeatherLog(cacheItem)
        test.assertValue(cacheItem)
        test.assertComplete()
    }

    @Test
    fun `add weather log cache fail`() {

        // given
        `when`(mockCacheDataSource.addWeatherLog(cacheItem)).thenReturn(Single.error(throwable))

        // when
        val test = repository.addWeatherLog(cacheItem).test()

        // then
        verify(mockCacheDataSource).addWeatherLog(cacheItem)
        test.assertError(throwable)
    }

    @Test
    fun `delete weather log cache success`() {
        // given
        `when`(mockCacheDataSource.deleteWeatherLog(dateCreated)).thenReturn(Completable.complete())

        // when
        val test = repository.deleteWeatherLog(dateCreated).test()

        // then
        verify(mockCacheDataSource).deleteWeatherLog(dateCreated)
        test.assertComplete()
    }

    @Test
    fun `delete weather log cache fail`() {

        // given
        `when`(mockCacheDataSource.deleteWeatherLog(dateCreated)).thenReturn(
            Completable.error(
                throwable
            )
        )

        // when
        val test = repository.deleteWeatherLog(dateCreated).test()

        // then
        verify(mockCacheDataSource).deleteWeatherLog(dateCreated)
        test.assertError(throwable)
    }

}