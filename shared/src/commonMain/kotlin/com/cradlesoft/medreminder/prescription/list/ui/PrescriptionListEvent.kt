package com.cradlesoft.medreminder.prescription.list.ui

sealed interface PrescriptionListEvent {
    object GetPrescriptions: PrescriptionListEvent
}