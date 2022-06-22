package com.practice.covid19.screens.status

import androidx.lifecycle.*
import com.practice.covid19.APIServices.CovidStatusAPI
import com.practice.covid19.model.Summary
import com.practice.covid19.network.APIClient
import com.practice.covid19.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeCovidViewModel : ViewModel() {

    private val _summaryLiveData = MutableLiveData(Resource.loading(Summary()))
    val summaryLiveData = _summaryLiveData as LiveData<Resource<Summary>>

    private val _summaryStateFlow = MutableStateFlow(Resource.loading(Summary()))
    val summaryStateFlow = _summaryStateFlow as StateFlow<Resource<Summary>>

    private val _summarySharedFlow = MutableSharedFlow<Resource<Summary>>()
    val summarySharedFlow = _summarySharedFlow as SharedFlow<Resource<Summary>>


    fun getCovidStatusStateFlowOn() {
        _summaryStateFlow.value = Resource.loading(null)
        viewModelScope.launch {
            getFlowStatus()
                .catch { e ->
                    e.message?.let {
                        _summaryStateFlow.emit(Resource.error(it))
                    }
                }.flowOn(Dispatchers.IO)
                .collectLatest {
                    it.takeIf { it.isSuccessful }?.let {
                        _summaryStateFlow.emit(Resource.success(it.body() as Summary))
                    } ?: kotlin.run {
                        _summaryStateFlow.emit(Resource.error("Error", null))
                    }
                }
        }

    }

    private fun getFlowStatus() = flow {
        emit(APIClient.createService(tClass = CovidStatusAPI::class.java).getCountriesSummary())
    }

    fun getCovidStatusSharedFlow() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _summarySharedFlow.emit(Resource.loading(null))
                val summaryResponse =
                    APIClient.createService(tClass = CovidStatusAPI::class.java)
                        .getCountriesSummary()
                summaryResponse.takeIf { it.isSuccessful }?.let {
                    _summarySharedFlow.emit(Resource.success(it.body() as Summary))
                } ?: kotlin.run {
                    _summarySharedFlow.emit(Resource.error("Error", null))
                }
            } catch (e: Exception) {
                _summarySharedFlow.emit(Resource.error(e.message ?: "Err", null))
            }
        }
    }

    fun getCovidStatusStateFlow() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _summaryStateFlow.emit(Resource.loading(null))
                val summaryResponse =
                    APIClient.createService(tClass = CovidStatusAPI::class.java)
                        .getCountriesSummary()
                summaryResponse.takeIf { it.isSuccessful }?.let {
                    _summaryStateFlow.emit(Resource.success(it.body() as Summary))
                } ?: kotlin.run {
                    _summaryStateFlow.emit(Resource.error("Error", null))
                }
            } catch (e: Exception) {
                _summaryStateFlow.value = Resource.error(e.message ?: "Err", null)
            }
        }
    }

    fun getCovidStatusLiveDataScope() = liveData {
        try {
            emit(Resource.loading(null))
            val summaryResponse =
                APIClient.createService(tClass = CovidStatusAPI::class.java).getCountriesSummary()
            summaryResponse.takeIf { it.isSuccessful }?.let {
                emit(Resource.success(it.body() as Summary))
            } ?: kotlin.run {
                emit(Resource.error("Error", null))
            }
        } catch (e: Exception) {
            emit(Resource.error(e.message ?: "Err"))
        }
    }

    fun getCovidStatusLiveData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _summaryLiveData.postValue(Resource.loading(null))
                val summaryResponse =
                    APIClient.createService(tClass = CovidStatusAPI::class.java)
                        .getCountriesSummary()
                summaryResponse.takeIf { it.isSuccessful }?.let {
                    _summaryLiveData.postValue(Resource.success(it.body() as Summary))
                } ?: kotlin.run {
                    _summaryLiveData.postValue(Resource.error("Error", null))
                }
            } catch (e: Exception) {
                _summaryLiveData.postValue(Resource.error(e.message ?: "Err", null))
            }
        }
    }
}
