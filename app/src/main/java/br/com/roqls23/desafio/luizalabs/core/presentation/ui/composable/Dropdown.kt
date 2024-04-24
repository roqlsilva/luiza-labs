package br.com.roqls23.desafio.luizalabs.core.presentation.ui.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties

@Composable
fun Dropdown(
    modifier: Modifier = Modifier,
    label: String,
    items: List<String> = emptyList(),
    onSelectItem: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    var selectedItem by remember { mutableStateOf("") }
    var isDropDownExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(items) {
        selectedItem = ""
        isDropDownExpanded = false
        focusManager.clearFocus()
    }

    TextField(
        value = selectedItem,
        onValueChange = {},
        readOnly = true,
        modifier = modifier
            .fillMaxWidth()
            .onFocusEvent {
                if (it.isFocused) {
                    isDropDownExpanded = true
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
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
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

    DropdownMenu(
        expanded = isDropDownExpanded,
        onDismissRequest = {
            isDropDownExpanded = false
            focusManager.clearFocus()
        },
        modifier = Modifier.fillMaxSize(fraction = .75f),
        properties = PopupProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            clippingEnabled = items.isNotEmpty()
        )
    ) {
        items.forEach { item ->
            DropdownMenuItem(
                text = {
                    Text(text = item)
                },
                onClick = {
                    selectedItem = item
                    isDropDownExpanded = false
                    focusManager.clearFocus()

                    onSelectItem.invoke(item)
                }
            )
        }
    }
}

@Composable
@Preview
private fun DropdownPreview() {
    Dropdown(
        modifier = Modifier.fillMaxWidth(),
        label = "Estados",
        items = emptyList(),
        onSelectItem = {}
    )
}