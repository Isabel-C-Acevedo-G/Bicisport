package com.example.bicispotcompose.ui.state

import com.example.bicispotcompose.data.model.BikeNetwork
import com.example.bicispotcompose.data.model.Station

/** Estado de la pantalla de detalle (estaciones) de una red específica. */
sealed interface NetworkDetailUiState {
    data object Loading : NetworkDetailUiState
    data class Error(val message: String) : NetworkDetailUiState
    data class Success(val network: BikeNetwork, val stations: List<Station>) : NetworkDetailUiState
}
