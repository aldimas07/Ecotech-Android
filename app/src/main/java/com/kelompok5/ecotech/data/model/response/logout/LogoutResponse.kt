package com.kelompok5.ecotech.data.model.response.logout

import com.google.gson.annotations.SerializedName

data class LogoutResponse (
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("message")
    val message: String
)