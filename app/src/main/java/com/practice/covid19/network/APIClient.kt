package com.practice.covid19.network

import com.google.gson.GsonBuilder
import com.practice.covid19.network.NetworkConstant.CONNECTION_TIMEOUT
import com.practice.covid19.network.NetworkConstant.READ_TIMEOUT
import com.practice.covid19.network.NetworkConstant.WRITE_TIMEOUT
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object APIClient {

    //private const val BASE_URL = "https://api.covid19api.com"
    private const val BASE_URL = "http://www.nactem.ac.uk/software/"

    private val connectionPool by lazy {
        ConnectionPool()
    }

    private val apiClient by lazy {
        OkHttpClient()
    }

    init {
        apiClient.newBuilder()
            .connectionPool(connectionPool)
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(Interceptors.connectionAliveInterceptor(connectionPool))
            .addNetworkInterceptor(Interceptors.logInterceptor())
    }

    private val retrofitBuilder by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
    }

    fun <T> createService(
        baseUrl: String = BASE_URL,
        headers: Map<String, String>? = null,
        tClass: Class<T>
    ): T {

        val client = apiClient.newBuilder()
            .addInterceptor(Interceptors.headerInterceptors(headers))
            .build()

        retrofitBuilder.baseUrl(baseUrl)
        retrofitBuilder.client(client)

        return retrofitBuilder.build().create(tClass)
    }

    fun cancelAll() {
        try {
            apiClient.dispatcher.cancelAll()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
