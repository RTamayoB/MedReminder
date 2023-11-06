package com.cradlesoft.medreminder.android.presciption.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
    medicine: Medicine,
    onDismissRequest: () -> Unit
) {

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
                        value = medicine.name,
                        onValueChange = {  },
                        label = "Nombre de Medicina",
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(.6F)
                    )
                    InputSelector(
                        options = MedicineType.values().toList(),
                        selectedOption = medicine.type,
                        onOptionSelected = {
                        },
                        label = "Tipo de Medicamento",
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(.4F)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    InputText(
                        value = medicine.startOfIntake.toString(),
                        onValueChange = {},
                        label = "Inicio",
                        trailingIcon = {
                            IconButton(onClick = { openDate = true }) {
                                Icon(painter = painterResource(id = R.drawable.ic_calendar_add), contentDescription = null)
                            }
                        },
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(.5F)
                    )
                    InputText(
                        value = medicine.endOfIntake.toString(),
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
                LazyColumn {
                    items(medicine.intakes) { intake ->
                        Text(text = "${intake.hour} - ${intake.intakeAmount}")
                    }
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