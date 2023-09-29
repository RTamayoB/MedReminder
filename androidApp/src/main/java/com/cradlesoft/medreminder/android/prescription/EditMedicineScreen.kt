package com.cradlesoft.medreminder.android.prescription

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EditMedicineScreen() {
    val medicineItems = listOf("Tabletas", "Gotas", "Inyecciones")
    val timeItems = listOf("Horas", "Cena", "Desayuno", "Comida")
    val dayItems = listOf("Dias", "Semanas")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFB3EBEE)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Ingrese su Medicamento",
            color = Color(0xFF280A82),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
        Image(
            imageVector = Icons.Default.PhotoCamera,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .padding(12.dp)
                .border(1.dp, Color(0xFF8055FE))
        )
        TextField(
            value = "",
            onValueChange = {},
            placeholder = {
                Text(text = "Nombre del Medicamento")
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null
                )
            },
            shape = RoundedCornerShape(40.dp),
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        )
        Text(
            text = "A continuación describe cada cuanto tiene que ser recordado el consumo del medicamento",
            fontSize = 14.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            textAlign = TextAlign.Center
        )
        Row(
            Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            NumberTextField(
                modifier = Modifier.padding(4.dp)
            )
            Option(
                medicineItems,
                modifier = Modifier.padding(4.dp)
            )
            Text(
                text = "Cada",
                modifier = Modifier.padding(4.dp)
            )
            NumberTextField(
                modifier = Modifier.padding(4.dp)
            )
            Option(
                timeItems,
                modifier = Modifier.padding(4.dp)
            )
        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Por",
                modifier = Modifier.padding(4.dp)
            )
            NumberTextField(
                modifier = Modifier.padding(4.dp)
            )
            Option(
                dayItems,
                modifier = Modifier.padding(4.dp)
            )
        }
        Spacer(modifier = Modifier.weight(1F))
        Row(
            modifier = Modifier.padding(12.dp)
        ) {
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF09CACA),
                ),
                border = BorderStroke(1.dp, Color.White)
            ) {
                Text(text = "Guardar")
            }
            Spacer(modifier = Modifier.weight(1F))
        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF093FCA)
                ),
                border = BorderStroke(1.dp, Color.White)
            ) {
                Text(text = "Finalizar receta")
            }
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF9051)
                ),
                border = BorderStroke(1.dp, Color.White)
            ) {
                Column {
                    Text(text = "Agregar mas")
                    Text(text = "medicamentos")
                }
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    }
}

@Composable
fun NumberTextField(
    modifier: Modifier = Modifier
) {
    var number by remember {
        mutableStateOf("1")
    }
    TextField(
        value = number,
        onValueChange = {
            number = it
        },
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.colors(
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
        modifier = modifier
            .size(55.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

@Composable
fun Option(
    items: List<String>,
    modifier: Modifier = Modifier
) {
    //TODO: Move to viewmodel
    var expanded by remember {
        mutableStateOf(false)
    }
    var selected by remember {
        mutableStateOf(items.first())
    }
    Box(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(Color.White)
                .padding(4.dp)
                .clickable(
                    onClick = {
                        expanded = true
                    }
                )
        ) {
            Text(
                text = selected,
            )
            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item) },
                    onClick = {
                        selected = item
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun EditMedicineScreenPreview() {
    EditMedicineScreen()
}