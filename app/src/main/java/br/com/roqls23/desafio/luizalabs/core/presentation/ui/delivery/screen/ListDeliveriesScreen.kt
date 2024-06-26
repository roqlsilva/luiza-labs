package br.com.roqls23.desafio.luizalabs.core.presentation.ui.delivery.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.roqls23.desafio.luizalabs.R
import br.com.roqls23.desafio.luizalabs.core.domain.entity.DeliveryEntity
import br.com.roqls23.desafio.luizalabs.core.domain.interfaces.enums.TextInputMask
import br.com.roqls23.desafio.luizalabs.core.presentation.ui.composable.ConfirmDialog
import br.com.roqls23.desafio.luizalabs.core.presentation.ui.delivery.viewmodel.ListDeliveriesViewModel
import br.com.roqls23.desafio.luizalabs.utils.extensions.masked

@Composable
fun ListDeliveriesScreen(
    onClickNewDelivery: (() -> Unit)? = null,
    onEditDelivery: ((DeliveryEntity) -> Unit)? = null
) {

    val viewModel = hiltViewModel<ListDeliveriesViewModel>()
    val showDeleteConfirmationDialog = remember { mutableStateOf(false) }
    val selectedDeliveryId = remember { mutableLongStateOf(0L) }

    val deliveries = viewModel.deliveries.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.findDeliveries()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            deliveries.value.forEach { delivery ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(8.dp)
                        )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(style = SpanStyle(
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 18.sp,
                                        color = Color.DarkGray
                                    )) {
                                        append(stringResource(id = R.string.delivery_id_item_label, delivery.deliveryId))
                                    }

                                    withStyle(style = SpanStyle(
                                        fontWeight = FontWeight.Light,
                                        fontSize = 14.sp,
                                        color = Color.DarkGray
                                    )) {
                                        append(stringResource(id = R.string.packages_count_item_label, delivery.packagesCount))
                                    }
                                }
                            )
                        }

                        Column {
                            Text(
                                text = delivery.clientName,
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    color = Color.DarkGray
                                )
                            )
                            Text(
                                text = stringResource(id = R.string.client_cpf_item_label, delivery.clientCpf.masked(TextInputMask.CPF)),
                                style = TextStyle(
                                    fontWeight = FontWeight.Light,
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(id = R.string.due_date_delivery_item_label),
                                style = TextStyle(
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 16.sp,
                                    color = Color.DarkGray
                                )
                            )
                            Text(
                                text = delivery.dueDate,
                                style = TextStyle(
                                    fontWeight = FontWeight.Light,
                                    fontSize = 16.sp,
                                    color = Color.DarkGray
                                )
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = {
                                    onEditDelivery?.invoke(delivery)
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Edit,
                                    contentDescription = null,
                                    tint = Color.DarkGray
                                )
                            }
                            Spacer(modifier = Modifier.width(2.dp))
                            IconButton(
                                onClick = {
                                    selectedDeliveryId.longValue = delivery.id
                                    showDeleteConfirmationDialog.value = true
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = null,
                                    tint = Color.Red
                                )
                            }
                        }
                    }
                }
            }
        }

        IconButton(
            onClick = { onClickNewDelivery?.invoke() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(96.dp)
                .padding(end = 24.dp, bottom = 24.dp)
                .background(
                    color = Color(0xFF512DA8),
                    shape = RoundedCornerShape(50)
                )
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = Color.White
            )
        }
    }

    ConfirmDialog(
        state = showDeleteConfirmationDialog,
        title = stringResource(R.string.delete_dialog_title),
        message = stringResource(R.string.delete_dialog_message),
        onDismissRequest = {
            selectedDeliveryId.longValue = 0L
            showDeleteConfirmationDialog.value = false
        },
        onConfirm = {
            selectedDeliveryId.longValue.takeIf { it > 0 }
                ?.let { deliveryId ->
                    viewModel.deleteDelivery(deliveryId).let {
                        selectedDeliveryId.longValue = 0L
                        showDeleteConfirmationDialog.value = false
                    }
                }
        }
    )
}

@Composable
@Preview
private fun ListDeliveriesScreenPreview() {
    ListDeliveriesScreen()
}