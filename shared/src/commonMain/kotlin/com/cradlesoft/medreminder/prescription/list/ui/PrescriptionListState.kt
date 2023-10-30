package com.cradlesoft.medreminder.prescription.list.ui

import com.cradlesoft.medreminder.core.domain.models.Prescription

data class PrescriptionListState(
    val prescriptions: List<Prescription> = emptyList(),
)