package com.cradlesoft.medreminder.core.domain.models

data class Prescription(
    val id: Long? = null,
    val name: String = "",
    val doctor: Doctor? = null,
    val medicines: List<Medicine> = emptyList()
) {
    fun getTotalIntake(): Float {
        var totalIntake = 0.0F
        for (medicine in medicines) {
            for (intake in medicine.intakes) {
                totalIntake += intake.intakeAmount
            }
        }
        return totalIntake / 100F
    }
}