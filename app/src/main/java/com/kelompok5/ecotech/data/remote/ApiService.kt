package com.kelompok5.ecotech.data.remote

import com.kelompok5.ecotech.data.model.request.LoginRequestBody
import com.kelompok5.ecotech.data.model.request.RegisterRequestBody
import com.kelompok5.ecotech.data.model.request.RegisterUserKolektorRequestBody
import com.kelompok5.ecotech.data.model.response.login.LoginResponse
import com.kelompok5.ecotech.data.model.response.logout.LogoutResponse
import com.kelompok5.ecotech.data.model.response.predict.PredictResponse
import com.kelompok5.ecotech.data.model.response.register.RegisterResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @POST("auth/register")
    suspend fun registerUser(
        @Body body: RegisterRequestBody
    ): Response<RegisterResponse>

    @POST("auth/registerkolektor")
    suspend fun registerUserKolektor(
        @Body body: RegisterUserKolektorRequestBody
    ): Response<RegisterResponse>

    @POST("auth/login")
    suspend fun loginUser(
        @Body body: LoginRequestBody
    ): Response<LoginResponse>
    @POST("auth/logout")
    suspend fun logoutUser(
        @Header("Authorization") token: String
    ): Response<LogoutResponse>

    @Multipart
    @POST("predict")
    suspend fun predict(
        @Part file: MultipartBody.Part,
    ): Response<PredictResponse>
}