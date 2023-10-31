package com.cradlesoft.medreminder.android.presciption.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.cradlesoft.medreminder.android.presciption.list.components.PrescriptionListItem
import com.cradlesoft.medreminder.core.domain.models.Prescription
import com.cradlesoft.medreminder.prescription.list.ui.PrescriptionListEvent
import com.cradlesoft.medreminder.prescription.list.ui.PrescriptionListState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrescriptionsListScreen(
    state: PrescriptionListState,
    onEvent: (PrescriptionListEvent) -> Unit,
    onNavigateToPrescriptionDetails: (id: String) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text(text = "Prescriptions") })
        },
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
    ) { paddingValues ->

        LaunchedEffect(true) {
            onEvent(PrescriptionListEvent.GetPrescriptions)
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(state.prescriptions) {
                PrescriptionListItem(
                    prescription = it,
                    onPrescriptionClicked = { prescriptionClicked ->
                        onNavigateToPrescriptionDetails(prescriptionClicked.id.toString())
                    }
                )
            }
            item {
                Button(onClick = {
                    onEvent(
                        PrescriptionListEvent.CreateMockPrescription(
                            Prescription(
                                name = "Test 2",
                                doctor = null,
                                medicines = emptyList()
                            )
                        )
                    )
                }) {
                    Text(text = "Insert")
                }
            }
            /*items(mockPresctiptions) { prescriptionItem ->
                PrescriptionListItem(
                    prescription = prescriptionItem,
                    onPrescriptionClicked = { prescriptionClicked ->
                        onNavigateToPrescriptionDetails(prescriptionClicked.name)
                    }x
                )
            }*/
        }
    }

}