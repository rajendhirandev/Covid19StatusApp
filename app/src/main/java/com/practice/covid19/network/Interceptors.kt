package com.practice.covid19.network

import okhttp3.ConnectionPool
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor

/*
Author: Rajendhiran Easu
Date: 05-May-20
*/
object Interceptors {

    fun headerInterceptors(headers: Map<String, String>?) = Interceptor {
        val requestBuilder = it.request().newBuilder()
        headers?.forEach { data ->
            requestBuilder.addHeader(data.key, data.value)
        }
        it.proceed(requestBuilder.build())
    }

    fun connectionAliveInterceptor(connectionPool: ConnectionPool) = Interceptor {
        val request = it.request()
        val response: Response = it.proceed(request)
        if (response.code == NetworkConstant.DNS_UNAVAILABLE) {
            connectionPool.evictAll()
            it.proceed(it.request())
        } else {
            response
        }
    }

    fun logInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}
