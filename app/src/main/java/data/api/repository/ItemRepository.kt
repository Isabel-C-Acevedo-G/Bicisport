package com.example.bicisport.data.repository

import com.example.bicisport.data.api.RetrofitClient
import com.example.bicisport.data.model.Bicicleta

class ItemRepository {
    private val api = RetrofitClient.instance

    suspend fun getBicicletas(): List<Bicicleta> = api.getBicicletas()

    suspend fun getBicicletaPorId(id: String): Bicicleta = api.getBicicletaPorId(id)

    suspend fun crearBicicleta(bicicleta: Bicicleta): Bicicleta = api.crearBicicleta(bicicleta)

    suspend fun actualizarBicicleta(id: String, bicicleta: Bicicleta): Bicicleta =
        api.actualizarBicicleta(id, bicicleta)

    suspend fun eliminarBicicleta(id: String) = api.eliminarBicicleta(id)
}