package com.example.bicispotcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.bicispotcompose.ui.screens.NetworkDetailScreen
import com.example.bicispotcompose.ui.screens.NetworkListScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.NetworkList.route
    ) {
        composable(Screen.NetworkList.route) {
            NetworkListScreen(
                onNetworkClick = { networkId ->
                    navController.navigate(Screen.NetworkDetail.createRoute(networkId))
                }
            )
        }

        composable(
            route = Screen.NetworkDetail.route,
            arguments = listOf(
                navArgument(Screen.NetworkDetail.ARG_NETWORK_ID) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val networkId = backStackEntry.arguments
                ?.getString(Screen.NetworkDetail.ARG_NETWORK_ID)
                .orEmpty()

            NetworkDetailScreen(
                networkId = networkId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
