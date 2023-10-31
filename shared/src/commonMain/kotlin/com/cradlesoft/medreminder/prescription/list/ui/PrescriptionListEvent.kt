package com.cradlesoft.medreminder.prescription.list.ui

import com.cradlesoft.medreminder.core.domain.models.Prescription

sealed interface PrescriptionListEvent {
    data class CreateMockPrescription(val newPrescription: Prescription): PrescriptionListEvent
    object GetPrescriptions: PrescriptionListEvent
}