package com.kelompok5.ecotech.data.model.response.orders

import com.google.gson.annotations.SerializedName

data class UpdateStatusOrdersResponse(
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("message")
    val message: String
)
