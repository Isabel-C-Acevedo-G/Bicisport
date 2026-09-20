package com.example.bicispotcompose.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bicispotcompose.data.repository.BikeRepository
import com.example.bicispotcompose.ui.state.NetworkDetailUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class NetworkDetailViewModel(
    private val networkId: String,
    private val repository: BikeRepository = BikeRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<NetworkDetailUiState>(NetworkDetailUiState.Loading)
    val uiState: StateFlow<NetworkDetailUiState> = _uiState.asStateFlow()

    init {
        cargarEstaciones()
    }

    fun cargarEstaciones() {
        viewModelScope.launch {
            _uiState.value = NetworkDetailUiState.Loading
            _uiState.value = try {
                val red = repository.obtenerDetalleRed(networkId)
                val todasLasEstaciones = repository.obtenerEstaciones()

                // Si las estaciones ya tienen "networkId" poblado, filtramos por esta red.
                // Si no (aún no configuraste esa relación en MockAPI), mostramos todas
                // como demostración para que la app no se vea vacía.
                val estacionesDeLaRed = todasLasEstaciones.filter { it.networkId == networkId }
                val estaciones = estacionesDeLaRed.ifEmpty { todasLasEstaciones }

                NetworkDetailUiState.Success(network = red, stations = estaciones)
            } catch (e: IOException) {
                NetworkDetailUiState.Error("Sin conexión a internet.")
            } catch (e: Exception) {
                NetworkDetailUiState.Error("No se pudieron cargar las estaciones de esta red.")
            }
        }
    }
}
