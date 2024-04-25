package br.com.roqls23.desafio.luizalabs.core.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.roqls23.desafio.luizalabs.R
import br.com.roqls23.desafio.luizalabs.core.presentation.ui.composable.AppBar
import br.com.roqls23.desafio.luizalabs.core.presentation.ui.delivery.DeliveryRoutes
import br.com.roqls23.desafio.luizalabs.core.presentation.ui.delivery.screen.CreateDeliveryScreen
import br.com.roqls23.desafio.luizalabs.core.presentation.ui.delivery.screen.ListDeliveriesScreen
import br.com.roqls23.desafio.luizalabs.core.presentation.ui.delivery.screen.UpdateDeliveryScreen

@Composable
fun MainScreen() {

    val navController = rememberNavController()

    val pageTitle = remember { mutableStateOf("") }
    val backButtonAppBar = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            AppBar(
                title = pageTitle.value,
                backEnabled = backButtonAppBar.value,
                onBack = { navController.popBackStack() }
            )
        },
        content = {
            Box(
                modifier = Modifier
                    .padding(it)
                    .background(Color(0xFFF6F6F6))
            ) {
                AppNavHost(
                    navController = navController,
                    pageTitle = pageTitle,
                    enableBackButtonAppBar = backButtonAppBar
                )
            }
        }
    )
    
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    pageTitle: MutableState<String> = remember { mutableStateOf("") },
    enableBackButtonAppBar: MutableState<Boolean> = remember { mutableStateOf(false) }
) {
    NavHost(
        navController = navController,
        startDestination = DeliveryRoutes.LIST
    ) {
        composable(
            route = DeliveryRoutes.LIST
        ) {
            pageTitle.value = stringResource(R.string.deliveries_list_title)
            enableBackButtonAppBar.value = false
            ListDeliveriesScreen(
                onClickNewDelivery = {
                    navController.navigate(DeliveryRoutes.CREATE)
                },
                onEditDelivery = {
                    navController.navigate(
                        route = DeliveryRoutes.UPDATE.replace("{id}", it.id.toString())
                    )
                }
            )
        }

        composable(
            route = DeliveryRoutes.CREATE
        ) {
            pageTitle.value = stringResource(R.string.new_delivery_title)
            enableBackButtonAppBar.value = true
            CreateDeliveryScreen(
                onFinish = { navController.popBackStack() }
            )
        }

        composable(
            route = DeliveryRoutes.UPDATE,
            arguments = listOf(
                navArgument("id") { type = NavType.LongType }
            )
        ) { navBackStackEntry ->
            navBackStackEntry.arguments?.getLong("id")?.let { id ->
                pageTitle.value = stringResource(R.string.update_delivery_title)
                enableBackButtonAppBar.value = true
                UpdateDeliveryScreen(
                    onFinish = { navController.popBackStack() }
                )
            }
        }
    }
}