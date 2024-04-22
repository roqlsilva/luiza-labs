package br.com.roqls23.desafio.luizalabs.core.presentation.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.roqls23.desafio.luizalabs.core.presentation.ui.delivery.screen.CreateDeliveryScreen
import br.com.roqls23.desafio.luizalabs.core.presentation.ui.delivery.DeliveryRoutes
import br.com.roqls23.desafio.luizalabs.core.presentation.ui.delivery.screen.ListDeliveriesScreen
import br.com.roqls23.desafio.luizalabs.core.presentation.ui.delivery.screen.UpdateDeliveryScreen

@Composable
fun MainScreen() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = DeliveryRoutes.LIST
    ) {
        composable(
            route = DeliveryRoutes.LIST
        ) {
            ListDeliveriesScreen()
        }

        composable(
            route = DeliveryRoutes.CREATE
        ) {
            CreateDeliveryScreen()
        }

        composable(
            route = DeliveryRoutes.UPDATE
        ) {
            UpdateDeliveryScreen()
        }
    }
}