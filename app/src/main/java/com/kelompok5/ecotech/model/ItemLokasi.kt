package com.kelompok5.ecotech.model

data class ItemLokasi(
    val nama: String,
    val alamat: String,
    val latitude: Double? = null,
    val longitude: Double? = null
)