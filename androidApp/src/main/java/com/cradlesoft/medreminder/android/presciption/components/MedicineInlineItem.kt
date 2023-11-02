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
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cradlesoft.medreminder.core.domain.models.Medicine

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicineInlineItem(
    medicine: Medicine,
    onMedicineClicked: (Medicine) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(),
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
                Text(text = medicine.intakes.size.toString())
                Text(text = " for ${medicine.getPeriodInDays()} days")
            }
        }
    }
}