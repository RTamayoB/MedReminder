package com.cradlesoft.medreminder.prescription.detail.ui

sealed interface PrescriptionDetailEvent {
    data class SetPrescriptionName(val prescriptionName: String): PrescriptionDetailEvent
    object SavePrescription: PrescriptionDetailEvent
    object DeletePrescription: PrescriptionDetailEvent
    object EditModeOn: PrescriptionDetailEvent
    object CancelEdit: PrescriptionDetailEvent
}