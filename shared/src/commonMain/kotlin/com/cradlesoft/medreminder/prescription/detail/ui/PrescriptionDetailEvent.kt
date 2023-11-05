package com.cradlesoft.medreminder.prescription.detail.ui

import com.cradlesoft.medreminder.prescription.MedicineBuilder

sealed interface PrescriptionDetailEvent {
    data class SetPrescriptionName(val prescriptionName: String): PrescriptionDetailEvent
    data class AddMedicineToPrescription(val medicineBuilder: MedicineBuilder): PrescriptionDetailEvent
    object SavePrescription: PrescriptionDetailEvent
    object DeletePrescription: PrescriptionDetailEvent
    object EnabledEditMode: PrescriptionDetailEvent
    object CancelEditMode: PrescriptionDetailEvent
}