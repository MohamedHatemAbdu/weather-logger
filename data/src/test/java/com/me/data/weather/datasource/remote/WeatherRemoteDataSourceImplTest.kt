package com.me.data.weather.datasource.remote


import com.me.data.ConstValues.RIGA_LATVIA_CITY_ID
import com.me.data.weather.datasource.remote.api.IWeatherApi
import com.me.data.weather.model.mapToDomain
import com.me.data.weather.model.mapToWeatherData
import com.me.data.weatherLogEntity
import com.me.data.weatherResponse
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*


class WeatherRemoteDataSourceImplTest {

    private lateinit var dataSource: WeatherRemoteDataSourceImpl

    private val mockApi: IWeatherApi = mock(IWeatherApi::class.java)

    private val remoteWeatherResponse = weatherResponse.copy(name = "remote")

    private val remoteItem = weatherLogEntity.copy(name = "remote")

    private val throwable = Throwable()


    @Before
    fun setUp() {
        dataSource = WeatherRemoteDataSourceImpl(mockApi)

    }

    @Test
    fun `get weather log remote success`() {
        // given
        `when`(mockApi.getWeatherLog(RIGA_LATVIA_CITY_ID)).thenReturn(
            Single.just(
                remoteWeatherResponse
            )
        )

        // when
        val test = dataSource.getWeatherLog(RIGA_LATVIA_CITY_ID).test()

        // then
        verify(mockApi).getWeatherLog(RIGA_LATVIA_CITY_ID)
        assert(test.values()[0].name == remoteItem.name)
    }

    @Test
    fun `get weather log remote fail`() {
        // given
        `when`(mockApi.getWeatherLog(RIGA_LATVIA_CITY_ID)).thenReturn(Single.error(throwable))

        // when
        val test = dataSource.getWeatherLog(RIGA_LATVIA_CITY_ID).test()

        // then
        verify(mockApi).getWeatherLog(RIGA_LATVIA_CITY_ID)
        test.assertError(throwable)
    }

}