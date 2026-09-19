package com.example.bicisport.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bicisport.data.model.User
import com.example.bicisport.ui.screens.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserUpsertScreen(
    viewModel: UserViewModel = viewModel(),
    usuarioExistente: User? = null,
    onGuardarExitoso: () -> Unit
) {
    var name by remember { mutableStateOf(usuarioExistente?.name ?: "") }
    var email by remember { mutableStateOf(usuarioExistente?.email ?: "") }
    var telefono by remember { mutableStateOf(usuarioExistente?.telefono ?: "") }
    var avatar by remember { mutableStateOf(usuarioExistente?.avatar ?: "") }

    var errorValidacion by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(if (usuarioExistente == null) "Nuevo usuario" else "Editar usuario") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = telefono,
                onValueChange = { telefono = it },
                label = { Text("Teléfono") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            errorValidacion?.let {
                Text(text = it, color = MaterialTheme.colorScheme.error)
                Spacer(modifier = Modifier.height(8.dp))
            }

            Button(
                onClick = {
                    if (name.isBlank() || email.isBlank() || telefono.isBlank()) {
                        errorValidacion = "Nombre, email y teléfono son obligatorios"
                        return@Button
                    }
                    errorValidacion = null

                    val usuario = User(
                        id = usuarioExistente?.id ?: "",
                        name = name,
                        email = email,
                        avatar = avatar,
                        telefono = telefono
                    )

                    if (usuarioExistente == null) {
                        viewModel.crearUsuario(usuario)
                    } else {
                        viewModel.actualizarUsuario(usuario.id, usuario)
                    }
                    onGuardarExitoso()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar")
            }
        }
    }
}