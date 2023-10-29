package com.cradlesoft.medreminder.android.presciption.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrescriptionDetailsScreen() {
    var openTestDialog by remember {
        mutableStateOf(false)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Receta Ginecologo")},
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "Go back")
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(painter = painterResource(id = R.drawable.ic_file_edit), contentDescription = null)
                    }
                }
            )
        }
    ) { paddingValues ->

        var doctor by remember {
            mutableStateOf("Doctor Marcus")
        }

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 18.dp)
                .fillMaxSize()
        ) {
            InputText(
                value = doctor,
                onValueChange = {
                    doctor = it
                },
                label = "Doctor Asignado"
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Spacer(modifier = Modifier.width(14.dp))
            }
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
                item { 
                    Button(onClick = { openTestDialog = true }) {
                        Text(text = "Open Dialog")
                    }
                }
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