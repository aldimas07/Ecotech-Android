package com.kelompok5.ecotech.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kelompok5.ecotech.data.remote.ApiService
import com.kelompok5.ecotech.data.repository.EcotechRepository
import com.kelompok5.ecotech.data.remote.RetrofitClient

class KolektorViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(KolektorViewModel::class.java)) {
            val apiService = RetrofitClient.createService<ApiService>()
            val repository = EcotechRepository(apiService)
            return KolektorViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
