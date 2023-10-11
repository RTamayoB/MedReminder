package com.cradlesoft.medreminder.android.presciption.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cradlesoft.medreminder.android.presciption.list.components.PrescriptionListItem
import com.cradlesoft.medreminder.android.presciption.list.components.mockPresctiptions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrescriptionsListScreen(
    onNavigateToPrescriptionDetails: (id: String) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text(text = "Prescriptions") })
        },
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
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

}