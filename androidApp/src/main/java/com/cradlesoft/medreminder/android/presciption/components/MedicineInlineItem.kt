package com.cradlesoft.medreminder.android.presciption.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cradlesoft.medreminder.core.domain.models.Medicine

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicineInlineItem(
    medicine: Medicine,
    editModeEnabled: Boolean = false,
    onMedicineClicked: (Medicine) -> Unit,
    onMedicineDelete: (Medicine) -> Unit,
) {
    var openDeleteDialog by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(20.dp),
        onClick = {
            onMedicineClicked(medicine)
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            //TODO: Add Image from db
            Image(
                imageVector = Icons.Default.PhotoCamera,
                contentDescription = "",
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = medicine.name)
                //TODO: Set intake instructions correctly
                Text(text = "siguiente toma: ${medicine.getNextSchedule() ?: "mañana"}")
                Text(text = " por ${medicine.getPeriodInDays()} dias")
            }
            Spacer(modifier = Modifier.weight(1F))
            if (editModeEnabled) {
                IconButton(onClick = { openDeleteDialog = true }) {
                    Icon(imageVector = Icons.Default.DeleteForever, contentDescription = "Delete")
                }
            }
        }
    }

    if (openDeleteDialog) {
        AlertDialog(
            onDismissRequest = { openDeleteDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        onMedicineDelete(medicine)
                        openDeleteDialog = false
                    }
                ) {
                    Text(text = "Eliminar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDeleteDialog = false
                    }
                ) {
                    Text(text = "Cancelar")
                }
            },
            title = {
                Text(text = "¿Eliminar Medicina?")
            },
            text = {
                Text(text = "¿Borra medicina de la receta?")
            }
        )
    }
}

