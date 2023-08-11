package com.cradlesoft.medreminder.android.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.cradlesoft.medreminder.android.progress.components.PrescriptionProgressItem
import com.cradlesoft.medreminder.android.progress.components.mockPresctiptions

@Composable
fun ProgressScreen() {
    LazyColumn(
        modifier = Modifier
            .background(Color(0xFFB3EBEE))
            .fillMaxSize()
    ) {
        items(mockPresctiptions) {
            PrescriptionProgressItem(prescription = it)
        }
    }
}