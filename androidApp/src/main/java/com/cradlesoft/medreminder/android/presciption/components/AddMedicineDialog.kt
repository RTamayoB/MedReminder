package com.cradlesoft.medreminder.android.presciption.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.cradlesoft.medreminder.android.R
import com.cradlesoft.medreminder.android.core.ui.components.InputSelector
import com.cradlesoft.medreminder.android.core.ui.components.InputText
import com.cradlesoft.medreminder.core.domain.models.Medicine
import com.cradlesoft.medreminder.core.domain.models.MedicineType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMedicineDialog(
    onDismissRequest: () -> Unit
) {

    //TODO: Add a source of truth / ViewModel
    var newMedicine = Medicine()
    val options = listOf(
        "Tabletas",
        "Pastillas",
        "Gotas",
        "Solución",
        "Inyección"
    )
    var mg by remember {
        mutableStateOf("10")
    }

    var tempMedicineName by remember {
        mutableStateOf("")
    }

    var openDate by remember {
        mutableStateOf(false)
    }

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(Modifier.fillMaxSize()) {
                TopAppBar(
                    title = { Text(text = "Create new medicine") },
                    navigationIcon = {
                        IconButton(onClick = onDismissRequest) {
                            Icon(
                                imageVector = Icons.Rounded.Close,
                                contentDescription = "Close Add Medicine Dialog"
                            )
                        }
                    },
                    actions = {
                        TextButton(onClick = onDismissRequest) {
                            Text(text = "Save")
                        }
                    }
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    InputText(
                        value = tempMedicineName,
                        onValueChange = { tempMedicineName = it },
                        label = newMedicine.name,
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(.6F)
                    )
                    InputSelector(
                        options = MedicineType.values().toList(),
                        selectedOption = newMedicine.type,
                        onOptionSelected = {
                        },
                        label = "Tipo de Medicamento",
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(.4F)
                    )
                }
                InputText(
                    value = mg,
                    onValueChange = {
                        mg = it
                    },
                    label = "Cantidad",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                    modifier = Modifier.width(60.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    InputText(
                        value = newMedicine.startOfIntake.toString(),
                        onValueChange = {},
                        label = "Inicio",
                        trailingIcon = {
                            IconButton(onClick = {  }) {
                                Icon(painter = painterResource(id = R.drawable.ic_calendar_add), contentDescription = null)
                            }
                        },
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(.5F)
                    )
                    InputText(
                        value = newMedicine.endOfIntake.toString(),
                        onValueChange = {},
                        label = "Final",
                        trailingIcon = {
                            IconButton(onClick = { openDate = true }) {
                                Icon(painter = painterResource(id = R.drawable.ic_calendar_add), contentDescription = null)
                            }
                        },
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(.5F)
                    )
                }
            }
        }
    }
    if (openDate) {
        DatePickerDialog(
            onDismissRequest = { openDate = false },
            confirmButton = { /*TODO*/ }
        ) {
            DateRangePicker(state = rememberDateRangePickerState())
        }
    }
}