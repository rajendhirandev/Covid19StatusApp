package com.practice.covid19.screens.status

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.practice.covid19.APIServices.CovidStatusAPI
import com.practice.covid19.model.Summary
import com.practice.covid19.network.APIClient
import com.practice.covid19.network.Resource
import kotlinx.coroutines.Dispatchers

class HomeCovidViewModel : ViewModel() {

    fun getCovidStatus() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        val summaryResponse =
            APIClient.createService(tClass = CovidStatusAPI::class.java).getCountriesSummary()
        summaryResponse.let {
            if (it.isSuccessful) {
                emit(Resource.success(it.body() as Summary))
            } else {
                emit(Resource.error("Error"))
            }
        }
    }
}
