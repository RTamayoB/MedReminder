package com.cradlesoft.medreminder.android.presciption.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cradlesoft.medreminder.android.core.ui.components.InputText
import com.cradlesoft.medreminder.core.domain.models.Prescription

@Composable
fun PrescriptionForm(
    prescription: Prescription,
    onNameChanged: (String) -> Unit,
    onAddMedicineClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        InputText(
            value = prescription.name,
            onValueChange = onNameChanged,
            label = "Titulo de la Receta"
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Medicinas")
        Divider()
        LazyColumn {
            items(prescription.medicines) { medicine ->
                MedicineInlineItem(
                    medicine = medicine,
                    onMedicineClicked = {

                    }
                )
            }
            item {
                AddMedicineButton(onAddMedicineClicked = onAddMedicineClicked)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMedicineButton(
    onAddMedicineClicked: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(),
        shape = RoundedCornerShape(20.dp),
        onClick = onAddMedicineClicked
    ) {
        Text(
            text = "Add new medicine",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}