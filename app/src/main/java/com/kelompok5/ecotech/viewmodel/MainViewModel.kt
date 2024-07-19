package com.kelompok5.ecotech.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompok5.ecotech.data.model.response.orders.OrdersEwasteResponse
import com.kelompok5.ecotech.data.model.response.predict.PredictResponse
import com.kelompok5.ecotech.data.remote.ApiService
import com.kelompok5.ecotech.data.repository.EcotechRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

class MainViewModel(
    private val ecotechRepository: EcotechRepository,
    private val apiService: ApiService
) : ViewModel() {
    private val _prediction = MutableLiveData<PredictResponse?>()
    val prediction: LiveData<PredictResponse?> = _prediction

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _orderResponse = MutableLiveData<OrdersEwasteResponse>()
    val orderResponse: LiveData<OrdersEwasteResponse> = _orderResponse

    fun predict(imageMultipart: MultipartBody.Part) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                ecotechRepository.predict(imageMultipart)
            }.onSuccess { response ->
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        _prediction.value = response.body()
                    } else {
                        handlePredictError(response)
                    }
                }
            }.onFailure { e ->
                withContext(Dispatchers.Main) {
                    _errorMessage.value = e.message
                }
            }
        }
    }

    private fun handlePredictError(response: Response<PredictResponse>) {
        val errorMessage = response.errorBody()?.string()
        if (errorMessage != null) {
            try {
                val errorJson = JSONObject(errorMessage)
                val error = errorJson.getString("message")
                Log.d("predictMessage", "error $error")
                _errorMessage.value = error
            } catch (e: JSONException) {
                Log.d("predictMessage", "JSON parsing error: ${e.message}")
                _errorMessage.value = "Unexpected error occurred"
            }
        } else {
            _errorMessage.value = "Unexpected error occurred"
        }
    }

    fun createOrdersEwaste(penyetorId: RequestBody, kolektorId: RequestBody, imageMultipart: MultipartBody.Part): LiveData<OrdersEwasteResponse> {
        viewModelScope.launch {
            val response = apiService.createOrdersEwaste(
                penyetorId,
                kolektorId,
                imageMultipart
            )
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    _orderResponse.value = response.body()
                } else {
                    handleOrderError(response)
                }
            }
        }
        return orderResponse
    }

    private fun handleOrderError(response: Response<OrdersEwasteResponse>) {
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