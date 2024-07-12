package com.kelompok5.ecotech.data.model.response.login


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("data")
    val data: userData,
) {
    data class userData(
        val name: String,
        val email: String,
        val roleid: Int,
        val accessToken: String,
    )
}