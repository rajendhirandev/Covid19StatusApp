package com.practice.covid19.APIServices

import com.practice.covid19.model.Summary
import retrofit2.Response
import retrofit2.http.GET

/*
Author: Rajendhiran Easu
Date: 06-May-20
*/
interface CovidStatusAPI {
    @GET("/summary")
    suspend fun getCountriesSummary(): Response<Summary>
}
