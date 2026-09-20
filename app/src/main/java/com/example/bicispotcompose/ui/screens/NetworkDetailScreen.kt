package com.example.bicispotcompose.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DirectionsBike
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bicispotcompose.data.model.BikeNetwork
import com.example.bicispotcompose.data.model.Station
import com.example.bicispotcompose.ui.state.NetworkDetailUiState
import com.example.bicispotcompose.ui.theme.RojoAgotado
import com.example.bicispotcompose.ui.theme.VerdeDisponible
import com.example.bicispotcompose.ui.viewmodel.NetworkDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NetworkDetailScreen(
    networkId: String,
    onBack: () -> Unit,
    viewModel: NetworkDetailViewModel = viewModel(
        factory = viewModelFactory {
            initializer { NetworkDetailViewModel(networkId) }
        }
    )
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Estaciones") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            when (val estado = uiState) {
                is NetworkDetailUiState.Loading -> EstadoCargando()
                is NetworkDetailUiState.Error -> EstadoError(
                    mensaje = estado.message,
                    onReintentar = viewModel::cargarEstaciones
                )
                is NetworkDetailUiState.Success -> DetalleRed(
                    red = estado.network,
                    estaciones = estado.stations
                )
            }
        }
    }
}

@Composable
private fun DetalleRed(red: BikeNetwork, estaciones: List<Station>) {
    Column(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = red.name, style = MaterialTheme.typography.titleLarge)
            Text(
                text = "${red.city ?: "Ciudad no disponible"}, ${red.country ?: "-"}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )
            Text(
                text = "${estaciones.size} estaciones",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(estaciones, key = { it.id }) { estacion ->
                EstacionCard(estacion = estacion)
            }
        }
    }
}

@Composable
private fun EstacionCard(estacion: Station) {
    val bicisDisponibles = estacion.freeBikes ?: 0
    val cuposLibres = estacion.emptySlots ?: 0

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = estacion.name, style = MaterialTheme.typography.titleMedium)

            Row(
                modifier = Modifier.padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                IndicadorCupo(
                    icono = Icons.Filled.DirectionsBike,
                    etiqueta = "Bicis disponibles",
                    valor = bicisDisponibles,
                    color = if (bicisDisponibles > 0) VerdeDisponible else RojoAgotado
                )
                IndicadorCupo(
                    icono = Icons.Filled.DirectionsBike,
                    etiqueta = "Cupos libres",
                    valor = cuposLibres,
                    color = if (cuposLibres > 0) VerdeDisponible else RojoAgotado
                )
            }
        }
    }
}

@Composable
private fun IndicadorCupo(
    icono: androidx.compose.ui.graphics.vector.ImageVector,
    etiqueta: String,
    valor: Int,
    color: Color
) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icono, contentDescription = null, tint = color)
            Text(
                text = "$valor",
                style = MaterialTheme.typography.titleMedium,
                color = color,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
        Text(text = etiqueta, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
private fun EstadoCargando() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun EstadoError(mensaje: String, onReintentar: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = mensaje, style = MaterialTheme.typography.bodyLarge)
            TextButton(onClick = onReintentar) {
                Text("Reintentar")
            }
        }
    }
}
