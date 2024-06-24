package com.irhamsoetomo.ecotech.data.remote

import com.irhamsoetomo.ecotech.data.model.request.LoginRequestBody
import com.irhamsoetomo.ecotech.data.model.request.RegisterRequestBody
import com.irhamsoetomo.ecotech.data.model.response.login.LoginResponse
import com.irhamsoetomo.ecotech.data.model.response.predict.PredictResponse
import com.irhamsoetomo.ecotech.data.model.response.register.RegisterResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @POST("auth/register")
    suspend fun registerUser(
        @Body body: RegisterRequestBody
    ): Response<RegisterResponse>

    @POST("auth/login")
    suspend fun loginUser(
        @Body body: LoginRequestBody
    ): Response<LoginResponse>

    @Multipart
    @POST("predict")
    suspend fun predict(
        @Part file: MultipartBody.Part,
    ): Response<PredictResponse>
}