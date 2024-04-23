package br.com.roqls23.desafio.luizalabs.core.presentation.ui.delivery.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ListDeliveriesScreen(
    onClickNewDelivery: (() -> Unit)? = null
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        IconButton(
            onClick = { onClickNewDelivery?.invoke() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(96.dp)
                .padding(end = 24.dp, bottom = 24.dp)
                .background(
                    color = Color.Red,
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
}

@Composable
@Preview
private fun ListDeliveriesScreenPreview() {
    ListDeliveriesScreen()
}