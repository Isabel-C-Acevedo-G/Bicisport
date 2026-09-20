package com.example.bicisport

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsBike
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bicisport.ui.screens.ItemListScreen
import com.example.bicisport.ui.screens.ItemUpsertScreen
import com.example.bicisport.ui.screens.UserListScreen
import com.example.bicisport.ui.screens.UserUpsertScreen
import com.example.bicisport.ui.screens.viewmodel.ItemViewModel
import com.example.bicisport.ui.screens.viewmodel.UserViewModel
import com.example.bicisport.ui.theme.BicisportTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BicisportTheme {
                AppNavegacion()
            }
        }
    }
}

// Rutas de la app
private const val RUTA_BICICLETAS = "bicicletas"
private const val RUTA_BICICLETA_FORM = "bicicleta_form"
private const val RUTA_USUARIOS = "usuarios"
private const val RUTA_USUARIO_FORM = "usuario_form"

@Composable
fun AppNavegacion() {
    val navController: NavHostController = rememberNavController()

    Scaffold(
        bottomBar = { BarraInferior(navController) }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = RUTA_BICICLETAS,
            modifier = androidx.compose.ui.Modifier.padding(padding)
        ) {
            // Lista de bicicletas
            composable(RUTA_BICICLETAS) {
                val itemViewModel: ItemViewModel = viewModel()
                ItemListScreen(
                    viewModel = itemViewModel,
                    onAgregarClick = { navController.navigate("$RUTA_BICICLETA_FORM?id=") },
                    onEditarClick = { bicicleta -> navController.navigate("$RUTA_BICICLETA_FORM?id=${bicicleta.id}") }
                )
            }

            // Formulario de bicicleta (crear o editar, según si llega un id)
            composable(
                route = "$RUTA_BICICLETA_FORM?id={id}",
                arguments = listOf(navArgument("id") {
                    type = NavType.StringType
                    defaultValue = ""
                })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id") ?: ""
                // Reutilizamos el mismo ViewModel de la pantalla anterior para encontrar la bicicleta por id
                val itemViewModel: ItemViewModel = viewModel()
                val bicicletas by itemViewModel.bicicletas.collectAsState()
                val bicicletaExistente = bicicletas.find { it.id == id }

                ItemUpsertScreen(
                    viewModel = itemViewModel,
                    bicicletaExistente = bicicletaExistente,
                    onGuardarExitoso = { navController.popBackStack() }
                )
            }

            // Lista de usuarios
            composable(RUTA_USUARIOS) {
                val userViewModel: UserViewModel = viewModel()
                UserListScreen(
                    viewModel = userViewModel,
                    onAgregarClick = { navController.navigate("$RUTA_USUARIO_FORM?id=") },
                    onEditarClick = { usuario -> navController.navigate("$RUTA_USUARIO_FORM?id=${usuario.id}") }
                )
            }

            // Formulario de usuario (crear o editar)
            composable(
                route = "$RUTA_USUARIO_FORM?id={id}",
                arguments = listOf(navArgument("id") {
                    type = NavType.StringType
                    defaultValue = ""
                })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id") ?: ""
                val userViewModel: UserViewModel = viewModel()
                val usuarios by userViewModel.usuarios.collectAsState()
                val usuarioExistente = usuarios.find { it.id == id }

                UserUpsertScreen(
                    viewModel = userViewModel,
                    usuarioExistente = usuarioExistente,
                    onGuardarExitoso = { navController.popBackStack() }
                )
            }
        }
    }
}

@Composable
fun BarraInferior(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        NavigationBarItem(
            selected = currentRoute == RUTA_BICICLETAS,
            onClick = {
                navController.navigate(RUTA_BICICLETAS) {
                    popUpTo(RUTA_BICICLETAS) { inclusive = true }
                }
            },
            icon = { Icon(Icons.Default.DirectionsBike, contentDescription = "Bicicletas") },
            label = { Text("Bicicletas") }
        )
        NavigationBarItem(
            selected = currentRoute == RUTA_USUARIOS,
            onClick = {
                navController.navigate(RUTA_USUARIOS) {
                    popUpTo(RUTA_BICICLETAS)
                }
            },
            icon = { Icon(Icons.Default.Person, contentDescription = "Usuarios") },
            label = { Text("Usuarios") }
        )
    }
}