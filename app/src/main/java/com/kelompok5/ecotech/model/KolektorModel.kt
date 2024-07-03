package com.kelompok5.ecotech.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class KolektorModel(
    val nama: String,
    val alamat: String,
) : Parcelable
