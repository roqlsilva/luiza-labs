package br.com.roqls23.desafio.luizalabs.core.presentation.ui.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomTextField(
    label: String,
    modifier: Modifier = Modifier,
    text: String = "",
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    var value by remember { mutableStateOf(text) }
    TextField(
        value = value,
        label = {
            Text(
                text = label,
                style = TextStyle(
                    color = Color.DarkGray,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                )
            )
        },
        onValueChange = {
            value = it
            onValueChange.invoke(value)
        },
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(8.dp)
            ),
        keyboardOptions = keyboardOptions,
        colors = TextFieldDefaults.colors(
            unfocusedTextColor = Color.DarkGray,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            unfocusedLabelColor = Color.LightGray,
        )
    )
}

@Composable
@Preview
private fun MaskedTextFieldPreview() {
    CustomTextField(
        label = "Nome do Cliente",
        onValueChange = {}
    )
}