package com.kelompok5.ecotech.data.repository

import com.kelompok5.ecotech.data.model.request.LoginRequestBody
import com.kelompok5.ecotech.data.model.request.OrdersEwasteRequestBody
import com.kelompok5.ecotech.data.model.request.RegisterRequestBody
import com.kelompok5.ecotech.data.model.request.RegisterUserKolektorRequestBody
import com.kelompok5.ecotech.data.model.response.kolektor.GetAllKolektorResponse
import com.kelompok5.ecotech.data.model.response.login.LoginResponse
import com.kelompok5.ecotech.data.model.response.logout.LogoutResponse
import com.kelompok5.ecotech.data.model.response.orders.GetOrdersEwasteByIDResponse
import com.kelompok5.ecotech.data.model.response.orders.OrdersEwasteResponse
import com.kelompok5.ecotech.data.model.response.predict.PredictResponse
import com.kelompok5.ecotech.data.model.response.register.RegisterResponse
import com.kelompok5.ecotech.data.remote.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response

class EcotechRepository(
    private val apiService: ApiService
) {
    suspend fun registerUser(registerRequestBody: RegisterRequestBody) : Response<RegisterResponse> {
        return apiService.registerUser(registerRequestBody)
    }

    suspend fun registerUserKolektor(registerUserKolektorRequestBody: RegisterUserKolektorRequestBody) : Response<RegisterResponse> {
        return apiService.registerUserKolektor(registerUserKolektorRequestBody)
    }

    suspend fun loginUser(loginUserRequestBody: LoginRequestBody) : Response<LoginResponse> {
        return apiService.loginUser(loginUserRequestBody)
    }

    suspend fun predict(imageMultipart: MultipartBody.Part) : Response<PredictResponse> {
        return apiService.predict(imageMultipart)
    }

    suspend fun logoutUser(token: String): Response<LogoutResponse> {
        return apiService.logoutUser("Bearer $token")
    }
    suspend fun getAllKolektor() = apiService.getAllKolektor()

    suspend fun getOrdersByKolektorIdAndStatusMenunggu(kolektorId: String): GetOrdersEwasteByIDResponse {
        return apiService.getOrderEwasteByKolektorIdAndStatusMenunggu(kolektorId)
    }

    suspend fun createOrdersEwaste(
        penyetorId: RequestBody,
        kolektorId: RequestBody,
        imageMultipart: MultipartBody.Part
    ): Response<OrdersEwasteResponse> {
        return apiService.createOrdersEwaste(penyetorId, kolektorId, imageMultipart)
    }

}