package com.kelompok5.ecotech.data.model.request

data class RegisterUserKolektorRequestBody(
    val name: String,
    val email: String,
    val alamat: String,
    val nohp: String,
    val password: String,
    val repassword: String
)
