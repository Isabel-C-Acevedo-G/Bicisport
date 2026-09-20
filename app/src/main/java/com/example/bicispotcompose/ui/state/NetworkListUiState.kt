package com.example.bicispotcompose.ui.state

import com.example.bicispotcompose.data.model.BikeNetwork

/** Estado de la pantalla que lista todas las redes de bicicletas. */
sealed interface NetworkListUiState {
    data object Loading : NetworkListUiState
    data class Error(val message: String) : NetworkListUiState
    data class Success(val networks: List<BikeNetwork>) : NetworkListUiState
}
