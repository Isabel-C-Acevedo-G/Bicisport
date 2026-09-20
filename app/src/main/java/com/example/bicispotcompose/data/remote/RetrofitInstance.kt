package com.example.bicispotcompose.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Punto único de construcción del cliente HTTP. Se crea una sola vez
 * gracias a "by lazy" y se reutiliza en toda la app.
 *
 * Apunta al proyecto "BiciSpot" en MockAPI (https://mockapi.io), con los
 * recursos "networks" y "stations".
 */
object RetrofitInstance {
    private const val BASE_URL = "https://6aac7b00a2413bf0ec10d089.mockapi.io/api/v1/"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
