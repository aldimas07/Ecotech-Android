package com.irhamsoetomo.ecotech.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.irhamsoetomo.ecotech.data.model.request.RegisterRequestBody
import com.irhamsoetomo.ecotech.data.model.response.predict.PredictResponse
import com.irhamsoetomo.ecotech.data.model.response.register.RegisterResponse
import com.irhamsoetomo.ecotech.data.repository.EcotechRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

class MainViewModel(
    private val ecotechRepository: EcotechRepository
) : ViewModel() {
    private val _prediction = MutableLiveData<PredictResponse?>()
    val prediction: LiveData<PredictResponse?> = _prediction

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

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

}