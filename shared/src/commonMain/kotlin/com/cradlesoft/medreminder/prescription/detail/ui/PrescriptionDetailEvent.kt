package com.cradlesoft.medreminder.prescription.detail.ui

import com.cradlesoft.medreminder.core.domain.models.Medicine
import com.cradlesoft.medreminder.prescription.MedicineBuilder

sealed interface PrescriptionDetailEvent {
    data class SetPrescriptionName(val prescriptionName: String): PrescriptionDetailEvent
    data class AddMedicineToPrescription(val medicineBuilder: MedicineBuilder): PrescriptionDetailEvent
    data class OpenMedicine(val selectedMedicine: Medicine): PrescriptionDetailEvent
    object SavePrescription: PrescriptionDetailEvent
    object DeletePrescription: PrescriptionDetailEvent
    object EnabledEditMode: PrescriptionDetailEvent
    object CancelEditMode: PrescriptionDetailEvent
}