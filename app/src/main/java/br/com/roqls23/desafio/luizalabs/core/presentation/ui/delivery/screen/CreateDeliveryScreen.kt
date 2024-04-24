package br.com.roqls23.desafio.luizalabs.core.presentation.ui.delivery.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.roqls23.desafio.luizalabs.core.domain.dto.CreateDeliveryForm
import br.com.roqls23.desafio.luizalabs.core.domain.interfaces.enums.TextInputMask
import br.com.roqls23.desafio.luizalabs.core.presentation.ui.composable.CustomTextField
import br.com.roqls23.desafio.luizalabs.core.presentation.ui.composable.DatePickerComponent
import br.com.roqls23.desafio.luizalabs.core.presentation.ui.composable.Dropdown
import br.com.roqls23.desafio.luizalabs.core.presentation.ui.composable.MaskedTextField
import br.com.roqls23.desafio.luizalabs.core.presentation.ui.composable.ScreenSection
import br.com.roqls23.desafio.luizalabs.core.presentation.ui.delivery.viewmodel.CreateDeliveryViewModel

@Composable
fun CreateDeliveryScreen(
    onFinish: () -> Unit
) {

    val scrollState = rememberScrollState()
    val viewModel = hiltViewModel<CreateDeliveryViewModel>()

    val uiState = viewModel.uiState.collectAsState()
    val districts = viewModel.districts.collectAsState()
    val states = viewModel.states.collectAsState()

    val form = remember { mutableStateOf(CreateDeliveryForm()) }

    LaunchedEffect(Unit) {
        viewModel.findStates()
    }

    LaunchedEffect(key1 = uiState.value) {
        when (uiState.value) {
            is CreateDeliveryViewModel.CreateDeliveryUiState.CreatedSuccessfully ->
                onFinish.invoke()

            else -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp)
            .verticalScroll(
                state = scrollState
            ),
    ) {
        ScreenSection(
            title = {
                Text(
                    text = "Dados da entrega",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.DarkGray
                    )
                )
            }
        ) {
            CustomTextField(
                label = "#ID da Entrega",
                onValueChange = { form.value.deliveryId = it },
                modifier = Modifier.padding(top = 8.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            CustomTextField(
                label = "Qtd. de pacotes",
                onValueChange = { form.value.packagesCount = it.toInt() },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            DatePickerComponent(
                label = "Data limite de entrega",
                onValueChange = { formattedDate, _ ->
                    form.value.dueDate = formattedDate
                }
            )

            CustomTextField(
                label = "Nome do cliente",
                onValueChange = {
                    form.value.clientName = it
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words
                )
            )

            MaskedTextField(
                label = "CPF",
                onValueChange = {
                    form.value.clientCPF = it
                },
                textInputMask = TextInputMask.CPF
            )
        }

        ScreenSection(
            modifier = Modifier.padding(top = 24.dp),
            title = {
                Text(
                    text = "Endereço",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.DarkGray
                    )
                )
            }
        ) {
            MaskedTextField(
                label = "CEP",
                onValueChange = {},
                textInputMask = TextInputMask.CEP
            )

            Dropdown(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                items = states.value.map { it.nome }.sorted(),
                label = "Estado",
                onSelectItem = { item ->
                    states.value.indexOfFirst { it.nome == item }
                        .takeIf { it != -1 }
                        ?.let { index ->
                            viewModel.findDistricts(states.value[index].sigla)
                        }
                }
            )

            Dropdown(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                label = "Cidade",
                items = districts.value.map { it.nome }.sorted(),
                onSelectItem = {}
            )

            CustomTextField(
                label = "Bairro",
                onValueChange = {},
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words
                )
            )

            CustomTextField(
                label = "Rua",
                onValueChange = {},
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words
                )
            )

            CustomTextField(
                label = "Número",
                onValueChange = {},
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words
                )
            )

            CustomTextField(
                label = "Complemento (Opcional)",
                onValueChange = {},
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words
                )
            )
        }

        Button(
            onClick = {
                viewModel.createDelivery(form.value)
            },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red,
                contentColor = Color.White,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
        ) {
            Text(
                text = "Salvar",
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    fontSize = 16.sp
                )
            )
        }
    }
}

@Composable
@Preview(showSystemUi = true)
private fun CreateDeliveryScreenPreview() {
    CreateDeliveryScreen(
        onFinish = {}
    )
}