package com.me.domain.usecase


import com.me.domain.ConstValues.RIGA_LATVIA_CITY_ID
import com.me.domain.weather.repository.IWeatherRepository
import com.me.domain.weather.usecase.WeatherUseCase
import com.me.domain.weatherLogEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*


class WeatherUseCaseTest {

    private lateinit var weatherUseCase: WeatherUseCase
    private val mockWeatherRepository: IWeatherRepository = mock(IWeatherRepository::class.java)

    private val weatherLogList = listOf(weatherLogEntity)

    private val dateCreated = weatherLogEntity.dateCreated

    val throwable = Throwable()

    @Before
    fun setUp() {
        weatherUseCase = WeatherUseCase(mockWeatherRepository)
    }

    @Test
    fun `repository get all weather log success`() {
        // given
        `when`(mockWeatherRepository.getWeatherLogs()).thenReturn(Flowable.just(weatherLogList))

        // when
        val test = weatherUseCase.getWeatherLogs().test()

        // test
        verify(mockWeatherRepository).getWeatherLogs()

        test.assertNoErrors()
        test.assertComplete()
        test.assertValueCount(1)
        test.assertValue(weatherLogList)
    }

    @Test
    fun `get all weather log fail`() {
        // given
        `when`(mockWeatherRepository.getWeatherLogs()).thenReturn(Flowable.error(throwable))

        // when
        val test = weatherUseCase.getWeatherLogs().test()

        //
        // then
        verify(mockWeatherRepository).getWeatherLogs()

        test.assertNoValues()
        test.assertNotComplete()
        test.assertError(throwable)
        test.assertValueCount(0)
    }

    @Test
    fun `get remote weather log success`() {
        // given
        `when`(mockWeatherRepository.getRemoteWeatherLog(RIGA_LATVIA_CITY_ID)).thenReturn(Single.just(
            weatherLogEntity))

        // when
        val test = weatherUseCase.getRemoteWeatherLog(RIGA_LATVIA_CITY_ID).test()

        // test
        verify(mockWeatherRepository).getRemoteWeatherLog(RIGA_LATVIA_CITY_ID)

        test.assertNoErrors()
        test.assertComplete()
        test.assertValueCount(1)
        test.assertValue(weatherLogEntity)
    }

    @Test
    fun `get remote weather log fail`() {
        // given
        `when`(mockWeatherRepository.getRemoteWeatherLog(RIGA_LATVIA_CITY_ID)).thenReturn(Single.error(throwable))

        // when
        val test = weatherUseCase.getRemoteWeatherLog(RIGA_LATVIA_CITY_ID).test()

        //
        // then
        verify(mockWeatherRepository).getRemoteWeatherLog(RIGA_LATVIA_CITY_ID)

        test.assertNoValues()
        test.assertNotComplete()
        test.assertError(throwable)
        test.assertValueCount(0)
    }

    @Test
    fun `get cache weather log success`() {
        // given
        `when`(mockWeatherRepository.getCachedWeatherLog(dateCreated)).thenReturn(Single.just(
            weatherLogEntity))

        // when
        val test = weatherUseCase.getCachedWeatherLog(dateCreated).test()

        // test
        verify(mockWeatherRepository).getCachedWeatherLog(dateCreated)

        test.assertNoErrors()
        test.assertComplete()
        test.assertValueCount(1)
        test.assertValue(weatherLogEntity)
    }

    @Test
    fun `get cache weather log fail`() {
        // given
        `when`(mockWeatherRepository.getCachedWeatherLog(dateCreated)).thenReturn(Single.error(throwable))

        // when
        val test = weatherUseCase.getCachedWeatherLog(dateCreated).test()

        //
        // then
        verify(mockWeatherRepository).getCachedWeatherLog(dateCreated)

        test.assertNoValues()
        test.assertNotComplete()
        test.assertError(throwable)
        test.assertValueCount(0)
    }

    @Test
    fun `edit cache weather log success`() {
        // given
        `when`(mockWeatherRepository.editWeatherLog(weatherLogEntity)).thenReturn(Completable.complete())

        // when
        val test = weatherUseCase.editWeatherLog(weatherLogEntity).test()

        // test
        verify(mockWeatherRepository).editWeatherLog(weatherLogEntity)

        test.assertNoErrors()
        test.assertComplete()
        test.assertValueCount(0)
    }

    @Test
    fun `edit cache weather log fail`() {
        // given
        `when`(mockWeatherRepository.editWeatherLog(weatherLogEntity)).thenReturn(Completable.error(throwable))

        // when
        val test = weatherUseCase.editWeatherLog(weatherLogEntity).test()

        //
        // then
        verify(mockWeatherRepository).editWeatherLog(weatherLogEntity)

        test.assertNoValues()
        test.assertNotComplete()
        test.assertError(throwable)
        test.assertValueCount(0)
    }


    @Test
    fun `add cache weather log success`() {
        // given
        `when`(mockWeatherRepository.addWeatherLog(weatherLogEntity)).thenReturn(Single.just(weatherLogEntity))

        // when
        val test = weatherUseCase.addWeatherLog(weatherLogEntity).test()

        // test
        verify(mockWeatherRepository).addWeatherLog(weatherLogEntity)

        test.assertNoErrors()
        test.assertComplete()
        test.assertValueCount(1)
    }

    @Test
    fun `add cache weather log fail`() {
        // given
        `when`(mockWeatherRepository.addWeatherLog(weatherLogEntity)).thenReturn(Single.error(throwable))

        // when
        val test = weatherUseCase.addWeatherLog(weatherLogEntity).test()

        //
        // then
        verify(mockWeatherRepository).addWeatherLog(weatherLogEntity)

        test.assertNoValues()
        test.assertNotComplete()
        test.assertError(throwable)
        test.assertValueCount(0)
    }

    @Test
    fun `delete cache weather log success`() {
        // given
        `when`(mockWeatherRepository.deleteWeatherLog(dateCreated)).thenReturn(Completable.complete())

        // when
        val test = weatherUseCase.deleteWeatherLog(dateCreated).test()

        // test
        verify(mockWeatherRepository).deleteWeatherLog(dateCreated)

        test.assertNoErrors()
        test.assertComplete()
        test.assertValueCount(0)
    }

    @Test
    fun `delete  cache weather log fail`() {
        // given
        `when`(mockWeatherRepository.deleteWeatherLog(dateCreated)).thenReturn(Completable.error(throwable))

        // when
        val test = weatherUseCase.deleteWeatherLog(dateCreated).test()

        //
        // then
        verify(mockWeatherRepository).deleteWeatherLog(dateCreated)

        test.assertNoValues()
        test.assertNotComplete()
        test.assertError(throwable)
        test.assertValueCount(0)
    }
}

