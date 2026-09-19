package com.example.bicisport.ui.screens.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bicisport.data.api.RetrofitClient
import com.example.bicisport.data.model.User
import com.example.bicisport.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    private val repository = UserRepository(RetrofitClient.instance)

    private val _usuarios = MutableStateFlow<List<User>>(emptyList())
    val usuarios: StateFlow<List<User>> = _usuarios

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        cargarUsuarios()
    }

    fun cargarUsuarios() {
        viewModelScope.launch {
            _cargando.value = true
            _error.value = null
            try {
                _usuarios.value = repository.getUsuarios()
            } catch (e: Exception) {
                _error.value = "No se pudieron cargar los usuarios: ${e.message}"
            } finally {
                _cargando.value = false
            }
        }
    }

    fun crearUsuario(usuario: User) {
        viewModelScope.launch {
            try {
                repository.crearUsuario(usuario)
                cargarUsuarios()
            } catch (e: Exception) {
                _error.value = "No se pudo crear el usuario: ${e.message}"
            }
        }
    }

    fun actualizarUsuario(id: String, usuario: User) {
        viewModelScope.launch {
            try {
                repository.actualizarUsuario(id, usuario)
                cargarUsuarios()
            } catch (e: Exception) {
                _error.value = "No se pudo actualizar el usuario: ${e.message}"
            }
        }
    }

    fun eliminarUsuario(id: String) {
        viewModelScope.launch {
            try {
                repository.eliminarUsuario(id)
                cargarUsuarios()
            } catch (e: Exception) {
                _error.value = "No se pudo eliminar el usuario: ${e.message}"
            }
        }
    }
}