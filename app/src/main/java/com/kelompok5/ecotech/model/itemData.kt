package com.kelompok5.ecotech.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataItem(
    val namaPenyetor: String,
    val status: String,
    ) : Parcelable
