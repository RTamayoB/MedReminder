package com.cradlesoft.medreminder.prescription.detail.ui

import com.cradlesoft.medreminder.core.domain.models.Medicine
import com.cradlesoft.medreminder.core.domain.models.Prescription

data class PrescriptionDetailState(
    val prescription: Prescription = Prescription(),
    val selectedMedicine: Medicine? = null,
    val isEditModeEnabled: Boolean = false
)