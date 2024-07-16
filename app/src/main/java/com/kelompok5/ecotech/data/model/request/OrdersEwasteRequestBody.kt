package com.kelompok5.ecotech.data.model.request

data class OrdersEwasteRequestBody(
    val id: Int,
    val penyetor_id: String,
    val kolektor_id: String,
    val status: String,
    val item_image: String,
    val created_at: String
)
