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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.roqls23.desafio.luizalabs.R
import br.com.roqls23.desafio.luizalabs.core.domain.dto.CreateDeliveryForm
import br.com.roqls23.desafio.luizalabs.core.domain.interfaces.enums.TextInputMask
import br.com.roqls23.desafio.luizalabs.core.presentation.ui.composable.CustomTextField
import br.com.roqls23.desafio.luizalabs.core.presentation.ui.composable.DatePickerComponent
import br.com.roqls23.desafio.luizalabs.core.presentation.ui.composable.Dropdown
import br.com.roqls23.desafio.luizalabs.core.presentation.ui.composable.MaskedTextField
import br.com.roqls23.desafio.luizalabs.core.presentation.ui.composable.ScreenSection
import br.com.roqls23.desafio.luizalabs.core.presentation.ui.delivery.viewmodel.UpdateDeliveryViewModel

@Composable
fun UpdateDeliveryScreen(
    onFinish: () -> Unit
) {

    val scrollState = rememberScrollState()
    val viewModel = hiltViewModel<UpdateDeliveryViewModel>()

    val selectedStateIndex = remember { mutableIntStateOf(-1) }
    val selectedDistrictStateIndex = remember { mutableIntStateOf(-1) }

    val uiState = viewModel.uiState.collectAsState()
    val districts = viewModel.districts.collectAsState()
    val states = viewModel.states.collectAsState()

    val form = remember {
        mutableStateOf(CreateDeliveryForm())
    }

    LaunchedEffect(Unit) {
        viewModel.loadDelivery()
    }

    LaunchedEffect(key1 = uiState.value) {
        when (val state = uiState.value) {
            is UpdateDeliveryViewModel.UpdateDeliveryUiState.UpdatedSuccessfully -> {
                onFinish.invoke()
            }

            is UpdateDeliveryViewModel.UpdateDeliveryUiState.LoadDelivery -> {
                form.value = state.entity.toCreateDeliveryForm()
                viewModel.findStates()
            }

            is UpdateDeliveryViewModel.UpdateDeliveryUiState.LoadStates -> {
                state.states.indexOfFirst { it.nome == form.value.uf }
                    .takeIf { it != -1 }
                    ?.let { index ->
                        selectedStateIndex.intValue = index
                        viewModel.findDistricts(state.states[index].sigla)
                    } ?: run {
                        selectedStateIndex.intValue = -1
                    }
            }

            is UpdateDeliveryViewModel.UpdateDeliveryUiState.LoadDistricts -> {
                state.districts.indexOfFirst { it.nome == form.value.city }
                    .takeIf { it != -1 }
                    ?.let { index ->
                        selectedDistrictStateIndex.intValue = index
                    } ?: run {
                        selectedDistrictStateIndex.intValue = -1
                    }
            }

            else -> Unit
        }
    }

    form.value.takeIf { it.deliveryId.isNotEmpty() }?.let {
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
                        text = stringResource(id = R.string.delivery_info),
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.DarkGray
                        )
                    )
                }
            ) {
                CustomTextField(
                    label = stringResource(id = R.string.delivery_id_label),
                    text = form.value.deliveryId,
                    onValueChange = { form.value.deliveryId = it },
                    modifier = Modifier.padding(top = 8.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                CustomTextField(
                    label = stringResource(id = R.string.packages_count_label),
                    text = form.value.packagesCount.toString(),
                    onValueChange = { form.value.packagesCount = it.toInt() },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                DatePickerComponent(
                    label = stringResource(id = R.string.due_date_delivery_label),
                    value = form.value.dueDate,
                    onValueChange = { formattedDate, _ ->
                        form.value.dueDate = formattedDate
                    }
                )

                CustomTextField(
                    label = stringResource(id = R.string.client_name_label),
                    text = form.value.clientName,
                    onValueChange = {
                        form.value.clientName = it
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        capitalization = KeyboardCapitalization.Words
                    )
                )

                MaskedTextField(
                    label = stringResource(id = R.string.cpf_label),
                    text = form.value.clientCPF,
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
                        text = stringResource(id = R.string.address_label),
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.DarkGray
                        )
                    )
                }
            ) {
                MaskedTextField(
                    label = stringResource(id = R.string.postal_code_label),
                    text = form.value.postalCode,
                    onValueChange = { form.value.postalCode = it},
                    textInputMask = TextInputMask.CEP
                )

                Dropdown(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    selectedIndex = selectedStateIndex,
                    items = states.value.map { it.nome }.sorted(),
                    label = stringResource(id = R.string.state_label),
                    onSelectItem = { item ->
                        states.value.indexOfFirst { it.nome == item }
                            .takeIf { it != -1 }
                            ?.let { index ->
                                val uf = states.value[index]
                                form.value.uf = uf.nome
                                selectedStateIndex.intValue = index
                                viewModel.findDistricts(uf.sigla)
                            }
                    }
                )

                Dropdown(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    selectedIndex = selectedDistrictStateIndex,
                    label = stringResource(id = R.string.city_label),
                    items = districts.value.map { it.nome }.sorted(),
                    onSelectItem = { item ->
                        districts.value.indexOfFirst { it.nome == item }
                            .takeIf { it != -1 }
                            ?.let { index ->
                                val district = districts.value[index]
                                form.value.city = district.nome
                                selectedDistrictStateIndex.intValue = index
                            }
                    }
                )

                CustomTextField(
                    label = stringResource(id = R.string.district_label),
                    text = form.value.district,
                    onValueChange = { form.value.district = it },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        capitalization = KeyboardCapitalization.Words
                    )
                )

                CustomTextField(
                    label = stringResource(id = R.string.street_label),
                    text = form.value.street,
                    onValueChange = { form.value.street = it },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        capitalization = KeyboardCapitalization.Words
                    )
                )

                CustomTextField(
                    label = stringResource(id = R.string.number_label),
                    text = form.value.number,
                    onValueChange = { form.value.number = it },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        capitalization = KeyboardCapitalization.Words
                    )
                )

                CustomTextField(
                    label = buildString {
                        append(stringResource(R.string.complement_label))
                        append(" ")
                        append("(${stringResource(R.string.optional_label)})")
                    },
                    text = form.value.complement,
                    onValueChange = { form.value.complement = it },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        capitalization = KeyboardCapitalization.Words
                    )
                )
            }

            Button(
                onClick = {
                    viewModel.updateDelivery(form.value)
                },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF512DA8),
                    contentColor = Color.White,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.btn_save_label),
                    style = TextStyle(
                        fontWeight = FontWeight.Medium,
                        color = Color.White,
                        fontSize = 16.sp
                    )
                )
            }
        }
    }
}
