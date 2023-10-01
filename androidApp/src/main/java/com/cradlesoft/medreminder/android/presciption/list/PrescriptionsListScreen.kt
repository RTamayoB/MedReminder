package com.cradlesoft.medreminder.android.presciption.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.cradlesoft.medreminder.android.presciption.list.components.PrescriptionListItem
import com.cradlesoft.medreminder.android.presciption.list.components.mockPresctiptions

@Composable
fun PrescriptionsListScreen(
    onNavigateToPrescriptionDetails: (id: String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .background(Color(0xFFB3EBEE))
            .fillMaxSize()
    ) {
        items(mockPresctiptions) { prescriptionItem ->
            PrescriptionListItem(
                prescription = prescriptionItem,
                onPrescriptionClicked = { prescriptionClicked ->
                    onNavigateToPrescriptionDetails(prescriptionClicked.name)
                }
            )
        }
    }
}