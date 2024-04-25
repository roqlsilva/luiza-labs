package br.com.roqls23.desafio.luizalabs.core.presentation.ui.composable

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun ConfirmDialog(
    state: State<Boolean>,
    title: String,
    message: String,
    onDismissRequest: () -> Unit,
    onConfirm: (() -> Unit)? = null,
    onCancel: (() -> Unit)? = null,
) {
    if (state.value) {
        AlertDialog(
            title = {
                Text(
                    text = title,
                    style = TextStyle(
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp,
                        color = Color.DarkGray
                    )
                )
            },
            text = {
                Text(
                    text = message,
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        color = Color.DarkGray
                    )
                )
            },
            onDismissRequest = { onDismissRequest.invoke() },
            confirmButton = {
                TextButton(
                    onClick = { onConfirm?.invoke() }
                ) {
                    Text(
                        text = "Sim",
                        style = TextStyle(
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp,
                            color = Color.DarkGray
                        )
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onCancel?.invoke()
                        onDismissRequest.invoke()
                    }
                ) {
                    Text(
                        text = "Não",
                        style = TextStyle(
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp,
                            color = Color.DarkGray
                        )
                    )
                }
            },
        )
    }
}

@Composable
@Preview
fun ConfirmDialogPreview() {
    ConfirmDialog(
        state = remember { mutableStateOf(true) },
        title = "Título",
        message = "Mensagem do dialog para confirmação?",
        onDismissRequest = {}
    )
}