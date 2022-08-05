package com.practice.acronym.screens.status

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.acronym.APIServices.AcronymAPI
import com.practice.acronym.model.Acronym
import com.practice.acronym.network.APIClient
import com.practice.acronym.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AcromineViewModel : ViewModel() {

    private val _meaningStateFlow = MutableStateFlow(Resource.loading(ArrayList<Acronym>()))
    val meaningStateFlow = _meaningStateFlow as StateFlow<Resource<List<Acronym>>>

    fun getMeanings(acro: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _meaningStateFlow.emit(Resource.loading(null))
                val meanObj =
                    APIClient.createService(tClass = AcronymAPI::class.java)
                        .getAcr(acro)
                meanObj.takeIf { it.isSuccessful }?.let {
                    _meaningStateFlow.emit(Resource.success(it.body() as ArrayList<Acronym>))
                } ?: kotlin.run {
                    _meaningStateFlow.emit(Resource.error("Error", null))
                }
            } catch (e: Exception) {
                _meaningStateFlow.value = Resource.error(e.message ?: "Err", null)
            }
        }
    }
}
