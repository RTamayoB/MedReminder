package com.cradlesoft.medreminder.prescription.detail.ui

import com.cradlesoft.medreminder.core.domain.models.Prescription

data class PrescriptionDetailState(
    val prescription: Prescription = Prescription(),
    val isEditMode: Boolean = false
)