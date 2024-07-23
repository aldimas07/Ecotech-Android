package com.kelompok5.ecotech.data.remote

import com.kelompok5.ecotech.data.model.request.LoginRequestBody
import com.kelompok5.ecotech.data.model.request.OrdersEwasteRequestBody
import com.kelompok5.ecotech.data.model.request.RegisterRequestBody
import com.kelompok5.ecotech.data.model.request.RegisterUserKolektorRequestBody
import com.kelompok5.ecotech.data.model.response.kolektor.GetAllKolektorResponse
import com.kelompok5.ecotech.data.model.response.kolektor.allKolektor
import com.kelompok5.ecotech.data.model.response.login.LoginResponse
import com.kelompok5.ecotech.data.model.response.logout.LogoutResponse
import com.kelompok5.ecotech.data.model.response.orders.GetOrdersEwasteByIDResponse
import com.kelompok5.ecotech.data.model.response.orders.OrdersEwasteResponse
import com.kelompok5.ecotech.data.model.response.orders.UpdateStatusOrdersResponse
import com.kelompok5.ecotech.data.model.response.predict.PredictResponse
import com.kelompok5.ecotech.data.model.response.register.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

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

    @GET("kolektor")
    suspend fun getAllKolektor(
    ): GetAllKolektorResponse

    @GET("orders/status/id/menunggu")
    suspend fun getOrderEwasteByKolektorIdAndStatusMenunggu(
        @Query("kolektor_id") kolektorId: String
    ): GetOrdersEwasteByIDResponse

    @GET("orders/status/id/all")
    suspend fun getAllStatusOrderEwasteByKolektorId(
        @Query("kolektor_id") kolektorId: String
    ): GetOrdersEwasteByIDResponse


    @Multipart
    @POST("auth/submitewaste")
    suspend fun createOrdersEwaste(
        @Part("penyetor_id") penyetorId: RequestBody,
        @Part("kolektor_id") kolektorId: RequestBody,
        @Part image: MultipartBody.Part
    ): Response<OrdersEwasteResponse>

    @PUT("setor/accept/{id}")
    suspend fun updateStatusOrderEwasteAccepted(@Path("id") id: String): Response<UpdateStatusOrdersResponse>

    @PUT("setor/reject/{id}")
    suspend fun updateStatusOrderEwasteRejected(@Path("id") id: String): Response<UpdateStatusOrdersResponse>
}