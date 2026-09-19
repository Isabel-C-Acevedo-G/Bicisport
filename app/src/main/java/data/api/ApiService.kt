package com.example.bicisport.data.api

import com.example.bicisport.data.model.Bicicleta
import com.example.bicisport.data.model.User
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @GET("items")
    suspend fun getBicicletas(): List<Bicicleta>

    @GET("items/{id}")
    suspend fun getBicicletaPorId(@Path("id") id: String): Bicicleta

    @POST("items")
    suspend fun crearBicicleta(@Body bicicleta: Bicicleta): Bicicleta

    @PUT("items/{id}")
    suspend fun actualizarBicicleta(@Path("id") id: String, @Body bicicleta: Bicicleta): Bicicleta

    @DELETE("items/{id}")
    suspend fun eliminarBicicleta(@Path("id") id: String)

    @GET("users")
    suspend fun getUsuarios(): List<User>

    @GET("users/{id}")
    suspend fun getUsuarioPorId(@Path("id") id: String): User

    @POST("users")
    suspend fun crearUsuario(@Body usuario: User): User

    @PUT("users/{id}")
    suspend fun actualizarUsuario(@Path("id") id: String, @Body usuario: User): User

    @DELETE("users/{id}")
    suspend fun eliminarUsuario(@Path("id") id: String)
}