package com.kelompok5.ecotech.data.remote

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private val clientBuilder = OkHttpClient.Builder()
        .addInterceptor(getLogginInterceptor())
        .writeTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
    private val retrofitBuilder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
    val retrofit: Retrofit = retrofitBuilder
        .baseUrl("http://54.206.44.255:3000/v1/")
//        .baseUrl("http://localhost:3000/v1/")
        .client(clientBuilder.build())
        .build()

    private fun getLogginInterceptor(): Interceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    fun <T> executeApi(call: Call<T>, apiListener: ApiListener<T>) {
        val callback = object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                if (call.isCanceled)
                    apiListener.onCancel()
                else
                    apiListener.onFailure(t)
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (call.isCanceled) {
                    apiListener.onCancel()
                    return
                }
                if (response.isSuccessful) {
                    apiListener.onSuccess(response.body())
                } else {
                    val reason = DefaultErrorHandler(response).handleError()
                    apiListener.onFailure(reason)
                }
            }
        }

        call.enqueue(callback)
    }

    inline fun <reified T> createService(): T {
        return retrofit.create(T::class.java)
    }
}