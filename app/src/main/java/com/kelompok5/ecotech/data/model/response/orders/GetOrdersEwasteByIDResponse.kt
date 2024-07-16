package com.kelompok5.ecotech.data.model.response.orders

import com.google.gson.annotations.SerializedName

data class GetOrdersEwasteByIDResponse(
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: orderDataByID
)

data class orderDataByID(
    val id: Int,
    val penyetor_id: String,
    val kolektor_id: String,
    val item_image: String,
    val status: String,
    val created_at: String,
)
