package com.kelompok5.ecotech.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompok5.ecotech.data.model.response.orders.OrdersEwasteResponse
import com.kelompok5.ecotech.data.model.response.orders.UpdateStatusOrdersResponse
import com.kelompok5.ecotech.data.remote.ApiService
import com.kelompok5.ecotech.data.repository.EcotechRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

class OrderViewModel(
    private val ecotechRepository: EcotechRepository,
    private val apiService: ApiService
) : ViewModel() {

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _updateResponse = MutableLiveData<UpdateStatusOrdersResponse>()
    val updateResponse: LiveData<UpdateStatusOrdersResponse> = _updateResponse

    fun updateStatusOrderEwasteAccepted(id: String): LiveData<UpdateStatusOrdersResponse> {
        viewModelScope.launch {
            ecotechRepository.updateStatusOrderEwasteAccepted(id)
            withContext(Dispatchers.Main) {
                val response = apiService.updateStatusOrderEwasteAccepted(id)
                if (response.isSuccessful) {
                    _updateResponse.value = response.body()
                } else {
                    handleOrderError(response)
                }
            }
        }
        return updateResponse
    }

    fun updateStatusOrderReject(id: String): LiveData<UpdateStatusOrdersResponse> {
        viewModelScope.launch {
            ecotechRepository.updateStatusOrderEwasteRejected(id)
            withContext(Dispatchers.Main) {
                val response = apiService.updateStatusOrderEwasteRejected(id)
                if (response.isSuccessful) {
                    _updateResponse.value = response.body()
                } else {
                    handleOrderError(response)
                }
            }
        }
        return updateResponse
    }

    private fun handleOrderError(response: Response<UpdateStatusOrdersResponse>) {
        val errorMessage = response.errorBody()?.string()
        if (errorMessage != null) {
            try {
                val errorJson = JSONObject(errorMessage)
                val error = errorJson.getString("message")
                _errorMessage.value = error
            } catch (e: JSONException) {
                _errorMessage.value = "Unexpected error occurred"
            }
        } else {
            _errorMessage.value = "Unexpected error occurred"
        }
    }

}