package com.example.bicispotcompose.data.model

import com.google.gson.annotations.SerializedName

/**
 * Una estación de bicicletas. Coincide con el recurso "stations" de tu
 * proyecto de MockAPI. "networkId" es opcional: si más adelante agregas
 * ese campo en MockAPI y lo llenas con el id de una red, la app filtrará
 * automáticamente las estaciones de cada red. Mientras no exista, la app
 * muestra todas las estaciones en cada red como demostración.
 */
data class Station(
    @SerializedName("id") val id: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("free_bikes") val freeBikes: Int? = null,
    @SerializedName("empty_slots") val emptySlots: Int? = null,
    @SerializedName("latitude") val latitude: Double? = null,
    @SerializedName("longitude") val longitude: Double? = null,
    @SerializedName("networkId") val networkId: String? = null
)

/**
 * Una red de bicicletas compartidas. Coincide con el recurso "networks"
 * de tu proyecto de MockAPI.
 */
data class BikeNetwork(
    @SerializedName("id") val id: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("city") val city: String? = null,
    @SerializedName("country") val country: String? = null
)
