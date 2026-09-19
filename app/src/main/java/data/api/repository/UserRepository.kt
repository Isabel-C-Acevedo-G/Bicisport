package com.example.bicisport.data.repository

import com.example.bicisport.data.api.ApiService
import com.example.bicisport.data.model.User

class UserRepository(private val apiService: ApiService) {

    suspend fun getUsuarios(): List<User> = apiService.getUsuarios()

    suspend fun getUsuarioPorId(id: String): User = apiService.getUsuarioPorId(id)

    suspend fun crearUsuario(usuario: User): User = apiService.crearUsuario(usuario)

    suspend fun actualizarUsuario(id: String, usuario: User): User =
        apiService.actualizarUsuario(id, usuario)

    suspend fun eliminarUsuario(id: String) = apiService.eliminarUsuario(id)
}