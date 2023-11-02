package com.cradlesoft.medreminder.prescription.detail.ui

import com.cradlesoft.medreminder.core.domain.models.Medicine

sealed interface PrescriptionDetailEvent {
    data class SetPrescriptionName(val prescriptionName: String): PrescriptionDetailEvent
    data class AddMedicineToPrescription(val newMedicine: Medicine): PrescriptionDetailEvent
    object SavePrescription: PrescriptionDetailEvent
    object DeletePrescription: PrescriptionDetailEvent
    object EnabledEditMode: PrescriptionDetailEvent
    object CancelEditMode: PrescriptionDetailEvent
}