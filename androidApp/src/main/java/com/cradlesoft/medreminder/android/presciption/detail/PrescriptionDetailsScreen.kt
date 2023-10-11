package com.cradlesoft.medreminder.android.presciption.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
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
import com.cradlesoft.medreminder.android.R
import com.cradlesoft.medreminder.android.core.ui.components.InputSelector
import com.cradlesoft.medreminder.android.core.ui.components.InputText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrescriptionDetailsScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
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
        var doctor by remember {
            mutableStateOf("Doctor Marcus")
        }
        var mg by remember {
            mutableStateOf("10")
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
                InputSelector(
                    options = options,
                    selectedOption = selectedOption,
                    onOptionSelected = {
                        selectedOption = it
                    },
                    label = "Tipo de Medicamento",
                    modifier = Modifier.width(200.dp)
                )
                Spacer(modifier = Modifier.width(14.dp))
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