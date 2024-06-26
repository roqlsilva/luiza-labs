package br.com.roqls23.desafio.luizalabs.core.presentation.ui.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.roqls23.desafio.luizalabs.R
import br.com.roqls23.desafio.luizalabs.utils.extensions.toBrazilianDateFormat
import br.com.roqls23.desafio.luizalabs.utils.extensions.toMillis

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerComponent(
    label: String,
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String, Long) -> Unit,
) {

    val focusManager = LocalFocusManager.current
    var showDatePickerDialog by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf(value) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = value.takeIf { it.isNotEmpty() }?.toMillis() ?: System.currentTimeMillis(),
        initialDisplayedMonthMillis = value.takeIf { it.isNotEmpty() }?.toMillis() ?: System.currentTimeMillis(),
    )

    if (showDatePickerDialog) {
        DatePickerDialog(
            onDismissRequest = {
                showDatePickerDialog = false
                focusManager.clearFocus()
            },
            confirmButton = {
                Button(
                    onClick = {
                        datePickerState
                            .selectedDateMillis?.let { millis ->
                                selectedDate = millis.toBrazilianDateFormat()
                                onValueChange.invoke(selectedDate, millis)
                            }
                        showDatePickerDialog = false
                        focusManager.clearFocus()
                    }
                ) {
                    Text(text = stringResource(R.string.choose_date_label))
                }
            }
        ) {
            DatePicker(
                state = datePickerState
            )
        }
    }

    TextField(
        value = selectedDate,
        onValueChange = {},
        modifier = modifier
            .fillMaxWidth()
            .onFocusEvent {
                if (it.isFocused) {
                    showDatePickerDialog = true
                }
            }
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(8.dp)
            ),
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
        readOnly = true,
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.DateRange,
                contentDescription = null,
                tint = Color.DarkGray
            )
        },
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
private fun DatePickerPreview() {
    DatePickerComponent(
        label = "Data limite",
        onValueChange = { _,_ -> }
    )
}