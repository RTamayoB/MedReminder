package com.cradlesoft.medreminder.android.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cradlesoft.medreminder.android.core.ui.theme.MedTheme

@Composable
fun <T>InputSelector(
    options: List<T>,
    selectedOption: T,
    onOptionSelected: (T) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null
) {
    val selectorPadding = Modifier.padding(14.dp)
    var expanded by remember { mutableStateOf(false) }

    InputBase(
        label = label,
        modifier = modifier
    ) {
        Box {
            Box(
                modifier = Modifier
                    .defaultMinSize(
                        minWidth = OutlinedTextFieldDefaults.MinWidth,
                        minHeight = OutlinedTextFieldDefaults.MinHeight
                    )
                    .clip(OutlinedTextFieldDefaults.shape)
                    .border(
                        width = if (expanded) OutlinedTextFieldDefaults.FocusedBorderThickness else OutlinedTextFieldDefaults.UnfocusedBorderThickness,
                        color = if (expanded) MaterialTheme.colorScheme.primary else
                            MaterialTheme.colorScheme.outline,
                        shape = OutlinedTextFieldDefaults.shape
                    )
                    .clickable(
                        onClick = {
                            expanded = true
                        }
                    )
            ) {
                Text(
                    text = selectedOption.toString().lowercase().replaceFirstChar { it.uppercase() },
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = selectorPadding.align(Alignment.CenterStart)
                )
                Icon(
                    imageVector = Icons.Rounded.ArrowDropDown,
                    contentDescription = null,
                    modifier = selectorPadding.align(Alignment.CenterEnd)
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.wrapContentWidth()
            ) {
                options.forEachIndexed { index, option ->
                    DropdownMenuItem(
                        text = {
                            Text(text = option.toString().lowercase().replaceFirstChar { it.uppercase() })
                        },
                        onClick = {
                            onOptionSelected(option)
                            expanded = false
                        },
                        modifier = Modifier
                            .defaultMinSize(
                                minHeight = 30.dp
                            )
                    )
                    if (index != options.lastIndex) {
                        Divider()
                    }
                }
            }
        }

    }
}

@Preview
@Composable
fun InputSelectorTest() {
    val selectorItems = listOf("Pastillas", "Tabletas", "Solución")
    var selectedItem by remember {
        mutableStateOf(selectorItems.first())
    }

    MedTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Text(text = "Item selected: $selectedItem")
            InputSelector(
                options = selectorItems,
                selectedOption = selectedItem,
                onOptionSelected = {
                    selectedItem = it
                },
                label = "Tipo de Medicamento"
            )
        }
    }
}