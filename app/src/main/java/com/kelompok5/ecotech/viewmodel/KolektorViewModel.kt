package com.kelompok5.ecotech.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.kelompok5.ecotech.data.repository.EcotechRepository
import kotlinx.coroutines.Dispatchers

class KolektorViewModel(private val repository: EcotechRepository) : ViewModel() {

    val kolektorList = liveData(Dispatchers.IO) {
        val response = repository.getAllKolektor()
        emit(response)
    }
}
