package com.example.bicispotcompose.data.repository

import com.example.bicispotcompose.data.model.BikeNetwork
import com.example.bicispotcompose.data.model.Station
import com.example.bicispotcompose.data.remote.ApiService
import com.example.bicispotcompose.data.remote.RetrofitInstance

/**
 * Capa de repositorio del patrón MVVM. El ViewModel nunca llama a
 * Retrofit directamente: siempre pasa por aquí. Esto desacopla la
 * UI/ViewModel de los detalles de la fuente de datos (en este caso, MockAPI).
 */
class BikeRepository(
    private val api: ApiService = RetrofitInstance.api
) {
    suspend fun obtenerRedes(): List<BikeNetwork> = api.getNetworks()

    suspend fun obtenerDetalleRed(id: String): BikeNetwork = api.getNetworkDetail(id)

    suspend fun obtenerEstaciones(): List<Station> = api.getStations()
}
