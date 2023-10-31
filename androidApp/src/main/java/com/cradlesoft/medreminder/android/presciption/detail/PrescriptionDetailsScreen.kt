package com.cradlesoft.medreminder.android.presciption.detail

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.cradlesoft.medreminder.android.R
import com.cradlesoft.medreminder.android.core.ui.components.InputSelector
import com.cradlesoft.medreminder.android.core.ui.components.InputText
import com.cradlesoft.medreminder.core.domain.models.Prescription
import com.cradlesoft.medreminder.prescription.detail.ui.PrescriptionDetailEvent
import com.cradlesoft.medreminder.prescription.detail.ui.PrescriptionDetailState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrescriptionDetailsScreen(
    state: PrescriptionDetailState,
    onEvent: (PrescriptionDetailEvent) -> Unit,
    onBackPress: () -> Unit
) {
    var openTestDialog by remember {
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
                    val title = if (state.isEditMode) "Edit Prescription" else state.prescription.name
                    Text(text = title)
                },
                navigationIcon = {
                    if (state.isEditMode) {
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
                    if (!state.isEditMode) {
                        IconButton(onClick = { onEvent(PrescriptionDetailEvent.EditModeOn) }) {
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
                            Text(text = "Save")
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Crossfade(targetState = state.isEditMode, label = "") { isEditMode ->
            if (isEditMode) {
                PrescriptionForm(
                    prescription = state.prescription,
                    onNameChanged = { onEvent(PrescriptionDetailEvent.SetPrescriptionName(it)) },
                    modifier = Modifier.padding(paddingValues)
                )
            } else {
                PrescriptionDetail(
                    prescription = state.prescription,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
    if (openTestDialog) {
        AddMedicineDialog(
            onDismissRequest = {
                openTestDialog = false
            }
        )
    }
    if (openQuitEditModeDialog) {
        QuitEditModeDialog(
            onDismissRequest = { openQuitEditModeDialog = false },
            onConfirmButtonClick = {
                onEvent(PrescriptionDetailEvent.CancelEdit)
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
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        TextField(value = "Marcus Andrews", onValueChange = {}, enabled = false)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Medicinas")
        Divider()
        LazyColumn {
            items(prescription.medicines) { _ ->
                MedicineInlineItem()
            }
            items(1) {
                MedicineInlineItem()
            }
            items(2) {
                MedicineInlineItem2()
            }
            items(1) {
                MedicineInlineItem()
            }
        }
    }
}

@Composable
fun PrescriptionForm(
    prescription: Prescription,
    onNameChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var doctor by remember {
        mutableStateOf("Doctor Marcus")
    }

    Column(
        modifier = modifier
    ) {
        InputText(
            value = prescription.name,
            onValueChange = onNameChanged,
            label = "Titulo de la Receta"
        )
        InputText(
            value = doctor,
            onValueChange = {
                doctor = it
            },
            label = "Doctor Asignado"
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Medicinas")
        Divider()
        LazyColumn {
            items(1) {
                MedicineInlineItem()
            }
            items(2) {
                MedicineInlineItem2()
            }
            items(1) {
                MedicineInlineItem()
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMedicineDialog(
    onDismissRequest: () -> Unit
) {

    val options = listOf(
        "Tabletas",
        "Pastillas",
        "Gotas",
        "Solución",
        "Inyección"
    )
    var selectedOption by remember {
        mutableStateOf(options.first())
    }
    var mg by remember {
        mutableStateOf("10")
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
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    InputText(
                        value = "",
                        onValueChange = {},
                        label = "Nombre",
                        modifier = Modifier
                            .width(200.dp)
                            .padding(8.dp)
                    )
                    InputSelector(
                        options = options,
                        selectedOption = selectedOption,
                        onOptionSelected = {
                            selectedOption = it
                        },
                        label = "Tipo de Medicamento",
                        modifier = Modifier
                            .width(200.dp)
                            .padding(8.dp)
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
            }
        }
    }
}

@Composable
fun MedicineInlineItem() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                imageVector = Icons.Default.PhotoCamera,
                contentDescription = "",
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = "Paracetamol 600gr")
                Text(text = "1 tableta | 8:30 PM")
                Text(text = "por 5 dias")
            }
        }
    }
}

@Composable
fun MedicineInlineItem2() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                imageVector = Icons.Default.PhotoCamera,
                contentDescription = "",
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = "Paracetamol 600gr")
                Text(text = "5 gotas | cada 8 horas")
                Text(text = "del 24 de Octubre al 2 de Diciembre")
            }
        }
    }
}