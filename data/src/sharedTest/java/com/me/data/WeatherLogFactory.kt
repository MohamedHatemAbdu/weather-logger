package com.me.data

import com.me.data.DataFactory.Factory.randomDouble
import com.me.data.DataFactory.Factory.randomLong
import com.me.data.DataFactory.Factory.randomUuid
import com.me.data.weather.model.WeatherData

class WeatherLogFactory {
    object Factory {

        fun makeWeatherLogData(): WeatherData {
            return WeatherData(randomUuid(), randomUuid(), randomUuid(), randomDouble(), randomLong().toString(), randomLong())
        }

        fun makeWeatherLogData(dateCreated: Long): WeatherData {
            return WeatherData(randomUuid(), randomUuid(), randomUuid(), randomDouble(), randomLong().toString(), dateCreated)
        }


        fun makeWeatherLogDataList(count: Int): List<WeatherData> {
            val weatherDataList = mutableListOf<WeatherData>()
            repeat(count) {
                weatherDataList.add(makeWeatherLogData())
            }
            return weatherDataList
        }

        fun makeWeatherDataListSameIdsDiffCont(weatherDataList: List<WeatherData>): List<WeatherData> {
            val copiedWeatherDataList = mutableListOf<WeatherData>()
            weatherDataList.map { copiedWeatherDataList.add(makeWeatherLogData(it.dateCreated)) }
            return copiedWeatherDataList
        }


    }
}