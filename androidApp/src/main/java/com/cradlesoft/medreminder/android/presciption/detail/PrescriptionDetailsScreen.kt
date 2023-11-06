package com.cradlesoft.medreminder.android.presciption.detail

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.cradlesoft.medreminder.android.R
import com.cradlesoft.medreminder.android.core.ui.components.InputSelector
import com.cradlesoft.medreminder.android.presciption.components.AddMedicineDialog
import com.cradlesoft.medreminder.android.presciption.components.MedicineInlineItem
import com.cradlesoft.medreminder.android.presciption.components.PrescriptionForm
import com.cradlesoft.medreminder.core.domain.models.Medicine
import com.cradlesoft.medreminder.core.domain.models.MedicineType
import com.cradlesoft.medreminder.core.domain.models.Prescription
import com.cradlesoft.medreminder.prescription.MedicineBuilder
import com.cradlesoft.medreminder.prescription.ScheduleMethod
import com.cradlesoft.medreminder.prescription.detail.ui.PrescriptionDetailEvent
import com.cradlesoft.medreminder.prescription.detail.ui.PrescriptionDetailState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrescriptionDetailsScreen(
    state: PrescriptionDetailState,
    onEvent: (PrescriptionDetailEvent) -> Unit,
    onBackPress: () -> Unit
) {
    var detailsMedicineDialog by remember {
        mutableStateOf(false)
    }
    var openAddMedicineDialog by remember {
        mutableStateOf(false)
    }
    var openQuitEditModeDialog by remember {
        mutableStateOf(false)
    }
    var deletePrescriptionDialog by remember {
        mutableStateOf(false)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    val title = if (state.isEditModeEnabled) "Editar Receta" else state.prescription.name
                    Text(text = title)
                },
                navigationIcon = {
                    if (state.isEditModeEnabled) {
                        IconButton(onClick = { openQuitEditModeDialog = true }) {
                            Icon(
                                imageVector = Icons.Rounded.Close,
                                contentDescription = "Disable Edit Mode"
                            )
                        }
                    } else {
                        IconButton(onClick = onBackPress) {
                            Icon(
                                imageVector = Icons.Rounded.ArrowBack,
                                contentDescription = "Go back"
                            )
                        }
                    }
                },
                actions = {
                    if (!state.isEditModeEnabled) {
                        IconButton(onClick = { onEvent(PrescriptionDetailEvent.EnabledEditMode) }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_file_edit),
                                contentDescription = null
                            )
                        }
                    } else {
                        IconButton(onClick = { deletePrescriptionDialog = true }) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Prescription")
                        }
                        TextButton(onClick = { onEvent(PrescriptionDetailEvent.SavePrescription) }) {
                            Text(text = "Guardar")
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Crossfade(targetState = state.isEditModeEnabled, label = "") { isEditMode ->
            if (isEditMode) {
                PrescriptionForm(
                    prescription = state.prescription,
                    editModeEnabled = state.isEditModeEnabled,
                    onNameChanged = { onEvent(PrescriptionDetailEvent.SetPrescriptionName(it)) },
                    onAddMedicineClicked = {
                        openAddMedicineDialog = true
                    },
                    onMedicineDelete  = {
                        onEvent(PrescriptionDetailEvent.DeleteMedicine(it))
                    },
                    modifier = Modifier.padding(paddingValues)
                )
            } else {
                PrescriptionDetail(
                    prescription = state.prescription,
                    onMedicineClicked = {
                        onEvent(PrescriptionDetailEvent.OpenMedicine(it))
                        detailsMedicineDialog = true
                    },
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
    if (detailsMedicineDialog) {
        AddMedicineDialog(
            medicine = state.selectedMedicine ?: Medicine(),
            onDismissRequest = {
                detailsMedicineDialog = false
            }
        )
    }
    if (openAddMedicineDialog) {
        SimpleMedicineDialog(
            onDismissRequest = {
                openAddMedicineDialog = false
                               },
            onConfirmButtonClick = {
                onEvent(PrescriptionDetailEvent.AddMedicineToPrescription(it))
                openAddMedicineDialog = false
            },
        )
    }
    if (openQuitEditModeDialog) {
        QuitEditModeDialog(
            onDismissRequest = { openQuitEditModeDialog = false },
            onConfirmButtonClick = {
                onEvent(PrescriptionDetailEvent.CancelEditMode)
                openQuitEditModeDialog = false
            }
        )
    }
    if (deletePrescriptionDialog) {
        DeletePrescriptionDialog(
            onDismissRequest = { deletePrescriptionDialog = false },
            onConfirmButtonClick = {
                deletePrescriptionDialog = false
                onEvent(PrescriptionDetailEvent.DeletePrescription)
                onBackPress()
            }
        )
    }
}

@Composable
fun PrescriptionDetail(
    prescription: Prescription,
    onMedicineClicked: (Medicine) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(text = "Medicinas", modifier = Modifier.padding(8.dp))
        Divider()
        LazyColumn {
            items(prescription.medicines) { medicine ->
                MedicineInlineItem(
                    medicine = medicine,
                    editModeEnabled = false,
                    onMedicineClicked = onMedicineClicked,
                    onMedicineDelete = {}
                )
            }
        }
    }
}
@Composable
fun QuitEditModeDialog(
    onDismissRequest: () -> Unit,
    onConfirmButtonClick: () ->Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = { 
            TextButton(onClick = onConfirmButtonClick) {
                Text(text = "Quit")
            } 
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(text = "Cancel")
            }
        },
        title = { Text(text = "Quit Edit Mode?")}
    )
}

@Composable
fun DeletePrescriptionDialog(
    onDismissRequest: () -> Unit,
    onConfirmButtonClick: () ->Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = onConfirmButtonClick) {
                Text(text = "Delete")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(text = "Cancel")
            }
        },
        title = { Text(text = "Delete Prescription?")},
        text = {
            Text(text = "Deleting the Prescription will also delete all associated meds.")
        }
    )
}

@Composable
fun SimpleMedicineDialog(
    onDismissRequest: () -> Unit,
    onConfirmButtonClick: (MedicineBuilder) -> Unit
) {
    var newMedicine by remember {
        mutableStateOf(MedicineBuilder())
    }
    var method by remember {
        mutableStateOf(ScheduleMethod.Interval(0))
    }
    val textFieldSizeModifier = Modifier.width(60.dp)
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = {
                    newMedicine = newMedicine.copy(
                        method = method
                    )
                    onConfirmButtonClick(newMedicine)
                }
            ) {
                Text(text = "Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(text = "Cancelar")
            }
        },
        title = { Text(text = "Agregar Medicina")},
        text = {
            Column {
                TextField(
                    value = newMedicine.name,
                    onValueChange = {
                        newMedicine = newMedicine.copy(name = it)
                    },
                    modifier = Modifier.padding(8.dp),
                    label = { Text(text = "Nombre de la Medicina")}
                )
                InputSelector(
                    options = MedicineType.values().toList(),
                    selectedOption = newMedicine.type,
                    onOptionSelected = {
                        newMedicine = newMedicine.copy(type = it)
                    },
                    label = "Tipo de Medicamento",
                    modifier = Modifier
                        .padding(8.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Tomar", modifier = Modifier.padding(8.dp))
                    TextField(
                        value = newMedicine.commonDosage.toString(),
                        onValueChange = {
                            newMedicine = newMedicine.copy(commonDosage = it.toFloatOrNull() ?: 0.0F)
                        },
                        modifier = textFieldSizeModifier,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Cada", modifier = Modifier.padding(8.dp))
                    TextField(
                        value = method.interval.toString(),
                        onValueChange = {
                            method = method.copy(
                                interval = it.toLongOrNull() ?: 0
                            )
                        },
                        modifier = textFieldSizeModifier,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    Text(text = "Horas", modifier = Modifier.padding(8.dp))
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Por", modifier = Modifier.padding(8.dp))
                    TextField(
                        value = newMedicine.days.toString(),
                        onValueChange = {
                            newMedicine = newMedicine.copy(days = it.toIntOrNull() ?: 0)
                        },
                        modifier = textFieldSizeModifier,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    Text(text = "Dias", modifier = Modifier.padding(8.dp))
                }
            }
        }
    )
}