package com.me.data.weather.model

import com.me.data.weatherLogData
import org.junit.Test

class WeatherDataTest {


    @Test
    fun `map data to domain`() {
        // given

        // when
        val model = weatherLogData.mapToDomain()

        // then
        assert(model.name == weatherLogData.name)
        assert(model.country == weatherLogData.country)
        assert(model.description == weatherLogData.description)
        assert(model.temp == weatherLogData.temp)
        assert(model.dt == weatherLogData.dt)
        assert(model.dateCreated == weatherLogData.dateCreated)


    }
}