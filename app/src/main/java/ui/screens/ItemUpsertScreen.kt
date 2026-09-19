package com.example.bicisport.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bicisport.data.model.Bicicleta
import com.example.bicisport.ui.screens.viewmodel.ItemViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemUpsertScreen(
    viewModel: ItemViewModel = viewModel(),
    bicicletaExistente: Bicicleta? = null,
    onGuardarExitoso: () -> Unit
) {
    var marca by remember { mutableStateOf(bicicletaExistente?.marca ?: "") }
    var color by remember { mutableStateOf(bicicletaExistente?.color ?: "") }
    var placa by remember { mutableStateOf(bicicletaExistente?.placa ?: "") }
    var estado by remember { mutableStateOf(bicicletaExistente?.estado ?: "disponible") }

    var errorValidacion by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(if (bicicletaExistente == null) "Nueva bicicleta" else "Editar bicicleta") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = marca,
                onValueChange = { marca = it },
                label = { Text("Marca") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = color,
                onValueChange = { color = it },
                label = { Text("Color") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = placa,
                onValueChange = { placa = it },
                label = { Text("Placa") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = estado,
                onValueChange = { estado = it },
                label = { Text("Estado (disponible / ocupado)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            errorValidacion?.let {
                Text(text = it, color = MaterialTheme.colorScheme.error)
                Spacer(modifier = Modifier.height(8.dp))
            }

            Button(
                onClick = {
                    if (marca.isBlank() || color.isBlank() || placa.isBlank()) {
                        errorValidacion = "Marca, color y placa son obligatorios"
                        return@Button
                    }
                    errorValidacion = null

                    val bicicleta = Bicicleta(
                        id = bicicletaExistente?.id ?: "",
                        marca = marca,
                        color = color,
                        placa = placa,
                        estado = estado
                    )

                    if (bicicletaExistente == null) {
                        viewModel.crearBicicleta(bicicleta)
                    } else {
                        viewModel.actualizarBicicleta(bicicleta.id, bicicleta)
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