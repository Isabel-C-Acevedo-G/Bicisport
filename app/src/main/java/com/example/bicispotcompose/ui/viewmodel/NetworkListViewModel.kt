package com.example.bicispotcompose.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bicispotcompose.data.model.BikeNetwork
import com.example.bicispotcompose.data.repository.BikeRepository
import com.example.bicispotcompose.ui.state.NetworkListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class NetworkListViewModel(
    private val repository: BikeRepository = BikeRepository()
) : ViewModel() {

    private var redesCompletas: List<BikeNetwork> = emptyList()

    private val _uiState = MutableStateFlow<NetworkListUiState>(NetworkListUiState.Loading)
    val uiState: StateFlow<NetworkListUiState> = _uiState.asStateFlow()

    private val _busqueda = MutableStateFlow("")
    val busqueda: StateFlow<String> = _busqueda.asStateFlow()

    init {
        cargarRedes()
    }

    fun cargarRedes() {
        viewModelScope.launch {
            _uiState.value = NetworkListUiState.Loading
            _uiState.value = try {
                redesCompletas = repository.obtenerRedes()
                NetworkListUiState.Success(filtrar(redesCompletas, _busqueda.value))
            } catch (e: IOException) {
                NetworkListUiState.Error("Sin conexión a internet. Verifica tu red e intenta de nuevo.")
            } catch (e: Exception) {
                NetworkListUiState.Error("No se pudieron cargar las redes de bicicletas. Revisa la URL de MockAPI.")
            }
        }
    }

    fun buscar(texto: String) {
        _busqueda.value = texto
        _uiState.value = NetworkListUiState.Success(filtrar(redesCompletas, texto))
    }

    private fun filtrar(redes: List<BikeNetwork>, texto: String): List<BikeNetwork> {
        if (texto.isBlank()) return redes
        val q = texto.trim().lowercase()
        return redes.filter {
            it.name.lowercase().contains(q) ||
                (it.city?.lowercase()?.contains(q) == true) ||
                (it.country?.lowercase()?.contains(q) == true)
        }
    }
}
