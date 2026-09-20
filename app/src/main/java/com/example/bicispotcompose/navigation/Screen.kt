package com.example.bicispotcompose.navigation

/** Rutas de navegación centralizadas para evitar strings sueltos por la app. */
sealed class Screen(val route: String) {
    data object NetworkList : Screen("networkList")

    data object NetworkDetail : Screen("networkDetail/{networkId}") {
        const val ARG_NETWORK_ID = "networkId"
        fun createRoute(networkId: String) = "networkDetail/$networkId"
    }
}
