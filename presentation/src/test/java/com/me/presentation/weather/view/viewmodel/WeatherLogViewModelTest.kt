package com.me.presentation.weather.view.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.me.domain.weather.usecase.WeatherUseCase
import com.me.presentation.ConstValues.RIGA_LATVIA_CITY_ID
import com.me.presentation.RxSchedulersOverrideRule
import com.me.presentation.weatherLogEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.*

class WeatherLogViewModelTest {

    private lateinit var viewModel: WeatherLogViewModel

    private val mockWeatherUseCase: WeatherUseCase = mock(WeatherUseCase::class.java)

    private val weatherLogList = listOf(weatherLogEntity)

    private val dateCreated = weatherLogEntity.dateCreated

    private val throwable = Throwable()

    @Rule
    @JvmField
    val rxSchedulersOverrideRule = RxSchedulersOverrideRule()

    @Rule
    @JvmField
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = WeatherLogViewModel(mockWeatherUseCase)
    }

    @Test
    fun `get all weather Log cache succeeds`() {

        // given
        `when`(mockWeatherUseCase.getWeatherLogs())
            .thenReturn(Flowable.just(weatherLogList))

        // when
        viewModel.getWeatherLogs()

        // then
        verify(mockWeatherUseCase).getWeatherLogs()
        Assert.assertEquals(weatherLogList, viewModel.weatherLogsList.value?.data)
    }

    @Test
    fun `get all weather Log cache fails`() {

        // given
        `when`(mockWeatherUseCase.getWeatherLogs())
            .thenReturn(Flowable.error(throwable))

        // when
        viewModel.getWeatherLogs()

        // then
        verify(mockWeatherUseCase).getWeatherLogs()
        Assert.assertEquals(null, viewModel.weatherLogsList.value?.data)
    }


    @Test
    fun `get weather Log cache succeeds`() {

        // given
        `when`(mockWeatherUseCase.getCachedWeatherLog(dateCreated))
            .thenReturn(Single.just(weatherLogEntity))

        // when
        viewModel.getCachedWeatherLog(dateCreated)

        // then
        verify(mockWeatherUseCase).getCachedWeatherLog(dateCreated)
        Assert.assertEquals(weatherLogEntity, viewModel.weatherLog.value?.data)
    }

    @Test
    fun `get weather Log cache fails`() {

        // given
        `when`(mockWeatherUseCase.getCachedWeatherLog(dateCreated))
            .thenReturn(Single.error(throwable))

        // when
        viewModel.getCachedWeatherLog(dateCreated)

        // then
        verify(mockWeatherUseCase).getCachedWeatherLog(dateCreated)
        Assert.assertEquals(null, viewModel.weatherLog.value?.data)
    }


    @Test
    fun `get weather Log remote succeeds`() {

        // given
        `when`(mockWeatherUseCase.getRemoteWeatherLog(RIGA_LATVIA_CITY_ID))
            .thenReturn(Single.just(weatherLogEntity))

        // when
        viewModel.getRemoteWeatherLog(RIGA_LATVIA_CITY_ID)

        // then
        verify(mockWeatherUseCase).getRemoteWeatherLog(RIGA_LATVIA_CITY_ID)
        Assert.assertEquals(weatherLogEntity, viewModel.weatherLog.value?.data)
    }

    @Test
    fun `get weather Log remote fails`() {

        // given
        `when`(mockWeatherUseCase.getRemoteWeatherLog(RIGA_LATVIA_CITY_ID))
            .thenReturn(Single.error(throwable))

        // when
        viewModel.getRemoteWeatherLog(RIGA_LATVIA_CITY_ID)

        // then
        verify(mockWeatherUseCase).getRemoteWeatherLog(RIGA_LATVIA_CITY_ID)
        Assert.assertEquals(null, viewModel.weatherLog.value?.data)
    }


    @Test
    fun `delete weather log succeeds`() {

        // given
        `when`(mockWeatherUseCase.deleteWeatherLog(dateCreated))
            .thenReturn(Completable.complete())

        // when
        viewModel.deleteWeatherLog(dateCreated)

        // then
        verify(mockWeatherUseCase).deleteWeatherLog(dateCreated)
        Assert.assertEquals(true, viewModel.isDeletedWetherLog.value?.data)

    }


    @Test
    fun `delete weather log fails`() {

        // given
        `when`(mockWeatherUseCase.deleteWeatherLog(dateCreated))
            .thenReturn(Completable.error(throwable))

        // when
        viewModel.deleteWeatherLog(dateCreated)

        // then
        verify(mockWeatherUseCase).deleteWeatherLog(dateCreated)
        Assert.assertEquals(null, viewModel.isDeletedWetherLog.value?.data)

    }


}