package com.kelompok5.ecotech.data.model.response.orders

import com.google.gson.annotations.SerializedName

data class OrdersEwasteResponse(
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: orderData,
)

data class orderData(
    val penyetor_id: String,
    val kolektor_id: String,
    val item_image: String,
)