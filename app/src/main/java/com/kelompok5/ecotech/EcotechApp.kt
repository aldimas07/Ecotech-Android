package com.kelompok5.ecotech

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.kelompok5.ecotech.data.remote.ApiService
import com.kelompok5.ecotech.data.remote.RetrofitClient
import com.kelompok5.ecotech.data.repository.EcotechRepository
import com.kelompok5.ecotech.viewmodel.AuthViewModel
import com.kelompok5.ecotech.viewmodel.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class EcotechApp : Application() {

    override fun onCreate() {
        super.onCreate()

        context = applicationContext

        startKoin {
            androidLogger()
            androidContext(this@EcotechApp)
            modules(authModule, mainModule, repositoryModule)
        }
    }

    private val authModule = module {
        viewModel { AuthViewModel(get()) }
    }

    private val mainModule = module {
        viewModel { MainViewModel(get(), get()) }
    }

    private val repositoryModule = module {
        single { RetrofitClient.createService<ApiService>() }
        single { EcotechRepository(get()) }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }
}