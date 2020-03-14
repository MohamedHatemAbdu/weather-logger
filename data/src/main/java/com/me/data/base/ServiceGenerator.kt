package com.me.data.base

import okhttp3.OkHttpClient

import okhttp3.logging.HttpLoggingInterceptor

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory


object ServiceGenerator {

    private const val BASE_URL = "https://api.openweathermap.org"

    const val OPEN_WEATHER_APPID_KEY = "appid"
    const val OPEN_WEATHER_APPID_VAL = "784f57eefcbd29401826e9074bad9564"

    private val builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())


    private var retrofit = builder.build()


    private val logging = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(AppIdInterceptor())

    fun <S> createService(
        serviceClass: Class<S>?
        , debug: Boolean
    ): S {
        if (!httpClient.interceptors().contains(logging) && debug) {
            httpClient.addInterceptor(logging)
            builder.client(httpClient.build())
            retrofit = builder.build()
        }
        return retrofit.create(serviceClass)
    }
}