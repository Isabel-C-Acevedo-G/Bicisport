package com.example.bicisport.ui.screens.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bicisport.data.model.Bicicleta
import com.example.bicisport.data.repository.ItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ItemViewModel : ViewModel() {

    private val repository = ItemRepository()

    private val _bicicletas = MutableStateFlow<List<Bicicleta>>(emptyList())
    val bicicletas: StateFlow<List<Bicicleta>> = _bicicletas

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        cargarBicicletas()
    }

    fun cargarBicicletas() {
        viewModelScope.launch {
            _cargando.value = true
            _error.value = null
            try {
                _bicicletas.value = repository.getBicicletas()
            } catch (e: Exception) {
                _error.value = "No se pudieron cargar las bicicletas: ${e.message}"
            } finally {
                _cargando.value = false
            }
        }
    }

    fun crearBicicleta(bicicleta: Bicicleta) {
        viewModelScope.launch {
            try {
                repository.crearBicicleta(bicicleta)
                cargarBicicletas()
            } catch (e: Exception) {
                _error.value = "No se pudo crear la bicicleta: ${e.message}"
            }
        }
    }

    fun actualizarBicicleta(id: String, bicicleta: Bicicleta) {
        viewModelScope.launch {
            try {
                repository.actualizarBicicleta(id, bicicleta)
                cargarBicicletas()
            } catch (e: Exception) {
                _error.value = "No se pudo actualizar la bicicleta: ${e.message}"
            }
        }
    }

    fun eliminarBicicleta(id: String) {
        viewModelScope.launch {
            try {
                repository.eliminarBicicleta(id)
                cargarBicicletas()
            } catch (e: Exception) {
                _error.value = "No se pudo eliminar la bicicleta: ${e.message}"
            }
        }
    }
}
