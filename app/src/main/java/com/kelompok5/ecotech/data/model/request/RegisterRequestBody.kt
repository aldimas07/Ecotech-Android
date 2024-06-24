package com.kelompok5.ecotech.data.model.request

data class RegisterRequestBody(
    val name: String,
    val email: String,
    val password: String,
    val repassword: String
)
