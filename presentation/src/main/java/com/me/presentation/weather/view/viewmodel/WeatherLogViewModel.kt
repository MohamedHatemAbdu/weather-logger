package com.me.presentation.weather.view.viewmodel

import androidx.lifecycle.MutableLiveData
import com.me.domain.weather.entity.WeatherEntity
import com.me.domain.weather.usecase.WeatherUseCase
import com.me.presentation.base.extensions.setError
import com.me.presentation.base.extensions.setLoading
import com.me.presentation.base.extensions.setSuccess
import com.me.presentation.base.model.Resource
import com.me.presentation.base.viewmodel.BaseViewModel

import io.reactivex.schedulers.Schedulers

class WeatherLogViewModel constructor(private val weatherUseCase: WeatherUseCase) :
    BaseViewModel() {

    val weatherLogsList = MutableLiveData<Resource<List<WeatherEntity>>>()
    val weatherLog = MutableLiveData<Resource<WeatherEntity>>()

    fun getWeatherLogs() =
        compositeDisposable.add(
            weatherUseCase.getWeatherLogs()
                .doOnSubscribe { weatherLogsList.setLoading() }
                .subscribeOn(Schedulers.io())
                .subscribe({
                    weatherLogsList.setSuccess(it)
                }, {
                    weatherLogsList.setError(it.message)
                })
        )

    fun getRemoteWeatherLog(cityId: String) {
        compositeDisposable.add(
            weatherUseCase.getRemoteWeatherLog(
                cityId
            ).doOnSubscribe { weatherLog.setLoading() }
                .subscribeOn(Schedulers.io())
                .subscribe(

                    {
                        weatherLog.setSuccess(it)

                    }
                    , {
                        weatherLog.setError(it.message)
                    })
        )
    }

    fun getCachedWeatherLog(dateCreated: Long) {
        compositeDisposable.add(
            weatherUseCase.getCachedWeatherLog(
                dateCreated
            ).subscribeOn(Schedulers.io())
                .subscribe({ weatherLog.setSuccess(it) }, { })
        )
    }


    fun deletePost(dateCreated: Long) {
        compositeDisposable.add(
            weatherUseCase.deleteWeatherLog(
                dateCreated
            ).subscribeOn(Schedulers.io())
                .subscribe({}, {})
        )
    }


    fun clearAll() {
        compositeDisposable.add(
            weatherUseCase.clearAllWeatherLog(
            ).subscribeOn(Schedulers.io())
                .subscribe({}, {})
        )
    }


}