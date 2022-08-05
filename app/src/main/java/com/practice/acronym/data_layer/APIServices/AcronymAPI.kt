package com.practice.acronym.data_layer.APIServices

import com.practice.acronym.data_layer.model.Acronym
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AcronymAPI {
    @GET("acromine/dictionary.py")
    suspend fun getAcr(@Query("sf") sform: String): Response<List<Acronym>>
}
