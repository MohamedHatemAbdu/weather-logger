package com.me.data.base

import com.me.data.base.ServiceGenerator.OPEN_WEATHER_APPID_VAL
import com.me.data.base.ServiceGenerator.OPEN_WEATHER_APPID_KEY
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


class AppIdInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val originalHttpUrl: HttpUrl = original.url

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter(OPEN_WEATHER_APPID_KEY, OPEN_WEATHER_APPID_VAL)
            .build()

        val requestBuilder: Request.Builder = original.newBuilder()
            .url(url)

        val request: Request = requestBuilder.build()
        return chain.proceed(request)
    }

}