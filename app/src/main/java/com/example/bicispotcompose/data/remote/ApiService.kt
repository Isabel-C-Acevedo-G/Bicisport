package com.example.bicispotcompose.data.remote

import com.example.bicispotcompose.data.model.BikeNetwork
import com.example.bicispotcompose.data.model.Station
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Contrato del servicio REST. Apunta a los recursos "networks" y
 * "stations" de tu proyecto en MockAPI (https://mockapi.io).
 */
interface ApiService {
    @GET("networks")
    suspend fun getNetworks(): List<BikeNetwork>

    @GET("networks/{id}")
    suspend fun getNetworkDetail(@Path("id") id: String): BikeNetwork

    @GET("stations")
    suspend fun getStations(): List<Station>
}
