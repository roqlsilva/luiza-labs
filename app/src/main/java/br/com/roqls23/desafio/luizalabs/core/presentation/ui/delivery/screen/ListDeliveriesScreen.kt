package br.com.roqls23.desafio.luizalabs.core.presentation.ui.delivery.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.roqls23.desafio.luizalabs.core.presentation.ui.delivery.viewmodel.ListDeliveriesViewModel

@Composable
fun ListDeliveriesScreen() {

    val viewModel = hiltViewModel<ListDeliveriesViewModel>()

    LaunchedEffect(Unit) {
        viewModel.findDistricts()
    }

    Text(text = "List deliveries")
}
