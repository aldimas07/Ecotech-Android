package com.irhamsoetomo.ecotech.data.repository

import com.irhamsoetomo.ecotech.data.model.request.LoginRequestBody
import com.irhamsoetomo.ecotech.data.model.request.RegisterRequestBody
import com.irhamsoetomo.ecotech.data.model.response.login.LoginResponse
import com.irhamsoetomo.ecotech.data.model.response.predict.PredictResponse
import com.irhamsoetomo.ecotech.data.model.response.register.RegisterResponse
import com.irhamsoetomo.ecotech.data.remote.ApiService
import okhttp3.MultipartBody
import retrofit2.Response

class EcotechRepository(
    private val apiService: ApiService
) {
    suspend fun registerUser(registerRequestBody: RegisterRequestBody) : Response<RegisterResponse> {
        return apiService.registerUser(registerRequestBody)
    }

    suspend fun loginUser(loginUserRequestBody: LoginRequestBody) : Response<LoginResponse> {
        return apiService.loginUser(loginUserRequestBody)
    }

    suspend fun predict(imageMultipart: MultipartBody.Part) : Response<PredictResponse> {
        return apiService.predict(imageMultipart)
    }
}